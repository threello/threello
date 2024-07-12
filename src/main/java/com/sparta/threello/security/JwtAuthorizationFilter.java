package com.sparta.threello.security;

import com.sparta.threello.enums.ErrorType;
import com.sparta.threello.enums.UserType;
import com.sparta.threello.exception.CustomException;
import com.sparta.threello.jwt.JwtProvider;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j(topic = "JWT 인가 필터")
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final UserDetailsServiceImpl userDetailsService;

    public JwtAuthorizationFilter(JwtProvider jwtProvider, UserDetailsServiceImpl userDetailsService) {
        this.jwtProvider = jwtProvider;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,@NonNull HttpServletResponse response,@NonNull FilterChain filterChain) throws ServletException, IOException {
        String accessToken = jwtProvider.getAccessTokenFromHeader(request);

        if (StringUtils.hasText(accessToken)) {
            if (jwtProvider.validateToken(accessToken)) {
                validToken(accessToken, request);
            } else {
                invalidToken(response);
                return; // 유효하지 않은 토큰일 경우 여기서 처리 후 종료
            }
        }
        filterChain.doFilter(request, response);
    }

    private void validToken(String token, HttpServletRequest request) {
        Claims info = jwtProvider.getUserInfoFromToken(token);

        try {
            setAuthentication(info.getSubject());
        } catch (Exception e) {
            String username = info != null ? info.getSubject() : "알 수 없음";
            log.error("username = {}, message = {}", username, "인증 정보를 찾을 수 없습니다.");
            throw new CustomException(ErrorType.NOT_FOUND_AUTHENTICATION_INFO);
        }
    }

    private void invalidToken(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = jwtProvider.getRefreshTokenFromHeader(request);

        if (StringUtils.hasText(refreshToken)) {
            if (jwtProvider.validateToken(refreshToken) && jwtProvider.existRefreshToken(refreshToken)) {
                Claims info = jwtProvider.getUserInfoFromToken(refreshToken);

                UserType type = UserType.valueOf(info.get("type").toString());

                String newAccessToken = jwtProvider.createToken(info.getSubject(), type);

                jwtProvider.setHeaderAccessToken(response, newAccessToken);

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

    private void invalidToken(HttpServletResponse response) {
        // 유효하지 않은 토큰에 대한 처리 로직 추가 (예: 오류 응답)
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        log.error("유효하지 않은 토큰입니다.");
        throw new CustomException(ErrorType.INVALID_ACCESS_TOKEN);
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