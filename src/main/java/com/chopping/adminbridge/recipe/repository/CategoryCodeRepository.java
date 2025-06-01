package com.chopping.adminbridge.recipe.repository;


import com.chopping.adminbridge.recipe.entity.CategoryCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CategoryCodeRepository extends JpaRepository<CategoryCode, Integer> {

    // category_del != 'Y' AND (category_code_up = 'category' OR category_code = 'category')
    @Query(value = "SELECT * FROM CATEGORY_CODE WHERE category_del <> 'Y' AND (category_code_up = :category OR category_code = :category)", nativeQuery = true)
    List<CategoryCode> findActiveCategoryCodes(@Param("category") String category);

    // 기본 CRUD는 JpaRepository로 상속됨
}