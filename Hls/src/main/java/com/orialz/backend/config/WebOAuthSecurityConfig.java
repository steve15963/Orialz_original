package com.orialz.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

public class WebOAuthSecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 인가(접근 권한) 설정
        http.authorizeHttpRequests().antMatchers("/").permitAll(); // 어떤 사용자든 접근 가능
        http.cors();
        return http.build();
    }
    }
