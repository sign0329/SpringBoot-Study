package com.ll.springboot_20240810.global.security;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;


@Configuration
public class SecurityConfig {
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(
                        csrf -> csrf
                                .ignoringRequestMatchers(
                                        PathRequest.toH2Console()
                                )
                )
                .headers(
                        headers -> headers
                                .addHeaderWriter(
                                        new XFrameOptionsHeaderWriter(XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN)
                                )

                )
                .authorizeHttpRequests(
                        authorizeHttpRequests -> authorizeHttpRequests
                                .requestMatchers("adm/**")
                                .hasRole("ADMIN")
                                .anyRequest()
                                .permitAll()
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/member/login") // 커스텀 로그인 페이지
                        .defaultSuccessUrl("/") // 로그인 성공 시 리다이렉트
                )
                .logout(logout -> logout
                        .logoutUrl("/member/logout")
                        .logoutSuccessUrl("/") // 로그아웃 성공 시 리다이렉트
                )
                .build();
    }


    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}



