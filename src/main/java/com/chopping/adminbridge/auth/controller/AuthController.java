package com.chopping.adminbridge.auth.controller;

import com.chopping.adminbridge.auth.dto.LoginRequest;
import com.chopping.adminbridge.member.entity.Member;
import com.chopping.adminbridge.security.CustomUserDetails;
import com.chopping.adminbridge.security.jwt.JwtTokenProvider;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            // 암호화해서 INSERT 하기
            String encodedPw = new BCryptPasswordEncoder().encode("1234");
            System.out.println(encodedPw);


            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            Member member = ((CustomUserDetails) authentication.getPrincipal()).getMember();
            String token = jwtTokenProvider.generateToken(member);

            return ResponseEntity.ok().body(Map.of(
                    "accessToken", token,
                    "memberId", member.getMemberId(),
                    "role", member.getRole()
            ));
        } catch (Exception e) {
            e.printStackTrace(); // ❗ 콘솔에 실제 오류 찍힘
            return ResponseEntity.status(401).body(Map.of("error", e.getMessage()));
        }
    }
}