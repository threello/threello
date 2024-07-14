package com.sparta.threello.controller;


import com.sparta.threello.dto.CardMemberRequestDto;
import com.sparta.threello.dto.CreateCardRequestDto;
import com.sparta.threello.dto.GetStatusCardRequestDto;
import com.sparta.threello.dto.ResponseDataDto;
import com.sparta.threello.dto.ResponseMessageDto;
import com.sparta.threello.dto.UpdateCardPositionRequestDto;
import com.sparta.threello.dto.UpdateCardRequestDto;
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
import org.springframework.web.bind.annotation.*;

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
        ResponseDataDto responseDto = cardService.createCard(deckId, requestDto, user);
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
    @PatchMapping("/cards/{cardId}")
    public ResponseEntity<ResponseDataDto> updateCard(@PathVariable Long cardId,
            @RequestBody UpdateCardRequestDto requestDto) {
        return ResponseEntity.ok(cardService.updateCard(cardId, requestDto));
    }

    //카드 포지션 변경
    @PatchMapping("/decks/{deckId}/cards/{cardId}")
    public ResponseEntity<ResponseDataDto> updateCardPosition(@PathVariable Long deckId,
            @PathVariable Long cardId, @RequestBody UpdateCardPositionRequestDto requestDto) {
        return ResponseEntity.ok(cardService.updateCardPosition(deckId,cardId, requestDto));
    }

    //카드 삭제
    @DeleteMapping("/cards/{cardId}")
    public ResponseEntity<ResponseMessageDto> deleteCard(@PathVariable Long cardId) {
        return ResponseEntity.ok(cardService.deleteCard(cardId));
    }


    //카드 멤버 초대
    @PostMapping("/cards/{cardId}/cardDetails/invite")
    public ResponseEntity<ResponseMessageDto> inviteCardMember(@PathVariable Long cardId,
            @RequestBody CardMemberRequestDto requestDto) {
        return ResponseEntity.ok(cardService.inviteCardMember(cardId,requestDto));
    }

    //카드 멤버 전체 조회
    @GetMapping("/cards/{cardId}/cardDetails/cardMembers")
    public ResponseEntity<ResponseDataDto> getCardMembers(@PathVariable Long cardId) {
        return ResponseEntity.ok(cardService.getCardMembers(cardId));
    }

    //카드 멤버 삭제
    @DeleteMapping("/cards/{cardId}/cardDetails/cardMembers/{cardMemberId}")
    public ResponseEntity<ResponseMessageDto> deleteCardMember(@PathVariable Long cardId,
            @PathVariable Long cardMemberId) {
        return ResponseEntity.ok(cardService.deleteCardMember(cardId,cardMemberId));
    }
}
