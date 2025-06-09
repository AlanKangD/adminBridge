package com.chopping.adminbridge.recipe.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RecipeEtcGroupDto {

    private String recipeEtc;
    private List<IngredientDto> ingredients;
}
