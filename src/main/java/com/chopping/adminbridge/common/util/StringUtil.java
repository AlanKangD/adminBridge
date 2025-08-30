package com.chopping.adminbridge.common.util;

import java.util.regex.Pattern;

/**
 * 문자열 관련 유틸리티 클래스
 */
public class StringUtil {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"
    );
    
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(
        "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$"
    );

    /**
     * 문자열이 null이거나 빈 문자열인지 확인
     */
    public static boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    /**
     * 문자열이 null이 아니고 빈 문자열이 아닌지 확인
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * 이메일 형식 검증
     */
    public static boolean isValidEmail(String email) {
        if (isEmpty(email)) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email).matches();
    }

    /**
     * 비밀번호 형식 검증 (최소 8자, 대소문자, 숫자, 특수문자 포함)
     */
    public static boolean isValidPassword(String password) {
        if (isEmpty(password)) {
            return false;
        }
        return PASSWORD_PATTERN.matcher(password).matches();
    }

    /**
     * 문자열을 카멜케이스로 변환
     */
    public static String toCamelCase(String str) {
        if (isEmpty(str)) {
            return str;
        }
        
        StringBuilder result = new StringBuilder();
        boolean nextUpper = false;
        
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            
            if (c == '_' || c == '-') {
                nextUpper = true;
            } else {
                if (nextUpper) {
                    result.append(Character.toUpperCase(c));
                    nextUpper = false;
                } else {
                    result.append(Character.toLowerCase(c));
                }
            }
        }
        
        return result.toString();
    }

    /**
     * 문자열을 스네이크케이스로 변환
     */
    public static String toSnakeCase(String str) {
        if (isEmpty(str)) {
            return str;
        }
        
        StringBuilder result = new StringBuilder();
        
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            
            if (Character.isUpperCase(c)) {
                if (i > 0) {
                    result.append('_');
                }
                result.append(Character.toLowerCase(c));
            } else {
                result.append(c);
            }
        }
        
        return result.toString();
    }

    /**
     * 문자열을 파스칼케이스로 변환
     */
    public static String toPascalCase(String str) {
        if (isEmpty(str)) {
            return str;
        }
        
        String camelCase = toCamelCase(str);
        return Character.toUpperCase(camelCase.charAt(0)) + camelCase.substring(1);
    }

    /**
     * 문자열을 지정된 길이로 자르고 말줄임표 추가
     */
    public static String truncate(String str, int maxLength) {
        if (isEmpty(str) || str.length() <= maxLength) {
            return str;
        }
        
        return str.substring(0, maxLength - 3) + "...";
    }

    /**
     * 문자열에서 HTML 태그 제거
     */
    public static String removeHtmlTags(String str) {
        if (isEmpty(str)) {
            return str;
        }
        
        return str.replaceAll("<[^>]*>", "");
    }

    /**
     * 문자열에서 특수문자 제거
     */
    public static String removeSpecialCharacters(String str) {
        if (isEmpty(str)) {
            return str;
        }
        
        return str.replaceAll("[^a-zA-Z0-9가-힣\\s]", "");
    }

    /**
     * 문자열을 숫자로 변환 (안전한 방법)
     */
    public static Integer toInteger(String str) {
        if (isEmpty(str)) {
            return null;
        }
        
        try {
            return Integer.parseInt(str.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * 문자열을 Long으로 변환 (안전한 방법)
     */
    public static Long toLong(String str) {
        if (isEmpty(str)) {
            return null;
        }
        
        try {
            return Long.parseLong(str.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * 문자열을 Double로 변환 (안전한 방법)
     */
    public static Double toDouble(String str) {
        if (isEmpty(str)) {
            return null;
        }
        
        try {
            return Double.parseDouble(str.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * 문자열을 Boolean으로 변환 (안전한 방법)
     */
    public static Boolean toBoolean(String str) {
        if (isEmpty(str)) {
            return null;
        }
        
        String lowerStr = str.trim().toLowerCase();
        return "true".equals(lowerStr) || "1".equals(lowerStr) || "yes".equals(lowerStr);
    }
}
