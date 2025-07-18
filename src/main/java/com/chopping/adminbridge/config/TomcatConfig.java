package com.chopping.adminbridge.config;

import jakarta.servlet.MultipartConfigElement;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

@Configuration
public class TomcatConfig {

    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> tomcatCustomizer() {
        return factory -> factory.addConnectorCustomizers(connector -> {
            // "maxFileCount" 속성을 설정하여 허용되는 최대 파일 개수를 늘립니다.
            // 예를 들어, 20개의 파일을 허용하려면 "20"으로 설정합니다.
            connector.setProperty("maxFileCount", "200");

            // 필요하다면 다른 설정도 여기에 추가할 수 있습니다.
            // connector.setProperty("maxPostSize", "10485760"); // 10MB
            // connector.setProperty("maxSavePostSize", "10485760"); // 10MB
        });
    }

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(DataSize.ofMegabytes(10));
        factory.setMaxRequestSize(DataSize.ofMegabytes(100));

        // ⚠️ maxFileCount는 설정할 수 없음 → 직접 제한해야 함 (아래 방법 2)
        return factory.createMultipartConfig();
    }
}
