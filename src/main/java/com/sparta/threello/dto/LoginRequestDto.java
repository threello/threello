package com.sparta.threello.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class LoginRequestDto {
    @NotBlank(message = "email은 필수 입력값입니다")
    private String email;

    @NotBlank(message = "password는 필수 입력값입니다")
    private String password;
}