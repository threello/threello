package com.sparta.threello.dto;

import com.sparta.threello.entity.CardMember;
import lombok.Getter;

@Getter
public class CardMemberResponseDto {
    private Long userId;
    private String username;

    public CardMemberResponseDto(CardMember cardMember) {
        this.userId = cardMember.getUser().getId();
        this.username = cardMember.getUser().getName();
    }
}
