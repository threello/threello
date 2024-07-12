package com.sparta.threello.security;

import com.sparta.threello.entity.User;
import com.sparta.threello.enums.ErrorType;
import com.sparta.threello.enums.UserType;
import com.sparta.threello.exception.CustomException;
import com.sparta.threello.jwt.JwtUtil;
import com.sparta.threello.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.sparta.threello.jwt.JwtUtil.BEARER_PREFIX;

@Slf4j(topic = "JWT 인가 필터")
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;

    public JwtAuthorizationFilter(JwtUtil jwtUtil, UserDetailsServiceImpl userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        String accessToken = jwtUtil.getAccessTokenFromHeader(request);

        if (StringUtils.hasText(accessToken)) {
            if (jwtUtil.validateToken(accessToken)) {
                validToken(accessToken);
            } else {
                invalidToken(request, response);
                return; // 유효하지 않은 토큰일 경우 여기서 처리 후 종료
            }
        }
        filterChain.doFilter(request, response);
    }

    private void validToken(String token) {
        Claims info = jwtUtil.getUserInfoFromToken(token);

        try {
            setAuthentication(info.getSubject());
        } catch (Exception e) {
            log.error("username = {}, message = {}", info.getSubject(), "인증 정보를 찾을 수 없습니다.");
            throw new CustomException(ErrorType.NOT_FOUND_AUTHENTICATION_INFO);
        }
    }

    private void invalidToken(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = jwtUtil.getRefreshTokenFromHeader(request);

        if (StringUtils.hasText(refreshToken)) {
            if (jwtUtil.validateToken(refreshToken) && jwtUtil.existRefreshToken(refreshToken)) {
                Claims info = jwtUtil.getUserInfoFromToken(refreshToken);

                UserType type = UserType.valueOf(info.get("type").toString());

                String newAccessToken = jwtUtil.createToken(info.getSubject(), type);

                jwtUtil.setHeaderAccessToken(response, newAccessToken);

                try {
                    setAuthentication(info.getSubject());
                } catch (Exception e) {
                    log.error("username = {}, message = {}", info.getSubject(), "인증 정보를 찾을 수 없습니다.");
                    throw new CustomException(ErrorType.NOT_FOUND_AUTHENTICATION_INFO);
                }
            } else {
                throw new CustomException(ErrorType.INVALID_REFRESH_TOKEN);
            }
        }
    }

    // 인증 처리
    private void setAuthentication(String email) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        // 예외 처리
        if (userDetails == null)  {
            throw new CustomException(ErrorType.NOT_FOUND_USER);
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    // 인증 객체 생성
    private Authentication createAuthentication(String email) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

}