package com.ll.springboot_20240810.global.webMvc;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMbvConfig implements WebMvcConfigurer {

//    @Override
//    public void addInterceptors(InterceptorRegistry registry){
//        registry.addInterceptor(needToLoginInterceptor)
//                .addPathPatterns("/adm/**")
//                .addPathPatterns("/article/write")
//                .addPathPatterns("/article/modify/**")
//                .addPathPatterns("/article/delete/**");;
//        registry.addInterceptor(needToAdminInterceptor)
//                .addPathPatterns("/adm/**");
//        registry.addInterceptor(needToLogoutInterceptor)
//                .addPathPatterns("/member/login")
//                .addPathPatterns("/member/join")
//                .addPathPatterns("/member/findUsername")
//                .addPathPatterns("/member/findPassword");
//    }
}
