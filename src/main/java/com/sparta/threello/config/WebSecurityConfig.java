package com.sparta.threello.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests((auth) -> auth
                // 로그인, 메인 화면, 회원 가입 모든 사용자에게 허용
                .requestMatchers("/", "/users", "/users/login").permitAll()
                // 회원탈퇴
                .requestMatchers(HttpMethod.PATCH, "/users").authenticated()




        );
        return http.build();
    }
}
