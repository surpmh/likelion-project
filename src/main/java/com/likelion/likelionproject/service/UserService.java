package com.likelion.likelionproject.service;

import com.likelion.likelionproject.dto.UserDto;
import com.likelion.likelionproject.dto.UserJoinRequest;
import com.likelion.likelionproject.dto.UserLoginRequest;
import com.likelion.likelionproject.entity.User;
import com.likelion.likelionproject.exception.AppException;
import com.likelion.likelionproject.enums.ErrorCode;
import com.likelion.likelionproject.repository.UserRepository;
import com.likelion.likelionproject.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Value("${jwt.secret}")
    private String key;
    private Long expireTimeMx = 3600000L;

    public UserDto join(UserJoinRequest request) {
        userRepository.findByUserName(request.getUserName())
                .ifPresent(user -> {
                    throw new AppException(ErrorCode.DUPLICATED_USER_NAME, String.format("%s is duplicated.", request.getUserName()));
                });

        User savedUser = userRepository.save(request.toEntity(encoder.encode(request.getPassword())));

        return UserDto.builder()
                .id(savedUser.getId())
                .userName(savedUser.getUserName())
                .password(savedUser.getPassword())
                .build();
    }

    public String login(UserLoginRequest request) {
        User selectedUser = userRepository.findByUserName(request.getUserName())
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND, ErrorCode.USERNAME_NOT_FOUND.getMessage()));

        if (!encoder.matches(request.getPassword(), selectedUser.getPassword())) {
            throw new AppException(ErrorCode.INVALID_PASSWORD, ErrorCode.INVALID_PASSWORD.getMessage());
        }

        return JwtUtil.createToken(selectedUser.getUserName(), key, expireTimeMx);
    }
}