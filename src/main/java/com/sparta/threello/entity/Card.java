package com.sparta.threello.entity;

import com.sparta.threello.dto.CreateCardRequestDto;
import com.sparta.threello.dto.UpdateCardPositionRequestDto;
import com.sparta.threello.dto.UpdateCardRequestDto;
import com.sparta.threello.enums.CardStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "card")
public class Card extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Long position;

    //카드상태의 default값을 정해줌
    @Enumerated(EnumType.STRING)
    @Column(name= "card_status",nullable = false)
    private CardStatus cardStatus;

    //deck 의 title 값이 들어가야함
    @Column(name= "card_deck_position",nullable = false)
    private String cardDeckPosition;

    //마감일자
    @Column
    private LocalDateTime dueAt;

    //Deck 과 join
    @ManyToOne
    @JoinColumn(name = "deck_id", nullable = false)
    private Deck deck;

    //CardDetail 과 join
    @OneToOne(mappedBy = "card", cascade = CascadeType.ALL, orphanRemoval = true)
    private CardDetail cardDetail;

    public Card(CreateCardRequestDto requestDto, Deck deck) {
        this.title = requestDto.getTitle();
        this.deck=deck;
        this.cardStatus=requestDto.getCardStatus();
        this.position = requestDto.getPosition();
        this.cardDeckPosition = deck.getTitle();
    }

//    public Card(String title, Long position, CardStatus cardStatus, Deck deck) {
//        this.title = title;
//        this.position = position;
//        this.cardStatus = cardStatus;
//        this.cardDeckPosition = deck.getTitle();
//        this.deck = deck;
//    }

    public void update(UpdateCardRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.cardStatus = requestDto.getCardStatus();
    }

    public void updatePosition(UpdateCardPositionRequestDto requestDto) {
        this.cardDeckPosition = requestDto.getCardDeckPosition();
        this.position = requestDto.getPosition();
    }


    /*
    * 연관관계 편의 메서드
    * */
    public void setCardDetail(CardDetail cardDetail) {
        this.cardDetail=cardDetail;
    }
}