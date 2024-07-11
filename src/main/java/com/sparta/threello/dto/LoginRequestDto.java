package com.sparta.threello.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LoginRequestDto {

    @NotBlank(message = "email은 필수 입력값입니다")
    private final String email;
    @NotBlank(message = "password는 필수 입력값입니다")
    private final String password;

}