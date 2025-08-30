package com.chopping.adminbridge.common.constant;

/**
 * API 응답 코드 상수 클래스
 */
public class ApiResponseCode {

    // 성공 응답
    public static final String SUCCESS = "SUCCESS";
    public static final String CREATED = "CREATED";
    public static final String UPDATED = "UPDATED";
    public static final String DELETED = "DELETED";

    // 클라이언트 오류 (4xx)
    public static final String BAD_REQUEST = "BAD_REQUEST";
    public static final String UNAUTHORIZED = "UNAUTHORIZED";
    public static final String FORBIDDEN = "FORBIDDEN";
    public static final String NOT_FOUND = "NOT_FOUND";
    public static final String METHOD_NOT_ALLOWED = "METHOD_NOT_ALLOWED";
    public static final String CONFLICT = "CONFLICT";
    public static final String UNSUPPORTED_MEDIA_TYPE = "UNSUPPORTED_MEDIA_TYPE";
    public static final String VALIDATION_ERROR = "VALIDATION_ERROR";

    // 서버 오류 (5xx)
    public static final String INTERNAL_SERVER_ERROR = "INTERNAL_SERVER_ERROR";
    public static final String SERVICE_UNAVAILABLE = "SERVICE_UNAVAILABLE";

    // 비즈니스 로직 오류
    public static final String USER_NOT_FOUND = "USER_NOT_FOUND";
    public static final String USER_ALREADY_EXISTS = "USER_ALREADY_EXISTS";
    public static final String INVALID_CREDENTIALS = "INVALID_CREDENTIALS";
    public static final String ACCOUNT_LOCKED = "ACCOUNT_LOCKED";
    public static final String ACCOUNT_DISABLED = "ACCOUNT_DISABLED";
    
    public static final String RECIPE_NOT_FOUND = "RECIPE_NOT_FOUND";
    public static final String RECIPE_ALREADY_EXISTS = "RECIPE_ALREADY_EXISTS";
    public static final String RECIPE_DELETED = "RECIPE_DELETED";
    
    public static final String FILE_NOT_FOUND = "FILE_NOT_FOUND";
    public static final String FILE_UPLOAD_FAILED = "FILE_UPLOAD_FAILED";
    public static final String FILE_DELETE_FAILED = "FILE_DELETE_FAILED";
    public static final String INVALID_FILE_TYPE = "INVALID_FILE_TYPE";
    public static final String FILE_SIZE_EXCEEDED = "FILE_SIZE_EXCEEDED";

    // 인증/인가 관련
    public static final String TOKEN_EXPIRED = "TOKEN_EXPIRED";
    public static final String TOKEN_INVALID = "TOKEN_INVALID";
    public static final String TOKEN_MISSING = "TOKEN_MISSING";
    public static final String INSUFFICIENT_PERMISSIONS = "INSUFFICIENT_PERMISSIONS";

    // 데이터베이스 관련
    public static final String DATABASE_ERROR = "DATABASE_ERROR";
    public static final String DATA_INTEGRITY_VIOLATION = "DATA_INTEGRITY_VIOLATION";
    public static final String DUPLICATE_KEY = "DUPLICATE_KEY";

    // 외부 서비스 관련
    public static final String EXTERNAL_SERVICE_ERROR = "EXTERNAL_SERVICE_ERROR";
    public static final String EXTERNAL_SERVICE_TIMEOUT = "EXTERNAL_SERVICE_TIMEOUT";
    public static final String EXTERNAL_SERVICE_UNAVAILABLE = "EXTERNAL_SERVICE_UNAVAILABLE";

    // 시스템 관련
    public static final String SYSTEM_MAINTENANCE = "SYSTEM_MAINTENANCE";
    public static final String RATE_LIMIT_EXCEEDED = "RATE_LIMIT_EXCEEDED";
    public static final String RESOURCE_EXHAUSTED = "RESOURCE_EXHAUSTED";
}
