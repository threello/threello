package com.sparta.threello.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.sparta.threello.entity.Card;
import com.sparta.threello.enums.CardStatus;
import java.time.LocalDateTime;

public class CardResponseDto {
    private Long id;
    private String cardDeckPosition;
    private CardStatus cardStatus;
    private String title;
    private Long position;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifiedAt;

    public CardResponseDto(Card card) {
        this.id = card.getId();
        this.cardDeckPosition = card.getCardDeckPosition();
        this.cardStatus = card.getCardStatus();
        this.title = card.getTitle();
        this.position = card.getPosition();
        this.createdAt = card.getCreatedAt();
        this.modifiedAt = card.getModifiedAt();
    }
}
