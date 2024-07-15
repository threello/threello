package com.sparta.threello.entity;

import com.sparta.threello.dto.CreateCardRequestDto;
import com.sparta.threello.dto.UpdateCardRequestDto;
import com.sparta.threello.enums.CardStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "card")
public class Card extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Long position;

    //카드상태의 default값을 정해줌
    @Enumerated(EnumType.STRING)
    @Column(name = "card_status", nullable = false)
    private CardStatus cardStatus;

    //deck 의 title 값이 들어가야함
    @Column(name = "deck_title", nullable = false)
    private String deckTitle;

    //마감일자
    @Column
    private LocalDate dueAt;

    //Deck 과 join
    @ManyToOne
    @JoinColumn(name = "deck_id", nullable = false)
    private Deck deck;

    //CardDetail 과 join
    @OneToOne(mappedBy = "card", cascade = CascadeType.ALL, orphanRemoval = true)
    private CardDetail cardDetail;

    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CardMember> cardMembers = new ArrayList<>();

    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    public Card(CreateCardRequestDto requestDto, Deck deck) {

        this.title = requestDto.getTitle();
        this.position = requestDto.getPosition();
        this.cardStatus = CardStatus.PROCESSING;
        this.deckTitle = deck.getTitle();
        this.dueAt = requestDto.getDueAt();
        this.deck = deck;
    }

//    public Card(String title, Long position, CardStatus cardStatus, Deck deck) {
//        this.title = title;
//        this.position = position;
//        this.cardStatus = cardStatus;
//        this.deckTitle = deck.getTitle();
//        this.deck = deck;
//    }

    public void updateCard(UpdateCardRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.cardStatus = requestDto.getCardStatus();
        this.dueAt = requestDto.getDueAt();
    }

    public void updatePosition(Deck deck, Long position) {
        setDeck(deck);
        this.position = position;

    }

    /*
     * 연관관계 편의 메서드
     * */
    public void setCardDetail(CardDetail cardDetail) {
        this.cardDetail = cardDetail;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
        deck.getCardList().add(this);
        this.deckTitle = this.deck.getTitle();
    }


}