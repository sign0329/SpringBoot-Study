package com.ll.springboot_20240810.global.interceptor;

import com.ll.springboot_20240810.global.rq.Rq;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class NeedToLoginInterceptor implements HandlerInterceptor {
    private final Rq rq;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        if (!rq.isLogined()){
            throw new RuntimeException("로그인 후 이용해주세요");}
        return true;
    }
}
