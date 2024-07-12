package com.sparta.threello.entity;

import com.sparta.threello.dto.CardDetailRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class CardDetail extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String description;

    //Card 와 조인
    @OneToOne
    @JoinColumn(name = "card_id", nullable = false)
    private Card card;

    public CardDetail(String description, Card card) {
        this.description = description;
        this.card = card;
    }


    public void updateCardDetail(CardDetailRequestDto requestDto) {
        this.description = requestDto.getDescription();
    }
}
