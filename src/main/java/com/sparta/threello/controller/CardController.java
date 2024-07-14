package com.sparta.threello.controller;


import com.sparta.threello.dto.*;
import com.sparta.threello.entity.Card;
import com.sparta.threello.entity.User;
import com.sparta.threello.repository.cardRepository.CardRepository;
import com.sparta.threello.security.UserDetailsImpl;
import com.sparta.threello.service.CardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.sparta.threello.entity.QUser.user;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;
    private final CardRepository cardRepository;

    //카드 생성
    @PostMapping("/decks/{deckId}/cards")
    public ResponseEntity<ResponseDataDto> createCard(
            @PathVariable Long deckId,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @Valid @RequestBody CreateCardRequestDto requestDto) {
        User user = userDetails.getUser();
        ResponseDataDto responseDto =cardService.createCard(deckId, requestDto,user);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(responseDto);
    }

    //카드 전체 조회(deck별)
    @GetMapping("/decks/{deckId}/cards")
    public ResponseEntity<ResponseDataDto> getCards(@PathVariable Long deckId) {
        return ResponseEntity.ok(cardService.getCards(deckId));
    }

    //특정 카드 조회
    @GetMapping("/cards/{cardId}")
    public ResponseEntity<ResponseDataDto> getCard(@PathVariable Long cardId) {
        return ResponseEntity.ok(cardService.getCard(cardId));
    }

    //작업자별 카드 조회(덱별)
    @GetMapping("/decks/{deckId}/cards/{userId}")
    public ResponseEntity<ResponseDataDto> getUserCards(@PathVariable Long deckId,
            @PathVariable Long userId) {
        return ResponseEntity.ok(cardService.getUserCards(deckId, userId));
    }

    //상태별 카드 조회
    @GetMapping("/decks/{deckId}/cards/status")
    public ResponseEntity<ResponseDataDto> getUserCards(@PathVariable Long deckId,
            @RequestBody GetStatusCardRequestDto requestDto) {
        return ResponseEntity.ok(cardService.getStatusCards(deckId, requestDto));
    }

    //카드 수정
    @PutMapping("/cards/{cardId}")
    public ResponseEntity<ResponseDataDto> updateCard(@PathVariable Long cardId,
            @RequestBody UpdateCardRequestDto requestDto) {
        return ResponseEntity.ok(cardService.updateCard(cardId, requestDto));
    }

    //카드 포지션 변경
    @PatchMapping("/cards/{cardId}")
    public ResponseEntity<ResponseDataDto> updateCardPosition(@PathVariable Long cardId,
            @RequestBody UpdateCardPositionRequestDto requestDto) {
        return ResponseEntity.ok(cardService.updateCardPosition(cardId, requestDto));
    }

    //카드 삭제
    @DeleteMapping("/cards/{cardId}")
    public ResponseEntity<ResponseMessageDto> deleteCard(@PathVariable Long cardId) {
        return ResponseEntity.ok(cardService.deleteCard(cardId));
    }

}
