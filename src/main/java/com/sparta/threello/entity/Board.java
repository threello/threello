package com.sparta.threello.entity;

import com.sparta.threello.dto.BoardRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "board", indexes = @Index(name = "idx_title", columnList = "title"))
public class Board extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    //BoardMember과 조인
    @OneToMany(mappedBy = "board",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BoardMember> boardMemberList = new ArrayList<>();

    //Deck과 양방향
    @OneToMany(mappedBy = "board",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Deck> deckList = new ArrayList<>();

    public Board(BoardRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.description = requestDto.getDescription();
    }

    public void update(BoardRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.description = requestDto.getDescription();
    }
}