package com.chopping.adminbridge;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/home")
@Tag(name = "Home API", description = "홈 및 공통 REST API")
public class HomeRestController {
    
    @GetMapping("/login")
    @Operation(summary = "로그인 상태 확인", description = "현재 사용자의 로그인 상태를 확인합니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "성공"),
        @ApiResponse(responseCode = "401", description = "인증되지 않음")
    })
    public ResponseEntity<?> checkLoginStatus() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) {
            return ResponseEntity.ok().body("이미 로그인된 사용자입니다. 사용자: " + auth.getName());
        }
        return ResponseEntity.ok().body("로그인이 필요합니다.");
    }

    @GetMapping("/status")
    @Operation(summary = "시스템 상태 확인", description = "시스템의 현재 상태를 확인합니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "시스템 정상")
    })
    public ResponseEntity<?> getSystemStatus() {
        return ResponseEntity.ok().body("시스템이 정상적으로 동작 중입니다.");
    }
}
