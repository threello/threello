package com.sparta.threello.repository;

import com.sparta.threello.entity.Deck;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeckRepository extends JpaRepository<Deck, Long> {
    Optional<Deck> findByTitle(String title);

    Page<Deck> findAllByBoardId(Long boardId, Pageable pageable);

    Deck findByIdAndBoardId(Long deckId, Long boardId);
}
