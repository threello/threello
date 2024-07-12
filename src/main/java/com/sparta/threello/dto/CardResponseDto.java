package com.sparta.threello.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.sparta.threello.entity.Card;
import com.sparta.threello.entity.CardDetail;
import com.sparta.threello.entity.CardMember;
import com.sparta.threello.enums.CardStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public class CardResponseDto {
    private Long id;

    private Long deckId;

    private String cardDeckPosition;

    private CardStatus cardStatus;

    private String title;

    private List<String> cardManager;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dueAt;

    private Long position;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifiedAt;

    public CardResponseDto(Card card) {
        this.id = card.getId();
        this.deckId = card.getDeck().getId();
        this.cardDeckPosition = card.getCardDeckPosition();
        this.cardStatus = card.getCardStatus();
        this.title = card.getTitle();
        this.dueAt = card.getDueAt();
        this.position = card.getPosition();
        this.createdAt = card.getCreatedAt();
        this.modifiedAt = card.getModifiedAt();
        this.cardManager = card.getCardMembers().stream().map(cardMember -> cardMember.getUser().getName()).toList();
    }




}
