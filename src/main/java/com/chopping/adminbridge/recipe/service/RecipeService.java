package com.chopping.adminbridge.recipe.service;

import com.chopping.adminbridge.recipe.dto.IngredientDto;
import com.chopping.adminbridge.recipe.dto.RecipeFormDto;
import com.chopping.adminbridge.recipe.entity.Recipe;
import com.chopping.adminbridge.recipe.entity.RecipeDetail;
import com.chopping.adminbridge.recipe.entity.RecipeIngredient;
import com.chopping.adminbridge.recipe.repository.RecipeDetailRepository;
import com.chopping.adminbridge.recipe.repository.RecipeIngredientRepository;
import com.chopping.adminbridge.recipe.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeIngredientRepository recipeIngredientRepository;
    private final RecipeDetailRepository recipeDetailRepository ;

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
            save.setCreatedDate(LocalDate.from(LocalDateTime.now()));
            save.setUseYn("Y");  // ✅ 추가
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
            detail.setUseYn("Y");  // ✅ 추가

            detail.setRecipe(saved);
            detail.setRecipeDetailDt(LocalDate.from(LocalDateTime.now()));
            recipeDetailRepository.save(detail);
        }
        ///  step 3 end ///

    }
    /**
     * 레시피 전체 수정 로직
     */
    @Transactional
    public void updateRecipe(RecipeFormDto dto, MultipartHttpServletRequest mul) throws Exception {

        /// step1 ///
        Recipe recipe = recipeRepository.findById((long) dto.getRecipeNo())
                .orElseThrow(() -> new IllegalArgumentException("해당 레시피가 존재하지 않습니다."));
        recipe.setRecipeName(dto.getRecipeName());
        recipe.setRecipeExplanation(dto.getRecipeExplanation());
        recipe.setRecipeTip(dto.getRecipeTip());
        recipe.setRecipeFileName(dto.getRecipeFileName());
        recipe.setRecipeType(dto.getRecipeType());
        recipe.setRecipeTime(Integer.valueOf(dto.getRecipeTime()));
        recipe.setRecipeDisplay("Y");  // 기본값 유지
        recipe.setRecipeDel("N");      // 기본값 유지
        recipe.setRecipeModDt(LocalDate.from(LocalDateTime.now())); // 수정일자 갱신
        Recipe saved = recipeRepository.save(recipe);
        /// step1 end///

        /// step2 ///
        // 기존 재료 데이터를 삭제 대신 'N' 처리
        List<RecipeIngredient> oldIngredients = recipeIngredientRepository.findByRecipe_RecipeNoAndUseYn((long) dto.getRecipeNo(), "Y");
        for (RecipeIngredient old : oldIngredients) {
            old.setUseYn("N");
            // 필요 시 수정일자 등 추가
        }
        String[] recipeEtc =  mul.getParameterValues("recipeEtc");
        String[] ingredient = mul.getParameterValues("recipeEtcIngredient");
        String[] quantity = mul.getParameterValues("recipeEtcQuantity");


        // save 와 같은 로직이지만 -1로 checkPonit 를 준것은
        // recipeForm 페이지에서 수정하면 startPoint 가 없어서 첫 시작부터 넣었기 때문
        int checkPoint = -1;
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
            save.setCreatedDate(LocalDate.from(LocalDateTime.now()));
            save.setUseYn("Y");  // ✅ 추가
            // join 관계의 어노테이션 작업으로 saved 객제 모두 전달
            save.setRecipe(saved);
            recipeIngredientRepository.save(save);
        }

        /// step2 end ///

        /// step3 ///
        List<RecipeDetail> existingDetails = recipeDetailRepository.findByRecipe_RecipeNoAndUseYnOrderByRecipeDetailStepAsc((long) dto.getRecipeNo(), "Y");
        for (RecipeDetail detail : existingDetails) {
            detail.setUseYn("N");
            recipeDetailRepository.save(detail);
        }

        String[] recipeContent =  mul.getParameterValues("recipeContent");
        String[] stepFileName =  mul.getParameterValues("stepFileName");


        for(int i=0; i < recipeContent.length; i++) {
            RecipeDetail detail = new RecipeDetail();   // → 매번 새 객체
            detail.setRecipeDetailImageName(stepFileName[i]);
            detail.setRecipeDetailContent(recipeContent[i]);
            detail.setRecipeDetailStep(i+1);
            detail.setRecipe(saved);
            detail.setUseYn("Y");  // ✅ 추가
            detail.setRecipeDetailDt(LocalDate.from(LocalDateTime.now()));
            recipeDetailRepository.save(detail);
        }
        /// step3 ///

    }


    public Page<RecipeFormDto> selectRecipeList(Pageable pageable) {
        Page<Recipe> resultPage = recipeRepository.findAll(pageable);

        return resultPage.map(recipe -> {
            RecipeFormDto dto = new RecipeFormDto();
            dto.setRecipeNo(recipe.getRecipeNo().intValue());
            dto.setRecipeName(recipe.getRecipeName());
            dto.setRecipeType(recipe.getRecipeType());
            dto.setRecipeRegDt(java.sql.Date.valueOf(recipe.getRecipeRegDt()));
            dto.setRecipeLike(recipe.getRecipeLike());
            dto.setRecipeViewCnt(recipe.getRecipeViewCnt());
            // 필요시 다른 필드도 추가로 세팅
            return dto;
        });
    }


    @Transactional(readOnly = true)
    public RecipeFormDto getRecipeDetail(Long recipeNo) {
        Recipe recipe = recipeRepository.findById(recipeNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 레시피가 존재하지 않습니다."));

        RecipeFormDto dto = new RecipeFormDto();
        dto.setRecipeNo(recipe.getRecipeNo().intValue());
        dto.setRecipeName(recipe.getRecipeName());
        dto.setRecipeType(recipe.getRecipeType());
        dto.setRecipeExplanation(recipe.getRecipeExplanation());
        dto.setRecipeTip(recipe.getRecipeTip());
        dto.setRecipePerson(
                recipe.getRecipePerson() != null ? String.valueOf(recipe.getRecipePerson()) : null);
        dto.setRecipeTime(
                recipe.getRecipeTime() != null ? String.valueOf(recipe.getRecipeTime()) : null);
        dto.setRecipeFileName(recipe.getRecipeFileName());

        // Step 정보 조회
        List<RecipeDetail> details = recipeDetailRepository
                .findByRecipe_RecipeNoAndUseYnOrderByRecipeDetailStepAsc(recipeNo, "Y");
        List<String> contents = new ArrayList<>();
        List<String> imageNames = new ArrayList<>();
        for (RecipeDetail detail : details) {
            contents.add(detail.getRecipeDetailContent());
            imageNames.add(detail.getRecipeDetailImageName());
        }
        dto.setRecipeContents(contents);
        dto.setStepFileNames(imageNames);

        // 재료 정보 조회
        List<RecipeIngredient> ingredients = recipeIngredientRepository
                .findByRecipe_RecipeNoAndUseYn(recipeNo, "Y");
        List<String> etcGroupList = new ArrayList<>();
        List<String> ingredientNames = new ArrayList<>();
        List<String> quantities = new ArrayList<>();
        for (RecipeIngredient ing : ingredients) {
            etcGroupList.add(ing.getEtcGroup());
            ingredientNames.add(ing.getIngredientName());
            quantities.add(ing.getQuantity() + (ing.getUnit() != null ? ing.getUnit() : ""));
        }
        dto.setRecipeEtc(etcGroupList);
        dto.setRecipeEtcIngredient(ingredientNames);
        dto.setRecipeEtcQuantity(quantities);

        // 계층 구조를 group 화 하여 새로 생성 소스
        // 재료 정보 조회 (그룹핑된 형태로 재구성)
        Map<String, List<IngredientDto>> grouped = new LinkedHashMap<>();

        for (RecipeIngredient ing : ingredients) {
            String groupName = ing.getEtcGroup();
            String quantityStr = ing.getQuantity() + (ing.getUnit() != null ? ing.getUnit() : "");

            grouped.putIfAbsent(groupName, new ArrayList<>());
            grouped.get(groupName).add(new IngredientDto(ing.getIngredientName(), quantityStr));
        }

        dto.setIngredientGroupMap(grouped);


        return dto;
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