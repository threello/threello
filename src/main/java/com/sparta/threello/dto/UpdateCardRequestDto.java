package com.sparta.threello.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.sparta.threello.enums.CardStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class UpdateCardRequestDto {

    @NotBlank(message="제목 입력은 필수입니다.")
    private String title;

    private CardStatus cardStatus;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dueAt;
}
