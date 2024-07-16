package com.sparta.threello.controller;


import com.sparta.threello.dto.*;
import com.sparta.threello.enums.ResponseStatus;
import com.sparta.threello.jwt.JwtProvider;
import com.sparta.threello.repository.UserRepository;
import com.sparta.threello.security.UserDetailsImpl;
import com.sparta.threello.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    private final UserService userService;

    /**
     * 회원가입
     * @param signupRequest 회원가입 정보
     * @return 회원 정보, 응답 상태
     */
    @PostMapping
    public ResponseEntity<ResponseMessageDto> signupUser(@RequestBody @Valid SignupRequestDto signupRequest) {
        System.out.println("test");
        userService.signupUser(signupRequest);
        return ResponseEntity.ok(new ResponseMessageDto(ResponseStatus.SIGN_UP_SUCCESS));
    }

    /**
     * 로그아웃
     *
     * @param details 회원 정보
     * @param request 요청 객체
     * @return 응답 상태
     */
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@AuthenticationPrincipal UserDetailsImpl details, HttpServletRequest request) {
        String accessToken = jwtProvider.getAccessTokenFromHeader(request);
        String refreshToken = jwtProvider.getRefreshTokenFromHeader(request);

        userService.logout(details.getUser(), accessToken, refreshToken);

        return ResponseEntity.ok(new ResponseMessageDto(ResponseStatus.LOGOUT_SUCCESS));
    }

    /**
     * 회원 상태 비활성화
     * @param requestDto 비밀번호
     * @param userDetails 회원 정보
     * @return 응답 상태
     */
    @PatchMapping
    public ResponseEntity<?> deactiveUser(@RequestBody PasswordRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        userService.deactivateUser(requestDto, userDetails.getUser());
        return ResponseEntity.ok(new ResponseMessageDto(ResponseStatus.DEACTIVATE_USER_SUCCESS));
    }

    /**
     * 회원 조회
     * @param userDetails 회원 정보
     * @return 응답 상태
     */
    @GetMapping
    public ResponseEntity<ResponseDataDto<UserProfileResponseDto>> getUserProfile(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        System.out.println("test");
        UserProfileResponseDto responseDto = userService.getUserProfile(userDetails.getUser());
        return ResponseEntity.ok(new ResponseDataDto<>(ResponseStatus.GET_USER_SUCCESS, responseDto));
    }
}