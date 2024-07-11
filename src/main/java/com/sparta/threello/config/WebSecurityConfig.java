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

        http
                .authorizeHttpRequests((auth) -> auth
                    // 로그인, 메인 화면, 회원 가입 모든 사용자에게 허용
                    .requestMatchers("/", "/users", "/users/login").permitAll()
                    // 회원탈퇴
                    .requestMatchers(HttpMethod.PATCH, "/users").authenticated()


                );

        http
                .formLogin((auth)->auth
                        // 권한이 필요한 API 요청 페이지 이전에 나타나야할 로그인페이지를 설정
                        .loginPage("/users/login")
                        // 프론트에서 넘겨줘야하는 데이터를 로그인처리해주는 api요청 설정
                        .loginProcessingUrl("/loginProc")
                        .permitAll()
                );

        //csrf 설정 disable시킴
        http
                .csrf((auth) -> auth.disable());

        return http.build();
    }
}
