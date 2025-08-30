package com.chopping.adminbridge.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * JPA 설정 클래스
 */
@Configuration
@EnableJpaAuditing
@EnableJpaRepositories(basePackages = "com.chopping.adminbridge")
public class JpaConfig {
    // JPA Auditing 활성화
    // BaseEntity의 @CreatedDate, @LastModifiedDate 어노테이션이 자동으로 동작하도록 설정
}
