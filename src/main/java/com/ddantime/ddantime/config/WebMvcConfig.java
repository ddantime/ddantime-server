package com.ddantime.ddantime.config;

import com.ddantime.ddantime.common.interceptor.UUIDAuthInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final UUIDAuthInterceptor uuidAuthInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(uuidAuthInterceptor)
                .addPathPatterns("/api/v1/**").excludePathPatterns("/api/v1/users");
    }

}
