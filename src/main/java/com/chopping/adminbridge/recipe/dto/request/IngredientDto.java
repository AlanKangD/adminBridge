package com.chopping.adminbridge.recipe.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IngredientDto {
    private String ingredientName;
    private String quantity;

    public IngredientDto(String ingredientName, String quantity) {
        this.ingredientName = ingredientName;
        this.quantity = quantity;
    }
}
