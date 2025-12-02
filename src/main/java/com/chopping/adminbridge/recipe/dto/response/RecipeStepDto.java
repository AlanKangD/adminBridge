package com.chopping.adminbridge.recipe.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "조리 단계 DTO (상세용)")
public record RecipeStepDto(
        @Schema(description = "단계 번호", example = "1")
        int stepNumber,

        @Schema(description = "단계 설명", example = "끓는 물에 소금을 넣고 면을 삶습니다.")
        String description,

        @Schema(description = "단계별 이미지 URL (선택)", example = "https://...")
        String imageUrl
) {}