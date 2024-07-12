package com.sparta.threello.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class BoardRequestDto {
    @NotBlank(message = "보드 이름을 입력해주세요.")
    @Size(min = 1, max = 10, message = "보드 이름은 최소 1글자에서 최대 10글자까지 가능합니다.")
    private String title;

    @NotBlank(message = "보드 내용을 입력해주세요.")
    private String description;
}
