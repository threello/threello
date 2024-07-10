package com.sparta.threello.dto;

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

    public DeckResponseDto(Long id,
                           Long board_id,
                           String title,
                           Long position,
                           LocalDateTime createdAt,
                           LocalDateTime modifiedAt) {
        this.id = id;
        this.board_id = board_id;
        this.title = title;
        this.position = position;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
