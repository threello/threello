package com.sparta.threello.repository;

import com.sparta.threello.entity.CardDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  CardDetailRepository extends JpaRepository<CardDetail, Long> {

}
