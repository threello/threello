package com.sparta.threello.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "deck")
public class Deck extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Long position;

    //Board와 join
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    //Card와 양방향
    @OneToMany(mappedBy = "deck",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Card> cardList = new ArrayList<>();
}