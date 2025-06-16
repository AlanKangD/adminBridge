package com.chopping.adminbridge.auth.controller;

import com.chopping.adminbridge.auth.dto.LoginRequest;
import com.chopping.adminbridge.auth.dto.SignupRequest;
import com.chopping.adminbridge.member.entity.Member;
import com.chopping.adminbridge.member.repository.MemberRepository;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/secure")
    public ResponseEntity<String> securedEndpoint(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return ResponseEntity.ok("✅ 인증된 사용자: " + userDetails.getUsername());
    }
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest request) {
        /// API 통신으로 회원가입을 처리하도록 구성
        // 이메일 중복 확인
        if (memberRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("이미 존재하는 이메일입니다.");
        }

        // id 중복 확인
        if (memberRepository.findByMemberId(request.getMemberId()).isPresent()) {
            return ResponseEntity.badRequest().body("이미 존재하는 아이디입니다.");
        }

        // 비밀번호 암호화 후 저장
        Member newMember = Member.builder()
                .memberId(request.getMemberId())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .nickname(request.getNickname())
                .role("ROLE_USER")
                .useYn("Y")
                .build();

        memberRepository.save(newMember);
        return ResponseEntity.ok("회원가입이 완료되었습니다.");
    }

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