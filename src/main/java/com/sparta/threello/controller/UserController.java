package com.sparta.threello.controller;


import com.sparta.threello.entity.User;
import com.sparta.threello.jwt.JwtUtil;
import com.sparta.threello.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @Autowired
    public UserController(JwtUtil jwtUtil, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.userRepository=userRepository;
    }

    // 공통 응답 처리 Generic 메서드
    private <T> ResponseEntity<T> createResponse(T body, HttpStatus status) {
        return new ResponseEntity<>(body, status);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        // 헤더에서 토큰을 가져옴 ->
        String refreshToken = jwtUtil.getRefreshTokenFromHeader(request);

        // 토큰이 유효한지 확인
        if (StringUtils.hasText(refreshToken) && jwtUtil.validateToken(refreshToken)) {
            Claims refreshTokenClaims = jwtUtil.getUserInfoFromToken(refreshToken);
            String userId = refreshTokenClaims.getSubject();

            // 유저의 리프레시 토큰 삭제
            User user = userRepository.findByEmail(userId).orElse(null);
            if (user != null) {
                user.setRefreshToken(null);
                userRepository.save(user);
            }
        }

        // 클라이언트 측 토큰 삭제 요청을 위해 응답 설정
        return createResponse("Logged out successfully", HttpStatus.OK);
    }
}