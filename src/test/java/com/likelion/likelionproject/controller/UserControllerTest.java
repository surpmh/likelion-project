package com.likelion.likelionproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.likelion.likelionproject.dto.UserDto;
import com.likelion.likelionproject.dto.UserJoinRequest;
import com.likelion.likelionproject.dto.UserLoginRequest;
import com.likelion.likelionproject.exception.AppException;
import com.likelion.likelionproject.enums.ErrorCode;
import com.likelion.likelionproject.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@WithMockUser
class UserControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    UserService userService;

    UserJoinRequest userJoinRequest = UserJoinRequest.builder()
            .userName("user")
            .password("1234")
            .build();

    @Test
    @DisplayName("회원가입 성공")
    void join_success() throws Exception {
        when(userService.join(any())).thenReturn(mock(UserDto.class));

        mockMvc.perform(post("/api/v1/users/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(userJoinRequest))
                        .with(csrf())
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("회원가입 실패 - userName 중복")
    void join_fail() throws Exception {
        when(userService.join(any())).thenThrow(new AppException(ErrorCode.DUPLICATED_USER_NAME, "Duplicate UserName."));

        mockMvc.perform(post("/api/v1/users/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(userJoinRequest))
                        .with(csrf())
                )
                .andDo(print())
                .andExpect(status().isConflict());
    }

    UserLoginRequest userLoginRequest = UserLoginRequest.builder()
            .userName("user")
            .password("1234")
            .build();

    @Test
    @DisplayName("로그인 성공")
    void login_success() throws Exception {
        when(userService.login(any())).thenReturn("token");

        mockMvc.perform(post("/api/v1/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(userLoginRequest))
                        .with(csrf())
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("로그인 실패 - userName 없음")
    void login_username_fail() throws Exception {
        when(userService.login(any()))
                .thenThrow(new AppException(ErrorCode.USERNAME_NOT_FOUND, "UserName not found."));

        mockMvc.perform(post("/api/v1/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(userLoginRequest))
                        .with(csrf())
                )
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("로그인 실패 - password 틀림")
    void login_password_fail() throws Exception {
        when(userService.login(any()))
                .thenThrow(new AppException(ErrorCode.INVALID_PASSWORD, "Invalid password."));

        mockMvc.perform(post("/api/v1/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(userLoginRequest))
                        .with(csrf())
                )
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }
}