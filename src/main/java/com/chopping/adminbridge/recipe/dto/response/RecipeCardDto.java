package com.chopping.adminbridge.recipe.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "레시피 카드 응답 DTO (사용자 목록용)")
public record RecipeCardDto(
        @Schema(description = "레시피 고유 ID", example = "1")
        String id,

        @Schema(description = "레시피 제목", example = "전문가의 특제 바질 페스토 파스타")
        String title,

        @Schema(description = "메인 이미지 URL", example = "https://...")
        String imageUrl,

        @Schema(description = "총 조리 시간 (분)", example = "20")
        int cookTime,

        @Schema(description = "별점 (0.0 ~ 5.0)", example = "4.9")
        double rating,

        @Schema(description = "리뷰 수", example = "128")
        int reviewCount

) {}