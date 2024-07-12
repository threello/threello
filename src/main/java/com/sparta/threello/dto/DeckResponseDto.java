package com.sparta.threello.dto;

import com.sparta.threello.entity.Deck;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class DeckResponseDto {
    private Long id;

    private Long board_id;

    private String title;

    private Long position;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;


    public DeckResponseDto(Deck deck) {
        this.id = deck.getId();
        this.board_id = deck.getBoard().getId();
        this.title = deck.getTitle();
        this.position = deck.getPosition();
        this.createdAt = deck.getCreatedAt();
        this.modifiedAt = deck.getModifiedAt();
    }

}
