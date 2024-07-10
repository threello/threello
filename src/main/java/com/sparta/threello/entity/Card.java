package com.sparta.threello.entity;

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
    private String card_deck_position;

    @Column
    private LocalDateTime endAt;

    //Deck 과 join
    @ManyToOne
    @JoinColumn(name = "deck_id", nullable = false)
    private Deck deck;

    //CardDetail 과 join
    @OneToOne(mappedBy = "card", cascade = CascadeType.ALL, orphanRemoval = true)
    private CardDetail cardDetail;
}