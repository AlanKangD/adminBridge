package com.chopping.adminbridge.recipe.service;

import com.chopping.adminbridge.recipe.entity.CategoryCode;
import com.chopping.adminbridge.recipe.repository.CategoryCodeRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoryCodeService {

    private final CategoryCodeRepository categoryCodeRepository;

    public CategoryCodeService(CategoryCodeRepository categoryCodeRepository) {
        this.categoryCodeRepository = categoryCodeRepository;
    }

    // category_del != 'Y' AND (category_code_up = 'category' OR category_code = 'category')
    public List<CategoryCode> getActiveCategoryCodes(String category) {
        System.out.println("💬 category 파라미터 값: " + category);
        List<CategoryCode> result = categoryCodeRepository.findActiveCategoryCodes(category);
        System.out.println("💬 result.size() = " + result.size());
        return result;
    }

    // 기타 CRUD 메서드 필요 시 추가
    public CategoryCode getCategoryCodeById(Integer id) {
        return categoryCodeRepository.findById(id).orElse(null);
    }

    public CategoryCode saveCategoryCode(CategoryCode categoryCode) {
        return categoryCodeRepository.save(categoryCode);
    }

    public void deleteCategoryCode(Integer id) {
        categoryCodeRepository.deleteById(id);
    }
}