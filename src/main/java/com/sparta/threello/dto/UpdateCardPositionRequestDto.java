package com.sparta.threello.dto;

import lombok.Getter;

@Getter
public class UpdateCardPositionRequestDto {
    private String cardDeckPosition;
    private Long position;
}
