package com.chopping.adminbridge.recipe.service;

import com.chopping.adminbridge.file.service.FileUploadService;
import com.chopping.adminbridge.recipe.dto.RecipeFormDto;
import com.chopping.adminbridge.recipe.entity.Recipe;
import com.chopping.adminbridge.recipe.entity.RecipeDetail;
import com.chopping.adminbridge.recipe.entity.RecipeIngredient;
import com.chopping.adminbridge.recipe.repository.RecipeDetailRepository;
import com.chopping.adminbridge.recipe.repository.RecipeIngredientRepository;
import com.chopping.adminbridge.recipe.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeIngredientRepository recipeIngredientRepository;
    private final RecipeDetailRepository recipeDetailRepository ;
    private final FileUploadService fileUploadService;

    /**
     * 레시피 전체 등록 로직
     */
    @Transactional
    public void createRecipe(RecipeFormDto dto, MultipartHttpServletRequest mul) throws Exception {

        ///  step 1 ///
        // ▶ 1. Recipe 엔티티 생성 및 기본 속성 채우기
        Recipe recipe = new Recipe();
        recipe.setRecipeType(dto.getRecipeType());
        recipe.setRecipeName(dto.getRecipeName());
        recipe.setRecipeExplanation(dto.getRecipeExplanation());
        recipe.setRecipeTip(dto.getRecipeTip());
        //recipe.setRecipePerson(Integer.valueOf(dto.getRecipePerson()));
        recipe.setRecipeFileName(dto.getRecipeFileName());
        recipe.setRecipeTime(Integer.valueOf(dto.getRecipeTime()));
        recipe.setRecipeRegDt(LocalDate.from(LocalDateTime.now()));
        recipe.setRecipeDisplay("Y");  // 기본값
        recipe.setRecipeDel("N");      // 기본값

        // 중간 saved 정산
        Recipe saved = recipeRepository.save(recipe);
        ///  step 1  end ///


        ///  step 2 ///
        String[] recipeEtc =  mul.getParameterValues("recipeEtc");
        String[] ingredient = mul.getParameterValues("recipeEtcIngredient");
        String[] quantity = mul.getParameterValues("recipeEtcQuantity");

        int checkPoint = 0;
        for(int i=0; i < ingredient.length; i++) {
            if(ingredient[i].equals("startPoint")){  //startPoint 가 시작되면 추가 재료영역이 있는 flag값
                checkPoint++;
                continue;
            }
            RecipeIngredient save = new RecipeIngredient();
            save.setEtcGroup(recipeEtc[checkPoint]);
            save.setIngredientName(ingredient[i]);
            List result = splitCheckType(quantity[i]);
            save.setQuantity((String) result.get(0));
            save.setUnit((String) result.get(1));

            // join 관계의 어노테이션 작업으로 saved 객제 모두 전달
            save.setRecipe(saved);
            recipeIngredientRepository.save(save);
        }
        ///  step 2 end ///


        ///  step 3 ///
        String[] recipeContent =  mul.getParameterValues("recipeContent");
        String[] stepFileName =  mul.getParameterValues("stepFileName");


        for(int i=0; i < recipeContent.length; i++) {
            RecipeDetail detail = new RecipeDetail();   // → 매번 새 객체
            detail.setRecipeDetailImageName(stepFileName[i]);
            detail.setRecipeDetailContent(recipeContent[i]);
            detail.setRecipeDetailStep(i+1);
            detail.setRecipe(saved);
            detail.setRecipeDetailDt(LocalDate.from(LocalDateTime.now()));
            recipeDetailRepository.save(detail);
        }
        ///  step 3 end ///

    }


    public List<RecipeFormDto> selectRecipeList(RecipeFormDto searchDto) {
        int page = searchDto.getStart() / 10; // 페이지 계산 (0부터 시작)
        int size = 10;

        PageRequest pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "recipeNo"));
        Page<Recipe> resultPage = recipeRepository.findAll(pageable);

        List<RecipeFormDto> result = new ArrayList<>();
        for (Recipe recipe : resultPage.getContent()) {
            RecipeFormDto dto = new RecipeFormDto();
            dto.setRecipeNo(recipe.getRecipeNo().intValue());
            dto.setRecipeName(recipe.getRecipeName());
            dto.setRecipeType(recipe.getRecipeType());
            dto.setRecipeRegDt(java.sql.Date.valueOf(recipe.getRecipeRegDt()));
            dto.setRecipeLike(recipe.getRecipeLike());
            dto.setRecipeViewCnt(recipe.getRecipeViewCnt());
            // 필요시 다른 필드도 추가로 세팅
            result.add(dto);
        }

        return result;
    }

    public List splitCheckType(String text) {
        List resultList = new ArrayList();
        String[] whiteList = {"g","T" ,"t" ,"ml" ,"L" ,"kg", "개"};
        for(int i=0; i < whiteList.length; i++) {
            if(text.indexOf(whiteList[i]) > 0){
                String substring = text.substring(0, text.indexOf(whiteList[i]));
                resultList.add(substring);
                resultList.add(whiteList[i]);
                return resultList;
            };
        }
        return null;
    }
}