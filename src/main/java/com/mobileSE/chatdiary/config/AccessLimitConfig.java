package com.mobileSE.chatdiary.config;


import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class AccessLimitConfig implements WebMvcConfigurer {

    @Resource
    private AccessLimitInterceptor accessLimitInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry
                .addInterceptor(accessLimitInterceptor)
                .addPathPatterns("/**")
                // 对登录和退出接口放行
                .excludePathPatterns("/login", "/logout");
    }
}

