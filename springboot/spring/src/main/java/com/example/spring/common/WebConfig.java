package com.example.spring.common;

import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Resource
    private  JwtInterceptor jwtInterceptor;

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        //指定Controller统一接口前缀
        configurer.addPathPrefix("/api", clazz->clazz.isAnnotationPresent(RestController.class));
    }

//    添加自定义拦截器JWTInterceptor，设置拦截规则
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor).addPathPatterns("/api/**")
                .excludePathPatterns("/api/captcha")
                .excludePathPatterns("/api/admin/login");
    }
}
