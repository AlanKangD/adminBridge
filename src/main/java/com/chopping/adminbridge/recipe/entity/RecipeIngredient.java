package com.chopping.adminbridge.recipe.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * JPA 엔티티: RECIPE_ETC 테이블 (레시피 재료)
 */
@Entity
@Table(name = "RECIPE_ETC")
@Getter
@Setter
@NoArgsConstructor
public class RecipeIngredient {

    /** PK: recipe_etc_no 자동 생성 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_etc_no")
    private Long recipeEtcNo;

    /** 재료 묶음 대분류 (예: "채소", "양념" 등) */
    @Column(name = "recipe_etc", length = 100)
    private String etcGroup;

    /** 실제 재료명 (예: "돼지고기", "양파" 등) */
    @Column(name = "recipe_etc_ingredient", length = 100)
    private String ingredientName;

    /**
     * 재료 수량 (숫자 정보만 남기도 하고, TEXT 컬럼에 그대로 저장해도 무방)
     * DB는 MEDIUMTEXT 이므로, columnDefinition="TEXT" 로 지정
     */
    @Lob
    @Column(name = "recipe_etc_quantity", columnDefinition = "TEXT")
    private String quantity;

    /** 계량 단위 (예: "g", "개" 등) */
    @Column(name = "recipe_etc_type", length = 50)
    private String unit;

    /** 등록일자 (DB 컬럼 타입 DATE) */
    @Column(name = "recipe_etc_dt")
    private LocalDate createdDate;

    /**
     * FK → RECIPE 테이블의 recipe_no
     * ManyToOne 관계: 여러 개의 RecipeIngredient는 하나의 Recipe에 속함
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_no")
    private Recipe recipe;
}