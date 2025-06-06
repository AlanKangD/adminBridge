package com.chopping.adminbridge.recipe.controller;

import com.chopping.adminbridge.recipe.dto.RecipeFormDto;
import com.chopping.adminbridge.recipe.entity.CategoryCode;
import com.chopping.adminbridge.recipe.service.CategoryCodeService;
import com.chopping.adminbridge.recipe.service.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.domain.Sort;

import java.util.List;

@Controller
@RequestMapping("/recipe")
public class RecipeController {
    private final CategoryCodeService categoryCodeService;
    private final RecipeService recipeService;

    public RecipeController(CategoryCodeService categoryCodeService, RecipeService recipeService) {
        this.categoryCodeService = categoryCodeService;
        this.recipeService = recipeService;
    }


    @GetMapping("/form")
    public String recipeForm(Model model) {

        // 1) 빈 DTO를 하나 생성해서 "recipeForm"이라는 이름으로 모델에 담아준다.
        model.addAttribute("recipeForm", new RecipeFormDto());

        // jpa 를 이용한 카테고리, 요리정보 데이터 가져오는 로직
        // 카테고리 리스트 데이터
        List<CategoryCode> categoryList = categoryCodeService.getActiveCategoryCodes("category");
        model.addAttribute("categoryList", categoryList);

        // 요리정보 리스트 (예: 시간 정보)
        List<CategoryCode> timeList = categoryCodeService.getActiveCategoryCodes("timeInfo");
        model.addAttribute("timeList", timeList);

        return "recipe/recipeForm";
    }

    @PostMapping("/save")
    public String saveRecipe(@ModelAttribute RecipeFormDto recipeFormDto, Model model, MultipartHttpServletRequest mul, RedirectAttributes redirectAttrs) throws Exception {
        try {

            recipeService.createRecipe(recipeFormDto, mul);

            // 등록 성공 시 메시지를 flash attribute로 담고, 목록 페이지로 리다이렉트
            redirectAttrs.addFlashAttribute("message", "레시피가 성공적으로 등록되었습니다.");
            return "redirect:/recipe/list";
        } catch (Exception e) {
            // 예외(유효성 검사 실패, DB 오류 등) 발생 시 실패 메시지와 함께 다시 등록 폼으로
            redirectAttrs.addFlashAttribute("error", "등록 중 오류가 발생했습니다: " + e.getMessage());
            // 폼에 입력했던 값들을 모델에 다시 담아서 보여주고 싶다면 dto를 addFlashAttribute에 같이 담아두면 됩니다.
            redirectAttrs.addFlashAttribute("recipeForm", recipeFormDto);
            return "redirect:/recipe/form";
        }
    }

    @GetMapping("/list")
    public String recipeListPage(
            @PageableDefault(size = 10, sort = "recipeNo", direction = Sort.Direction.DESC) Pageable pageable,
            Model model) {
        Page<RecipeFormDto> page = recipeService.selectRecipeList(pageable);
        model.addAttribute("page", page);
        model.addAttribute("list", page.getContent());

        return "recipe/recipeList";
    }

}
