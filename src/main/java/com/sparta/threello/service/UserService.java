package com.sparta.threello.service;

import com.sparta.threello.dto.PasswordRequestDto;
import com.sparta.threello.dto.SignupRequestDto;
import com.sparta.threello.dto.SignupResponseDto;
import com.sparta.threello.dto.UserProfileResponseDto;
import com.sparta.threello.entity.User;
import com.sparta.threello.enums.ErrorType;
import com.sparta.threello.enums.UserStatus;
import com.sparta.threello.exception.CustomException;
import com.sparta.threello.jwt.JwtProvider;
import com.sparta.threello.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public void logout(User user, String accessToken, String refreshToken) {
        if (user == null) {
            throw new CustomException(ErrorType.LOGGED_OUT_TOKEN);
        }

        User foundUser = userRepository.findByEmail(user.getEmail()).orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_USER));

        foundUser.saveRefreshToken("");

        //블랙리스트 추가
        jwtProvider.addBlacklistToken(accessToken);
        jwtProvider.addBlacklistToken(refreshToken);
    }

    public SignupResponseDto signupUser(SignupRequestDto requestDto) {
        // 이메일 중복 검증 로직
        Optional<User> checkEmail = userRepository.findByEmail(requestDto.getEmail());
        if (checkEmail.isPresent()) {
            throw new CustomException(ErrorType.DUPLICATE_EMAIL);
        }

        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

        User user = new User(requestDto, encodedPassword);

        userRepository.save(user);

        return new SignupResponseDto(user);
    }

    //회원탈퇴
    @Transactional
    public void deactivateUser(PasswordRequestDto requestDto, User loginUser) {
        //존재하지 않는 회원이라면
        User user = userRepository.findByEmail(loginUser.getEmail()).orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_USER));

        //이미 탈퇴한 회원이라면
        if(user.getUserStatus().equals(UserStatus.DEACTIVATE)) {
            throw new CustomException(ErrorType.DEACTIVATE_USER);
        }

//        //비밀번호 일치 확인
        if (!passwordEncoder.matches(requestDto.getPassword(), loginUser.getPassword())) {
            throw new CustomException(ErrorType.INVALID_PASSWORD);
        }
        user.deactivateUser();
    }

    //유저정보 조회
    @Transactional
    public UserProfileResponseDto getUserProfile(User userLogin) {
        System.out.println(userLogin.getId());
        return new UserProfileResponseDto(userLogin);
    }
}

