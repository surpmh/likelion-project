package com.likelion.likelionproject.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    DUPLICATED_USER_NAME(HttpStatus.CONFLICT, "Duplicate UserName.");

    private final HttpStatus httpStatus;
    private final String message;
}
