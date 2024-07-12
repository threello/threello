package com.sparta.threello.repository.cardRepository;

import com.sparta.threello.entity.Card;
import com.sparta.threello.enums.CardStatus;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CardRepository extends JpaRepository<Card, Long>, CardRepositoryCustom {
    List<Card> findAllByDeckId(Long DeckId, Sort sort);
    List<Card> findAllByCardStatus(CardStatus cardStatus, Sort sort);
}
