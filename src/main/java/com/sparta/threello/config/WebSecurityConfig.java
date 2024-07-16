package com.sparta.threello.config;


import com.sparta.threello.enums.UserType;
import com.sparta.threello.jwt.JwtProvider;
import com.sparta.threello.repository.UserRepository;
import com.sparta.threello.security.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final JwtProvider jwtProvider;
    private final UserDetailsServiceImpl userDetailsService;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final UserRepository userRepository;

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtProvider, userRepository);
        filter.setAuthenticationManager(authenticationManager(authenticationConfiguration));
        return filter;
    }

    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter() {
        return new JwtAuthorizationFilter(jwtProvider, userDetailsService);
    }

    @Bean
    public JwtExceptionFilter jwtExceptionFilter() {
        return new JwtExceptionFilter();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // CSRF 설정
        http.csrf(AbstractHttpConfigurer::disable);

        // 기본 설정인 Session 방식은 사용하지 않고 JWT 방식을 사용하기 위한 설정
        http.sessionManagement((sessionManagement) ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        http.authorizeHttpRequests((authorizeHttpRequests) ->
                authorizeHttpRequests
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll() // resources 접근 허용 설정
                        .requestMatchers("/").permitAll() // 요청 허가
                        .requestMatchers(HttpMethod.POST, "/boards/**").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.GET, "/boards/**").hasAnyRole("MANAGER", "USER")
                        .requestMatchers(HttpMethod.PUT, "/boards/**").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.DELETE, "/boards/**").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.POST, "/boards/**/invite").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.POST, "/columns/**").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.GET, "/columns/**").hasAnyRole("MANAGER", "USER")
                        .requestMatchers(HttpMethod.PUT, "/columns/**").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.DELETE, "/columns/**").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.POST, "/cards/**").hasAnyRole("MANAGER", "USER")
                        .requestMatchers(HttpMethod.GET, "/cards/**").hasAnyRole("MANAGER", "USER")
                        .requestMatchers(HttpMethod.PUT, "/cards/**").hasAnyRole("MANAGER", "USER")
                        .requestMatchers(HttpMethod.DELETE, "/cards/**").hasAnyRole("MANAGER", "USER")
                        .requestMatchers("/users/**").permitAll()
                        .anyRequest().authenticated() // 그 외 모든 요청 인증처리
        );

        // 인증 되지않은 유저 요청 시 동작
        http.exceptionHandling((exception) -> exception.authenticationEntryPoint(jwtAuthenticationEntryPoint));

        http.addFilterBefore(jwtAuthorizationFilter(), JwtAuthenticationFilter.class);
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(jwtExceptionFilter(), JwtAuthorizationFilter.class);

        return http.build();
    }
}