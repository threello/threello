package com.sparta.threello.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sparta.threello.entity.Card;
import com.sparta.threello.enums.CardStatus;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class GetCardResponseDto {

    private Long deckId;

    private Long id;

    private String deckTitle;

    private CardStatus cardStatus;

    private String title;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dueAt;

    private Long position;

    public GetCardResponseDto(Card card) {
        this.deckId = card.getDeck().getId();
        this.id = card.getId();
        this.deckTitle = card.getDeckTitle();
        this.cardStatus = card.getCardStatus();
        this.title = card.getTitle();
        this.dueAt = card.getDueAt();
        this.position = card.getPosition();
    }
}
