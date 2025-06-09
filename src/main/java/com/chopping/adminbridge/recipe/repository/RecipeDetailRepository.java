package com.chopping.adminbridge.recipe.repository;

import com.chopping.adminbridge.recipe.entity.RecipeDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeDetailRepository extends JpaRepository<RecipeDetail, Long> {
    List<RecipeDetail> findByRecipe_RecipeNoOrderByRecipeDetailStepAsc(Long recipeNo);
    // 장기적으로 필요하면 단계별 목록 검색 메서드 등 추가
}