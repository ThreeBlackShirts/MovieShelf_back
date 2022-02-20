package com.blackshirts.movieshelf.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    // 크로스 도메인 이슈를 해결하기 위한 코드
    //크로스 도메인 이슈: 브라우저에서 다른 도메인으로 URL 요청을 하는 경우 나타나는 보안문제
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
				.allowCredentials(true)
				.allowedOrigins("http://localhost:8080", "http://localhost:3000") // 원하는 도메인만 허용
				.allowedMethods("*") // 원하는 메소드만 허용 (GET, POST, ...)
				.allowedHeaders("*") // 원하는 헤더만 허용
				.allowCredentials(false); // 쿠키 요청 허용 (true -> 보안상 이슈 발생 가능)
    }
}