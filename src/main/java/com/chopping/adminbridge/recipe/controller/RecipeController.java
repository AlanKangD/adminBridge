package com.chopping.adminbridge.recipe.controller;

import com.chopping.adminbridge.recipe.VO.RecipeVO;
import com.chopping.adminbridge.recipe.entity.CategoryCode;
import com.chopping.adminbridge.recipe.service.CategoryCodeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/recipe")
public class RecipeController {
    private final CategoryCodeService categoryCodeService;

    public RecipeController(CategoryCodeService categoryCodeService) {
        this.categoryCodeService = categoryCodeService;
    }
    @GetMapping("/form")
    public String recipeForm(Model model) {
        model.addAttribute("recipeVO", new RecipeVO());

        // 카테고리 리스트 데이터
        List<CategoryCode> categoryList = categoryCodeService.getActiveCategoryCodes("category");
        model.addAttribute("categoryList", categoryList);

        // 요리정보 리스트 (예: 시간 정보)
        List<CategoryCode> timeList = categoryCodeService.getActiveCategoryCodes("timeInfo");
        model.addAttribute("timeList", timeList);

        return "recipe/recipeForm";
    }

}
