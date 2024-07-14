package com.sparta.threello.dto;

import lombok.Getter;

@Getter
public class UpdateCardPositionRequestDto {
    private String deckTitle;
    private Long position;
}
