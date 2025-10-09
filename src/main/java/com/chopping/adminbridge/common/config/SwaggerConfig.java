package com.chopping.adminbridge.common.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

/**
 * Swagger / OpenAPI 3 설정 (springdoc 2.x for Spring Boot 3.x)
 * - JWT Bearer 인증 표시
 * - 패키지 기준 문서 그룹핑 (auth / file / recipe)
 * - 서버 URL: 기본 "/" (WAR의 context-path 사용 시 자동 반영)
 */
@OpenAPIDefinition(
        info = @Info(
                title = "AdminBridge API",
                version = "v1",
                description = "관리자 페이지 REST API 문서",
                contact = @Contact(name = "AdminBridge", email = "support@chopping.kr")
        ),
        servers = {
                // 내장 톰캣 또는 프록시 뒤에 있을 때도 기본값이면 충분합니다.
                @Server(url = "/", description = "Default Server")
        },
        // 전역으로 BearerAuth 요구(엔드포인트별로 해제 가능)
        security = { @SecurityRequirement(name = "BearerAuth") }
)
@SecurityScheme(
        name = "BearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
@Configuration
public class SwaggerConfig {

    /**
     * 인증/인가 API 그룹
     * - com.chopping.adminbridge.auth.* 하위 컨트롤러 스캔
     * - Swagger UI 좌측 Tags에서 "auth" 그룹으로 구분
     */
    @Bean
    public GroupedOpenApi authApi() {
        return GroupedOpenApi.builder()
                .group("auth")
                .packagesToScan("com.chopping.adminbridge.auth")
                .build();
    }

    /**
     * 파일 업로드/관리 API 그룹
     */
    @Bean
    public GroupedOpenApi fileApi() {
        return GroupedOpenApi.builder()
                .group("file")
                .packagesToScan("com.chopping.adminbridge.file")
                .pathsToMatch("/upload/**")
                .build();
    }

    /**
     * 레시피 API 그룹 (REST API)
     */
    @Bean
    public GroupedOpenApi recipeApi() {
        return GroupedOpenApi.builder()
                .group("recipe")
                .packagesToScan("com.chopping.adminbridge.recipe.controller")
                .pathsToMatch("/api/recipe/**")
                .build();
    }

    /**
     * 홈 API 그룹 (REST API)
     */
    @Bean
    public GroupedOpenApi homeApi() {
        return GroupedOpenApi.builder()
                .group("home")
                .packagesToScan("com.chopping.adminbridge")
                .pathsToMatch("/api/home/**")
                .build();
    }

    /**
     * (선택) 전체 API 그룹 — 필요 없으면 제거 가능
     * - 여러 패키지를 한 번에 묶어 전체 문서 보기
     */
    @Bean
    public GroupedOpenApi allApi() {
        return GroupedOpenApi.builder()
                .group("all")
                .packagesToScan(
                        "com.chopping.adminbridge.auth",
                        "com.chopping.adminbridge.file",
                        "com.chopping.adminbridge.recipe.controller",
                        "com.chopping.adminbridge"
                )
                .pathsToMatch("/api/**", "/upload/**")
                .build();
    }
}
