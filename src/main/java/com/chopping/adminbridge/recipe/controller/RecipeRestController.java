package com.chopping.adminbridge.recipe.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.chopping.adminbridge.recipe.dto.request.RecipeFormDto;
import com.chopping.adminbridge.recipe.entity.CategoryCode;
import com.chopping.adminbridge.recipe.service.CategoryCodeService;
import com.chopping.adminbridge.recipe.service.RecipeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/recipe")
@RequiredArgsConstructor
@Tag(name = "Recipe API", description = "레시피 관리 REST API")
public class RecipeRestController {
    
    private final CategoryCodeService categoryCodeService;
    private final RecipeService recipeService;

    @GetMapping("/form")
    @Operation(summary = "레시피 폼 데이터 조회", description = "레시피 등록/수정에 필요한 카테고리 및 시간 정보를 조회합니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "성공"),
        @ApiResponse(responseCode = "404", description = "레시피를 찾을 수 없음")
    })
    public ResponseEntity<?> getRecipeForm(
            @Parameter(description = "레시피 번호 (수정 시)") 
            @RequestParam(value = "recipeNo", required = false) Long recipeNo) {
        try {
            // 카테고리 리스트 데이터
            List<CategoryCode> categoryList = categoryCodeService.getActiveCategoryCodes("category");
            // 요리정보 리스트 (예: 시간 정보)
            List<CategoryCode> timeList = categoryCodeService.getActiveCategoryCodes("timeInfo");

            RecipeFormDto recipeForm = new RecipeFormDto();
            String mode = "create";

            if (recipeNo != null) {
                // 기본 레시피 정보 조회
                recipeForm = recipeService.getRecipeDetail(recipeNo);
                mode = "modify";
            }

            return ResponseEntity.ok().body(new RecipeFormResponse(categoryList, timeList, recipeForm, mode));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("레시피 폼 데이터 조회 실패: " + e.getMessage());
        }
    }

    @PostMapping("/save")
    @Operation(summary = "레시피 등록", description = "새로운 레시피를 등록합니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "레시피 등록 성공"),
        @ApiResponse(responseCode = "400", description = "잘못된 요청"),
        @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    public ResponseEntity<?> saveRecipe(
            @Parameter(description = "레시피 정보") 
            @ModelAttribute RecipeFormDto recipeFormDto, 
            MultipartHttpServletRequest mul) {
        try {
            recipeService.createRecipe(recipeFormDto, mul);
            return ResponseEntity.ok().body("레시피가 성공적으로 등록되었습니다.");
        } catch (Exception e) {
            System.out.println(" ###### save error : " + e.getMessage());
            return ResponseEntity.badRequest().body("등록 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    @PostMapping("/update")
    @Operation(summary = "레시피 수정", description = "기존 레시피를 수정합니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "레시피 수정 성공"),
        @ApiResponse(responseCode = "400", description = "잘못된 요청"),
        @ApiResponse(responseCode = "404", description = "레시피를 찾을 수 없음"),
        @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    public ResponseEntity<?> updateRecipe(
            @Parameter(description = "레시피 정보") 
            @ModelAttribute RecipeFormDto recipeFormDto, 
            MultipartHttpServletRequest mul) {
        try {
            recipeService.updateRecipe(recipeFormDto, mul);
            return ResponseEntity.ok().body("레시피가 성공적으로 수정되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("수정 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    @GetMapping("/list")
    @Operation(summary = "레시피 목록 조회", description = "레시피 목록을 페이지네이션과 함께 조회합니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "성공"),
        @ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    public ResponseEntity<?> getRecipeList(
            @Parameter(description = "페이지 정보") 
            @PageableDefault(size = 10, sort = "recipeNo", direction = Sort.Direction.DESC) Pageable pageable) {
        try {
            Page<RecipeFormDto> page = recipeService.selectRecipeList(pageable);
            return ResponseEntity.ok().body(page);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("레시피 목록 조회 실패: " + e.getMessage());
        }
    }

    @PostMapping("/delete/{recipeNo}")
    @Operation(summary = "레시피 삭제", description = "레시피를 삭제합니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "레시피 삭제 성공"),
        @ApiResponse(responseCode = "404", description = "레시피를 찾을 수 없음"),
        @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    public ResponseEntity<?> deleteRecipe(
            @Parameter(description = "레시피 번호") 
            @PathVariable Long recipeNo) {
        try {
            recipeService.deleteRecipe(recipeNo);
            return ResponseEntity.ok().body("레시피가 성공적으로 삭제되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("삭제 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    // 응답용 내부 클래스
    public static class RecipeFormResponse {
        private List<CategoryCode> categoryList;
        private List<CategoryCode> timeList;
        private RecipeFormDto recipeForm;
        private String mode;

        public RecipeFormResponse(List<CategoryCode> categoryList, List<CategoryCode> timeList, RecipeFormDto recipeForm, String mode) {
            this.categoryList = categoryList;
            this.timeList = timeList;
            this.recipeForm = recipeForm;
            this.mode = mode;
        }

        // Getters
        public List<CategoryCode> getCategoryList() { return categoryList; }
        public List<CategoryCode> getTimeList() { return timeList; }
        public RecipeFormDto getRecipeForm() { return recipeForm; }
        public String getMode() { return mode; }
    }
}

