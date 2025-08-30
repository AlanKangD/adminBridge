package com.chopping.adminbridge.common.constant;

/**
 * 공통 상수 클래스
 */
public class CommonConstants {

    // 사용자 상태
    public static final String USER_STATUS_ACTIVE = "Y";
    public static final String USER_STATUS_INACTIVE = "N";
    
    // 사용자 역할
    public static final String ROLE_USER = "ROLE_USER";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    
    // 레시피 상태
    public static final String RECIPE_STATUS_ACTIVE = "Y";
    public static final String RECIPE_STATUS_INACTIVE = "N";
    public static final String RECIPE_STATUS_DELETED = "D";
    
    // 파일 관련
    public static final String UPLOAD_PATH = "/uploads/";
    public static final String IMAGE_PATH = "/images/";
    public static final String DOCUMENT_PATH = "/documents/";
    
    // 페이지네이션
    public static final int DEFAULT_PAGE_SIZE = 10;
    public static final int MAX_PAGE_SIZE = 100;
    
    // 날짜 형식
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String TIME_FORMAT = "HH:mm:ss";
    
    // HTTP 상태 메시지
    public static final String SUCCESS_MESSAGE = "성공적으로 처리되었습니다.";
    public static final String FAILURE_MESSAGE = "처리 중 오류가 발생했습니다.";
    public static final String NOT_FOUND_MESSAGE = "요청한 데이터를 찾을 수 없습니다.";
    public static final String UNAUTHORIZED_MESSAGE = "인증이 필요합니다.";
    public static final String FORBIDDEN_MESSAGE = "접근 권한이 없습니다.";
    
    // 정규식 패턴
    public static final String EMAIL_PATTERN = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    public static final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
    public static final String PHONE_PATTERN = "^01[0-9]-[0-9]{4}-[0-9]{4}$";
    
    // 파일 크기 제한 (바이트)
    public static final long MAX_FILE_SIZE = 10 * 1024 * 1024; // 10MB
    public static final long MAX_IMAGE_SIZE = 5 * 1024 * 1024; // 5MB
    
    // 허용된 파일 확장자
    public static final String[] ALLOWED_IMAGE_EXTENSIONS = {".jpg", ".jpeg", ".png", ".gif", ".bmp"};
    public static final String[] ALLOWED_DOCUMENT_EXTENSIONS = {".pdf", ".doc", ".docx", ".txt"};
    
    // 세션 관련
    public static final String SESSION_USER_KEY = "user";
    public static final int SESSION_TIMEOUT = 30 * 60; // 30분
    
    // 캐시 관련
    public static final String CACHE_CATEGORY = "category";
    public static final String CACHE_RECIPE = "recipe";
    public static final int CACHE_TTL = 3600; // 1시간
    
    // 로그 관련
    public static final String LOG_PREFIX = "[AdminBridge]";
    public static final String LOG_SEPARATOR = " | ";
    
    // API 응답 코드
    public static final String SUCCESS_CODE = "SUCCESS";
    public static final String ERROR_CODE = "ERROR";
    public static final String VALIDATION_ERROR_CODE = "VALIDATION_ERROR";
    public static final String AUTH_ERROR_CODE = "AUTH_ERROR";
    public static final String NOT_FOUND_ERROR_CODE = "NOT_FOUND";
    public static final String INTERNAL_ERROR_CODE = "INTERNAL_ERROR";
}
