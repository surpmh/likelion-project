package com.likelion.likelionproject.service;

import com.likelion.likelionproject.dto.UserDto;
import com.likelion.likelionproject.dto.UserJoinRequest;
import com.likelion.likelionproject.entity.User;
import com.likelion.likelionproject.exception.AppException;
import com.likelion.likelionproject.enums.ErrorCode;
import com.likelion.likelionproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    public UserDto join(UserJoinRequest request) {
        userRepository.findByUserName(request.getUserName())
                .ifPresent(user -> {
                    throw new AppException(ErrorCode.DUPLICATED_USER_NAME, String.format("%s is duplicated.", request.getUserName()));
                });

        User savedUser = userRepository.save(request.toEntity());

        return UserDto.builder()
                .id(savedUser.getId())
                .userName(savedUser.getUserName())
                .password(savedUser.getPassword())
                .build();
    }
}