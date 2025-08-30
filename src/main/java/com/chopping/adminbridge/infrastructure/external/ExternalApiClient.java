package com.chopping.adminbridge.infrastructure.external;

import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.chopping.adminbridge.common.exception.CustomException;

import lombok.extern.slf4j.Slf4j;

/**
 * 외부 API 연동을 위한 기본 클래스
 */
@Slf4j
public abstract class ExternalApiClient {

    protected final RestTemplate restTemplate;
    protected final String baseUrl;
    protected final HttpHeaders defaultHeaders;

    protected ExternalApiClient(RestTemplate restTemplate, String baseUrl) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
        this.defaultHeaders = new HttpHeaders();
        this.defaultHeaders.setContentType(MediaType.APPLICATION_JSON);
    }

    /**
     * GET 요청
     */
    protected <T> T get(String endpoint, Class<T> responseType) {
        return get(endpoint, null, responseType);
    }

    /**
     * GET 요청 (쿼리 파라미터 포함)
     */
    protected <T> T get(String endpoint, Map<String, String> queryParams, Class<T> responseType) {
        try {
            String url = buildUrl(endpoint, queryParams);
            log.info("External API GET request: {}", url);
            
            HttpEntity<String> entity = new HttpEntity<>(defaultHeaders);
            ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.GET, entity, responseType);
            
            log.info("External API GET response: {}", response.getStatusCode());
            return response.getBody();
        } catch (Exception e) {
            log.error("External API GET request failed: {}", e.getMessage(), e);
            throw new CustomException("EXTERNAL_API_ERROR", "외부 API 호출에 실패했습니다.");
        }
    }

    /**
     * POST 요청
     */
    protected <T> T post(String endpoint, Object requestBody, Class<T> responseType) {
        try {
            String url = buildUrl(endpoint, null);
            log.info("External API POST request: {}", url);
            
            HttpEntity<Object> entity = new HttpEntity<>(requestBody, defaultHeaders);
            ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.POST, entity, responseType);
            
            log.info("External API POST response: {}", response.getStatusCode());
            return response.getBody();
        } catch (Exception e) {
            log.error("External API POST request failed: {}", e.getMessage(), e);
            throw new CustomException("EXTERNAL_API_ERROR", "외부 API 호출에 실패했습니다.");
        }
    }

    /**
     * PUT 요청
     */
    protected <T> T put(String endpoint, Object requestBody, Class<T> responseType) {
        try {
            String url = buildUrl(endpoint, null);
            log.info("External API PUT request: {}", url);
            
            HttpEntity<Object> entity = new HttpEntity<>(requestBody, defaultHeaders);
            ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.PUT, entity, responseType);
            
            log.info("External API PUT response: {}", response.getStatusCode());
            return response.getBody();
        } catch (Exception e) {
            log.error("External API PUT request failed: {}", e.getMessage(), e);
            throw new CustomException("EXTERNAL_API_ERROR", "외부 API 호출에 실패했습니다.");
        }
    }

    /**
     * DELETE 요청
     */
    protected void delete(String endpoint) {
        try {
            String url = buildUrl(endpoint, null);
            log.info("External API DELETE request: {}", url);
            
            HttpEntity<String> entity = new HttpEntity<>(defaultHeaders);
            ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.DELETE, entity, Void.class);
            
            log.info("External API DELETE response: {}", response.getStatusCode());
        } catch (Exception e) {
            log.error("External API DELETE request failed: {}", e.getMessage(), e);
            throw new CustomException("EXTERNAL_API_ERROR", "외부 API 호출에 실패했습니다.");
        }
    }

    /**
     * URL 빌드
     */
    protected String buildUrl(String endpoint, Map<String, String> queryParams) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl + endpoint);
        
        if (queryParams != null) {
            queryParams.forEach(builder::queryParam);
        }
        
        return builder.toUriString();
    }

    /**
     * 헤더 추가
     */
    protected void addHeader(String key, String value) {
        defaultHeaders.add(key, value);
    }

    /**
     * 헤더 설정
     */
    protected void setHeader(String key, String value) {
        defaultHeaders.set(key, value);
    }

    /**
     * 인증 토큰 설정
     */
    protected void setAuthToken(String token) {
        setHeader("Authorization", "Bearer " + token);
    }

    /**
     * API 키 설정
     */
    protected void setApiKey(String apiKey) {
        setHeader("X-API-Key", apiKey);
    }
}
