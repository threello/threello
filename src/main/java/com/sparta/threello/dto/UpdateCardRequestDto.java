package com.sparta.threello.dto;


import com.sparta.threello.enums.CardStatus;
import lombok.Getter;

@Getter
public class UpdateCardRequestDto {
    private String title;
    private CardStatus cardStatus;
}
