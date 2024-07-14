package com.sparta.threello.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sparta.threello.entity.Comment;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class CommentResponseDto {
    private Long id;
    private Long userId;
    private Long cardId;
    private String username;
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifiedAt;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.userId = comment.getUser().getId();
        this.cardId = comment.getCard().getId();
        this.username = comment.getUser().getName();
        this.description = comment.getDescription();
        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
    }
}
