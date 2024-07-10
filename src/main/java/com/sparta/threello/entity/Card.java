package com.sparta.threello.entity;

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

    @Column(nullable = false)
    private CardStatus card_status = CardStatus.PROCESSING;

    //deck 의 title 값이 들어가야함
    @Column(nullable = false)
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
}