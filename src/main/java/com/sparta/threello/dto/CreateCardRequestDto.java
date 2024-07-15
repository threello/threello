package com.sparta.threello.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sparta.threello.entity.Card;
import com.sparta.threello.entity.CardDetail;
import com.sparta.threello.enums.CardStatus;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.springframework.validation.annotation.Validated;

@Getter
public class CreateCardRequestDto {

    @NotBlank(message="제목 입력은 필수입니다.")
    private String title;

    private Long position;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dueAt;


}
