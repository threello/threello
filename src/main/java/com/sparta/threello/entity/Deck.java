package com.sparta.threello.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "deck", indexes = @Index(name = "idx_title", columnList = "title"))
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

    public Deck(String title, Long position, Optional<Board> board) {
        this.title = title;
        this.position = position;
        this.board = board.get();
    }

    public void updateTitle(String title) {
        this.title = title;
    }

    public void updatePosition(Long position) {
        this.position = position;
    }
}