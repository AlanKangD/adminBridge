package com.chopping.adminbridge.recipe.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate; // DATE 만 관리한다고 가정

@Entity
@Table(name = "RECIPE")
@Getter
@Setter
@NoArgsConstructor
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_no")
    private Integer recipeNo;

    @Column(name = "recipe_type", length = 20)
    private String recipeType;

    @Column(name = "recipe_name", length = 100, nullable = false)
    private String recipeName;

    // DB 컬럼: recipe_reg_dt (DATE)
    @Column(name = "recipe_reg_dt", nullable = false)
    private LocalDate recipeRegDt;

    @Column(name = "recipe_display", length = 5, nullable = false)
    private String recipeDisplay = "Y";

    @Column(name = "recipe_file_name", length = 100)
    private String recipeFileName;

    @Column(name = "recipe_explanation", length = 100)
    private String recipeExplanation;

    @Column(name = "recipe_tip", length = 100)
    private String recipeTip;

    @Column(name = "recipe_person")
    private Integer recipePerson;

    @Column(name = "recipe_time")
    private Integer recipeTime;

    @Column(name = "recipe_like")
    private Integer recipeLike = 0;

    @Column(name = "recipe_del", length = 5, nullable = false)
    private String recipeDel = "N";

    @Column(name = "recipe_view_cnt")
    private Integer recipeViewCnt = 0;

    // DB 컬럼: recipe_mod_dt (DATE), nullable 허용
    @Column(name = "recipe_mod_dt")
    private LocalDate recipeModDt;

    // 연관관계(생략)…
}