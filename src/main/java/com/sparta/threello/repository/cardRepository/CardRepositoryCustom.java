package com.sparta.threello.repository.cardRepository;

import com.sparta.threello.entity.Card;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepositoryCustom {
    List<Card> findAllByDeckIdAndUserId(Long DeckId, Long UserId);
}
