package com.sparta.threello.dto;

import com.sparta.threello.enums.UserType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class SignupRequestDto {
    @Email(message = "올바른 이메일 형식을 입력해 주세요.")
    @NotBlank(message = "이메일을 입력해 주세요.")
    private String email;

    @NotBlank(message = "비밀번호를 입력해 주세요.")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*()_+\\-={}|:;<>?,./~`])[a-zA-Z0-9!@#$%^&*()_+\\-={}|:;<>?,./~`]{10,}$", message = "비밀번호는 대소문자 포함 영문, 숫자, 특수문자를 최소 1글자씩 포함해야 하며, 최소 10글자 이상이어야 합니다.")
    private String password;

    @NotBlank(message = "이름을 입력해 주세요.")
    private String name;

//    @NotBlank(message = "유저타입을 설정해 주세요.")
    private UserType userType;
}
