package com.chopping.adminbridge.recipe.controller;

import com.chopping.adminbridge.recipe.VO.RecipeVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/recipe")
public class RecipeController {

    @GetMapping("/form")
    public String recipeForm(Model model) {
        model.addAttribute("recipeVO", new RecipeVO());
        return "recipe/recipeForm";
    }

}
