package com.likelion.likelionproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserLoginResponse {
    private String userName;
    private String password;
    private String token;
}
