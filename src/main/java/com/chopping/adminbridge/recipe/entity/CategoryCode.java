package com.chopping.adminbridge.recipe.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "CATEGORY_CODE")
public class CategoryCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seq_category")
    private Integer seqCategory;

    @Column(name = "category_code")
    private String categoryCode;

    @Column(name = "category_code_up")
    private String categoryCodeUp;

    @Column(name = "category_code_nm")
    private String categoryCodeNm;

    @Column(name = "category_del")
    private String categoryDel;

    // Getter and Setter
    public Integer getSeqCategory() {
        return seqCategory;
    }

    public void setSeqCategory(Integer seqCategory) {
        this.seqCategory = seqCategory;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getCategoryCodeUp() {
        return categoryCodeUp;
    }

    public void setCategoryCodeUp(String categoryCodeUp) {
        this.categoryCodeUp = categoryCodeUp;
    }

    public String getCategoryCodeNm() {
        return categoryCodeNm;
    }

    public void setCategoryCodeNm(String categoryCodeNm) {
        this.categoryCodeNm = categoryCodeNm;
    }

    public String getCategoryDel() {
        return categoryDel;
    }

    public void setCategoryDel(String categoryDel) {
        this.categoryDel = categoryDel;
    }

    // toString() (Optional)
    @Override
    public String toString() {
        return "CategoryCode{" +
                "seqCategory=" + seqCategory +
                ", categoryCode='" + categoryCode + '\'' +
                ", categoryCodeUp='" + categoryCodeUp + '\'' +
                ", categoryCodeNm='" + categoryCodeNm + '\'' +
                ", categoryDel='" + categoryDel + '\'' +
                '}';
    }
}