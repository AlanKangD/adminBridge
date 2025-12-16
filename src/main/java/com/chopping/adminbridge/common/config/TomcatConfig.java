package com.chopping.adminbridge.common.config;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

import jakarta.servlet.MultipartConfigElement;

@Configuration
public class TomcatConfig {

    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> tomcatCustomizer() {
        return factory -> factory.addConnectorCustomizers(connector -> {
            // "maxFileCount" 속성을 설정하여 허용되는 최대 파일 개수를 늘립니다.
            // 예를 들어, 20개의 파일을 허용하려면 "20"으로 설정합니다.
            connector.setProperty("maxFileCount", "200");

            // Tomcat 커넥터의 최대 POST 크기 설정 (100MB = 104857600 bytes)
            connector.setProperty("maxPostSize", "104857600"); // 100MB
            connector.setProperty("maxSavePostSize", "104857600"); // 100MB
        });
    }

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        // 파일 크기 제한을 50MB로 증가 (WebP 변환 시 원본 파일이 클 수 있음)
        factory.setMaxFileSize(DataSize.ofMegabytes(50));
        // 요청 전체 크기 제한을 100MB로 유지
        factory.setMaxRequestSize(DataSize.ofMegabytes(100));

        // ⚠️ maxFileCount는 설정할 수 없음 → 직접 제한해야 함 (아래 방법 2)
        return factory.createMultipartConfig();
    }
}
