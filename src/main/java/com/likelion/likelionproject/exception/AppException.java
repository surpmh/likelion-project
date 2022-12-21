package com.likelion.likelionproject.exception;

import com.likelion.likelionproject.enums.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AppException extends RuntimeException {
    private ErrorCode errorCode;
    private String message;
}
