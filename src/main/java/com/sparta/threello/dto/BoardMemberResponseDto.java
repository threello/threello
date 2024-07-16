package com.sparta.threello.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BoardMemberResponseDto {
    private Long id;
    private String email;

    public BoardMemberResponseDto(Long boardId, String email) {
        this.id = boardId;
        this.email = email;
    }
}
