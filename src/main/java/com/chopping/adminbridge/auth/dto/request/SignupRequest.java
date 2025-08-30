package com.chopping.adminbridge.auth.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequest {
    private String email;
    private String password;
    private String nickname;
    private String memberId;
}