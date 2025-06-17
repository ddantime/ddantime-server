package com.ddantime.ddantime.config;

import com.ddantime.ddantime.common.interceptor.UUIDAuthInterceptor;
import com.ddantime.ddantime.common.resolver.RequestUserArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final UUIDAuthInterceptor uuidAuthInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(uuidAuthInterceptor)
                .addPathPatterns("/api/v1/**").excludePathPatterns("/api/v1/users");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new RequestUserArgumentResolver());
    }

}
