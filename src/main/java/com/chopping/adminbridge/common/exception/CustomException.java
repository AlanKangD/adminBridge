package com.chopping.adminbridge.common.exception;

import lombok.Getter;

/**
 * 커스텀 예외 클래스
 */
@Getter
public class CustomException extends RuntimeException {
    
    private final String errorCode;
    private final String message;
    
    public CustomException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.message = message;
    }
    
    public CustomException(String message) {
        super(message);
        this.errorCode = "CUSTOM_ERROR";
        this.message = message;
    }
    
    public CustomException(String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        this.message = message;
    }
}
