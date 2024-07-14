package com.sparta.threello.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sparta.threello.entity.CardDetail;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class GetCardDetailResponseDto {
    private Long id;
    private String deckTitle;
    private Long cardId;
    private String cardTitle;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dueAt;
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifiedAt;


    public GetCardDetailResponseDto(CardDetail cardDetail) {
        this.id = cardDetail.getId();
        this.deckTitle = cardDetail.getCard().getDeckTitle();
        this.cardId = cardDetail.getCard().getId();
        this.cardTitle = cardDetail.getCard().getTitle();
        this.dueAt = cardDetail.getCard().getDueAt();
        this.description = cardDetail.getDescription();
        this.createdAt = cardDetail.getCreatedAt();
        this.modifiedAt = cardDetail.getModifiedAt();
    }
}
