package com.sparta.threello.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sparta.threello.entity.Card;
import com.sparta.threello.enums.CardStatus;
import java.time.LocalDateTime;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class CardPerStatusResponseDto {

    private Long id;
    private Long deckId;
    private String deckTitle;
    private CardStatus cardStatus;
    private String title;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dueAt;
    private Long position;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifiedAt;

    public CardPerStatusResponseDto(Card card) {
        this.id = card.getId();
        this.deckId = card.getDeck().getId();
        this.deckTitle = card.getDeckTitle();
        this.cardStatus = card.getCardStatus();
        this.title = card.getTitle();
        this.dueAt = card.getDueAt();
        this.position = card.getPosition();
        this.createAt = card.getCreatedAt();
        this.modifiedAt = card.getModifiedAt();
    }
}
