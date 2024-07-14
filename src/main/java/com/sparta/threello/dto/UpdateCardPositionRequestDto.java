package com.sparta.threello.dto;

import lombok.Getter;

@Getter
public class UpdateCardPositionRequestDto {
    private Long deckId;
    private Long position;
}
