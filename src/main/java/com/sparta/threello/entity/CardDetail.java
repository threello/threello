package com.sparta.threello.entity;

import com.sparta.threello.dto.CardDetailRequestDto;
import com.sparta.threello.dto.CreateCardRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

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



    /*연관관계 편의 메서드*/
    public CardDetail(Card card) {
        this.card=card;
        card.setCardDetail(this);
    }

    public CardDetail(CardDetailRequestDto requestDto, Card card) {
        this.description = requestDto.getDescription();
        this.card=card;
        card.setCardDetail(this);
    }


    public void updateCardDetail(CardDetailRequestDto requestDto) {
        this.description = requestDto.getDescription();
    }
}
