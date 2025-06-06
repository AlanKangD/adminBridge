package com.chopping.adminbridge.recipe.repository;

import com.chopping.adminbridge.recipe.entity.Recipe;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;


public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    Page<Recipe> findAll(Pageable pageable);
    // 필요 시 커스텀 쿼리 작성 가능
}