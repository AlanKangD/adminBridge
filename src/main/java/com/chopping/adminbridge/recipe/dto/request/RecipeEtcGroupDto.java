package com.chopping.adminbridge.recipe.dto.request;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecipeEtcGroupDto {

    private String recipeEtc;
    private List<IngredientDto> ingredients;
}
