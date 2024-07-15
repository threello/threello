package com.sparta.threello.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.threello.dto.ExceptionDto;
import com.sparta.threello.dto.LoginRequestDto;
import com.sparta.threello.dto.ResponseMessageDto;
import com.sparta.threello.entity.User;
import com.sparta.threello.enums.ErrorType;
import com.sparta.threello.enums.ResponseStatus;
import com.sparta.threello.enums.UserStatus;
import com.sparta.threello.enums.UserType;
import com.sparta.threello.exception.CustomException;
import com.sparta.threello.jwt.JwtProvider;
import com.sparta.threello.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

// 로그인 요청 처리 로직
@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;

    public JwtAuthenticationFilter(JwtProvider jwtProvider, UserRepository userRepository) {
        this.jwtProvider = jwtProvider;
        this.userRepository = userRepository;
        setFilterProcessesUrl("/users/login"); //UsernamePasswordAuthenticationFilter을 상속 받으면 사용할수잇음
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("로그인 시도");
        try {
            LoginRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(), LoginRequestDto.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestDto.getEmail(),
                            requestDto.getPassword(),
                            null
                    )
            );
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    //로그인 성공 처리 로직
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.info("로그인 성공 및 JWT 생성");
        User user = ((UserDetailsImpl) authResult.getPrincipal()).getUser();

        //탈퇴한 회원인지 검증 로직
        if (UserStatus.DEACTIVATE.equals(user.getUserStatus())) {
            throw new CustomException(ErrorType.DEACTIVATE_USER);
        }

        String email = ((UserDetailsImpl) authResult.getPrincipal()).getUsername();
        UserType userType = ((UserDetailsImpl) authResult.getPrincipal()).getUser().getUserType();

        String accessToken = jwtProvider.createToken(email, userType);
        String refreshToken = jwtProvider.createRefreshToken(email, userType);

        User userImpl = ((UserDetailsImpl) authResult.getPrincipal()).getUser();
        userImpl.saveRefreshToken(refreshToken.substring(7));
        userRepository.save(userImpl);


        response.addHeader(JwtProvider.AUTH_ACCESS_HEADER, accessToken);
        response.addHeader(JwtProvider.AUTH_REFRESH_HEADER, refreshToken);
        log.info("accessToken = {}", accessToken);
        log.info("refreshToken = {}", refreshToken);


        // 로그인 성공 메시지
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new ObjectMapper().writeValueAsString(new ResponseMessageDto(ResponseStatus.LOGIN_SUCCESS)));
        response.getWriter().flush();
    }


    //로그인 실패 처리 로직
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        ErrorType errorType = ErrorType.NOT_FOUND_AUTHENTICATION_INFO;
        response.setStatus(errorType.getHttpStatus().value());
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write(new ObjectMapper().writeValueAsString(new ExceptionDto(errorType)));
        response.getWriter().flush();
    }
}
