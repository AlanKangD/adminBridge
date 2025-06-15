package com.chopping.adminbridge.security;

import com.chopping.adminbridge.member.entity.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails implements UserDetails {

    private final Member member;

    public CustomUserDetails(Member member) {
        this.member = member;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_" + member.getRole())
        );
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getEmail(); // 혹은 member.getEmail() 등
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // 계정 만료 여부 로직
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // 계정 잠김 여부 로직
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 자격 증명 만료 여부
    }

    @Override
    public boolean isEnabled() {
        return member.getUseYn().equalsIgnoreCase("Y");
    }

    public Member getMember() {
        return member;
    }
}