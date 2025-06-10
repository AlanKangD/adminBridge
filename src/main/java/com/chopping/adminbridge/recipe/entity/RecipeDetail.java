package com.chopping.adminbridge.recipe.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "RECIPE_DETAIL")
@Getter
@Setter
@NoArgsConstructor
public class RecipeDetail {

    /**
     * DB 컬럼: recipe_detail_no (AUTO_INCREMENT)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_detail_no")
    private Long recipeDetailNo;

    /**
     * N:1 관계 (RECIPE 1개에 여러 개의 상세(step)가 붙음)
     * DB 컬럼: recipe_no (외래키)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_no", nullable = false)
    private Recipe recipe;

    /**
     * DB 컬럼: recipe_detail_step (정수)
     */
    @Column(name = "recipe_detail_step", nullable = false)
    private Integer recipeDetailStep;

    /**
     * DB 컬럼: recipe_detail_content (최대 5000자)
     */
    @Lob
    @Column(name = "recipe_detail_content", nullable = false, columnDefinition = "VARCHAR(5000)")
    private String recipeDetailContent;

    /**
     * DB 컬럼: recipe_detail_image_name (최대 1000자)
     */
    @Column(name = "recipe_detail_image_name", length = 1000)
    private String recipeDetailImageName;

    /**
     * DB 컬럼: recipe_detail_dt (DATE, 기본값 CURRENT_TIMESTAMP)
     * 자바에서는 LocalDate로 매핑
     */
    @Column(name = "recipe_detail_dt", nullable = false)
    private LocalDate recipeDetailDt;

    @Column(name = "use_yn")
    private String useYn;

    /**
     * 생성자 호출 시 즉시 현재 날짜로 세팅하거나,
     * JPA가 INSERT 시 자동으로 채우려면 별도 어노테이션을 추가로 붙일 수 있습니다.
     * 아래는 기본값을 “오늘”로 채우는 예시입니다.
     */
    @PrePersist
    public void prePersist() {
        if (recipeDetailDt == null) {
            recipeDetailDt = LocalDate.now();
        }
    }
}