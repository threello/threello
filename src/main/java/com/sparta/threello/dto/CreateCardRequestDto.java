package com.sparta.threello.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.validation.annotation.Validated;

@Getter
public class CreateCardRequestDto {
    @NotBlank(message="제목 입력은 필수입니다.")
    private String title;
    @NotBlank(message="카드덱포지션 입력은 필수입니다.")
    private String cardDeckPosition;
    private Long position;
}
