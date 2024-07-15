package com.sparta.threello.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sparta.threello.entity.Card;
import com.sparta.threello.enums.CardStatus;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class UpdateCardPositionResponseDto {
    private Long id;
    private Long deckId;
    private String deckTitle;
    private CardStatus cardStatus;
    private String title;
    private Long position;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created_at;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modified_at;

    public UpdateCardPositionResponseDto(Card card) {
        this.id = card.getId();
        this.deckId = card.getDeck().getId();
        this.deckTitle = card.getDeckTitle();
        this.title = card.getTitle();
        this.cardStatus = card.getCardStatus();
        this.position = card.getPosition();
        this.created_at = card.getCreatedAt();
        this.modified_at = card.getModifiedAt();
    }
}
