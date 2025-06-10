package com.chopping.adminbridge.recipe.repository;

import com.chopping.adminbridge.recipe.entity.RecipeDetail;
import com.chopping.adminbridge.recipe.entity.RecipeIngredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient, Long> {
    // 필요 시 추가 메서드 작성
    // RecipeIngredientRepository
    List<RecipeIngredient> findByRecipe_RecipeNoAndUseYn(Long recipeNo, String useYn);
}