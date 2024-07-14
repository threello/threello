package com.sparta.threello.controller;

import com.sparta.threello.dto.CardDetailRequestDto;
import com.sparta.threello.dto.ResponseDataDto;
import com.sparta.threello.dto.ResponseMessageDto;
import com.sparta.threello.service.CardDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CardDetailController {

    private final CardDetailService cardDetailService;

    //카드 상세 생성
    @PostMapping("/cards/{cardId}/cardDetails")
    public ResponseEntity<ResponseDataDto> createCardDetail(@PathVariable Long cardId,
            @RequestBody CardDetailRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(cardDetailService.createCardDetail(cardId, requestDto));
    }

    //카드 상세 조회
    @GetMapping("/cardDetails/{cardDetailsId}")
    public ResponseEntity<ResponseDataDto> getCardDetail(@PathVariable Long cardDetailsId) {
        return ResponseEntity.ok(cardDetailService.getCardDetail(cardDetailsId));
    }

    //카드 상세 수정
    @PutMapping("/cardDetails/{cardDetailsId}")
    public ResponseEntity<ResponseDataDto> updateCardDetail(@PathVariable Long cardDetailsId,
            @RequestBody CardDetailRequestDto requestDto) {
        return ResponseEntity.ok(cardDetailService.updateCardDetail(cardDetailsId,requestDto));
    }

    //카드 상세 삭제
    @DeleteMapping("/cardDetails/{cardDetailsId}")
    public ResponseEntity<ResponseMessageDto> deleteCardDetail(@PathVariable Long cardDetailsId) {
        return ResponseEntity.ok(cardDetailService.deleteCardDetail(cardDetailsId));
    }
}
