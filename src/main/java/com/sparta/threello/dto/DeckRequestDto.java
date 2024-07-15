package com.sparta.threello.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class DeckRequestDto {
    @NotBlank(message = "컬럼 타이틀을 입력해주세요.")
    private String title;
    private Long position;
}
