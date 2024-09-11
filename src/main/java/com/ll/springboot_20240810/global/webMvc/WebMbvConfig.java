package com.ll.springboot_20240810.global.webMvc;

import com.ll.springboot_20240810.global.interceptor.NeedToAdminInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMbvConfig implements WebMvcConfigurer {

    private final NeedToAdminInterceptor needToAdminInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(needToAdminInterceptor)
                .addPathPatterns("/adm/**");
    }
}
