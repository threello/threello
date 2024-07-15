package com.sparta.threello.repository;

import com.sparta.threello.entity.CardMember;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardMemberRepository extends JpaRepository<CardMember, Long> {

    List<CardMember> findAllByCardId(Long cardId);
    Boolean existsCardMemberByCardIdAndUserId(Long cardId, Long userId);

}
