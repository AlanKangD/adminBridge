package com.chopping.adminbridge.recipe.repository;

import com.chopping.adminbridge.recipe.entity.RecipeIngredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient, Long> {
    // 필요 시 추가 메서드 작성
}