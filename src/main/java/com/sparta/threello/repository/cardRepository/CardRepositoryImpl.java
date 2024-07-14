package com.sparta.threello.repository.cardRepository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.threello.entity.Card;
import com.sparta.threello.entity.QCard;
import com.sparta.threello.entity.QCardMember;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CardRepositoryImpl implements CardRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Card> findAllByDeckIdAndUserId(Long deckId, Long userId){
        QCard card = QCard.card;
        QCardMember cardMember = QCardMember.cardMember;

        return queryFactory.select(card)
                .from(cardMember)
                .join(cardMember.card, card)
                .where(card.deck.id.eq(deckId)
                        .and(cardMember.user.id.eq(userId)))
                .orderBy(card.position.asc())
                .fetch();
    }
}
