package com.chopping.adminbridge.recipe.dto;

import java.sql.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * RecipeVO 기반의 DTO 클래스
 */
@Getter
@Setter
public class RecipeFormDto {

    // --- Recipe Master ---
    private int recipeNo;                // 레시피 No
    private String recipeType;           // 레시피 타입
    private String recipeName;           // 레시피 명
    private Date recipeRegDt;            // 레시피 등록일
    private String recipeDisplay;        // 레시피 전시 여부
    private String recipeExplanation;    // 레시피 설명
    private String recipeFileName;       // 레시피 대표 사진 이름
    private String recipeDel;            // 레시피 삭제 여부
    private String recipeTip;            // 레시피 팁


    // --- Recipe Detail ---
    private int recipeDetailNo;          // 레시피 상세 No
    private int recipeDetailStep;        // 레시피 순서
    private String recipeDetailContent;  // 레시피 내용
    private String recipeDetailImageName;// 레시피 이미지 이름
    private String recipeDetailDt;       // 레시피 상세 등록일 (문자열)
    private String recipePerson;         // 레시피 인분
    private String recipeTime;           // 레시피 시간

    // --- Recipe Etc (재료) ---
    private int recipeEtcNo;             // 레시피 기타 No
   // private String recipeEtcIngredient;  // 재료명
    //private String recipeEtcQuantity;    // 재료 수량
    private String recipeEtcType;        // 재료 계량 Type
    private Date recipeEtcDt;            // 재료 등록일
    //private String recipeEtc;            // 재료 대분류

    // --- 기타 정보 ---
    private int recipeLike;              // 레시피 좋아요 수
    private int recipeViewCnt;           // 레시피 조회 수
    private String filePath;             // 파일 경로

    // --- 페이징 설정 ---
    private int start;
    private int end;

    private List<String> recipeContents;
    private List<String> stepFileNames;
    private List<String> recipeEtc;
    private List<String> recipeEtcIngredient;
    private List<String> recipeEtcQuantity;


}