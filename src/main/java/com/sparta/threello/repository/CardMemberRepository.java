package com.sparta.threello.repository;

import com.sparta.threello.entity.CardMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardMemberRepository extends JpaRepository<CardMember, Long> {

}
