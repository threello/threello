package com.sparta.threello.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;


@Getter
public class CommentRequestDto {
    @NotBlank
    private String description;
}
