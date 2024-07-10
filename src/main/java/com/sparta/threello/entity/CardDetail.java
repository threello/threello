package com.sparta.threello.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class CardDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String description;

    //Card 와 조인
    @OneToOne
    @JoinColumn(name = "card_id", nullable = false)
    private Card card;
}
