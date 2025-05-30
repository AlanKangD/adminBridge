package com.chopping.adminbridge.recipe.VO;

import org.springframework.web.multipart.MultipartFile;
import java.util.List;


public class RecipeVO {

    // 레시피 제목
    private String recipeName;

    // 요리 소개
    private String recipeExplanation;

    // 카테고리 코드
    private String recipeType;

    // 요리 시간
    private String recipeTime;

    // 대표 이미지 파일
    private MultipartFile imageFile;

    // 재료 리스트
    private List<Material> materialList;

    // 요리 순서 리스트
    private List<RecipeStep> recipeSteps;

    // 요리 팁
    private String recipeTip;

    // Getters and Setters
    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getRecipeExplanation() {
        return recipeExplanation;
    }

    public void setRecipeExplanation(String recipeExplanation) {
        this.recipeExplanation = recipeExplanation;
    }

    public String getRecipeType() {
        return recipeType;
    }

    public void setRecipeType(String recipeType) {
        this.recipeType = recipeType;
    }

    public String getRecipeTime() {
        return recipeTime;
    }

    public void setRecipeTime(String recipeTime) {
        this.recipeTime = recipeTime;
    }

    public MultipartFile getImageFile() {
        return imageFile;
    }

    public void setImageFile(MultipartFile imageFile) {
        this.imageFile = imageFile;
    }

    public List<Material> getMaterialList() {
        return materialList;
    }

    public void setMaterialList(List<Material> materialList) {
        this.materialList = materialList;
    }

    public List<RecipeStep> getRecipeSteps() {
        return recipeSteps;
    }

    public void setRecipeSteps(List<RecipeStep> recipeSteps) {
        this.recipeSteps = recipeSteps;
    }

    public String getRecipeTip() {
        return recipeTip;
    }

    public void setRecipeTip(String recipeTip) {
        this.recipeTip = recipeTip;
    }

    // 내부 클래스: Material
    public static class Material {
        private String name;
        private String quantity;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }
    }

    // 내부 클래스: RecipeStep
    public static class RecipeStep {
        private String description;
        private MultipartFile stepFile;

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public MultipartFile getStepFile() {
            return stepFile;
        }

        public void setStepFile(MultipartFile stepFile) {
            this.stepFile = stepFile;
        }
    }
}
