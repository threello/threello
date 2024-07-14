package com.sparta.threello.dto;

import com.sparta.threello.entity.Card;
import lombok.Getter;

@Getter
public class UpdateCardPositionResponseDto {
    private Long deckId;
    private Long position;

    public UpdateCardPositionResponseDto(Card card) {
        this.deckId = card.getDeck().getId();
        this.position = card.getPosition();
    }
}
