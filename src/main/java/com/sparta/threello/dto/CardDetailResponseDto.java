package com.sparta.threello.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sparta.threello.entity.CardDetail;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class CardDetailResponseDto {
    private Long id;
    private Long cardId;
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifiedAt;

    public CardDetailResponseDto(CardDetail cardDetail) {
        this.id = cardDetail.getId();
        this.cardId = cardDetail.getCard().getId();
        this.description = cardDetail.getDescription();
        this.createdAt = cardDetail.getCreatedAt();
        this.modifiedAt = cardDetail.getModifiedAt();
    }
}
