package com.sparta.threello.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class InviteRequestDto {
    @NotBlank(message = "이메일을 입력해 주세요.")
    @Email(message = "이메일 형식을 입력해 주세요.")
    private String email;
}
