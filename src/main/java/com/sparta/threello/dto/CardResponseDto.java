package com.sparta.threello.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.sparta.threello.entity.Card;
import com.sparta.threello.enums.CardStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;

@Getter
public class CardResponseDto {
    private Long id;

    private Long deckId;

    private CardStatus cardStatus;

    private String title;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dueAt;

    private Long position;


    public CardResponseDto(Card card) {
        this.id = card.getId();
        this.deckId = card.getDeck().getId();
        this.cardStatus = card.getCardStatus();
        this.title = card.getTitle();
        this.dueAt = card.getDueAt();
        this.position = card.getPosition();
    }




}
