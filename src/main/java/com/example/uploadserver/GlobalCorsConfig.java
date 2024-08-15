package com.example.uploadserver;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
public class GlobalCorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();

        // 允许所有源
        config.setAllowedOriginPatterns(List.of("*"));

        // 允许携带凭证
        config.setAllowCredentials(true);

        // 允许所有请求方法
        config.addAllowedMethod("*");

        // 允许所有头部
        config.addAllowedHeader("*");

        // 暴露所有头部
        config.addExposedHeader("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}

