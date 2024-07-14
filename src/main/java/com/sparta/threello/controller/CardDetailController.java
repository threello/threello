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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CardDetailController {

    private final CardDetailService cardDetailService;

    //카드 상세 조회
    @GetMapping("/cards/{cardId}/cardDetails")
    public ResponseEntity<ResponseDataDto> getCardDetail(@PathVariable Long cardId) {
        return ResponseEntity.ok(cardDetailService.getCardDetail(cardId));
    }

    //카드 상세 수정
    @PatchMapping("/cards/{cardId}/cardDetails")
    public ResponseEntity<ResponseDataDto> updateCardDetail(@PathVariable Long cardId,
            @RequestBody CardDetailRequestDto requestDto) {
        return ResponseEntity.ok(cardDetailService.updateCardDetail(cardId,requestDto));
    }

}