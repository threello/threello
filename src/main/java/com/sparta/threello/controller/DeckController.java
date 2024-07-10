package com.sparta.threello.controller;

import com.sparta.threello.dto.DeckRequestDto;
import com.sparta.threello.dto.DeckResponseDto;
import com.sparta.threello.dto.ResponseDataDto;
import com.sparta.threello.enums.ResponseStatus;
import com.sparta.threello.service.DeckService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/boards/{id}/decks")
@RestController
public class DeckController {
    private final DeckService deckService;

    /**
     * 덱 생성
     * @param deckRequestDto 요청 객체
     * @param userDetails 회원 정보
     * @return status.code, message
     **/
    @PostMapping
    public ResponseEntity<ResponseDataDto<DeckResponseDto>> createDeck(@PathVariable long id, @RequestBody DeckRequestDto deckRequestDto/*, @AuthenticationPrincipal UserDetailsImpl authentication*/) {
        ResponseDataDto<DeckResponseDto> responseDto = deckService.createDeck(id, deckRequestDto/*, authentication*/);
        return ResponseEntity.ok(new ResponseDataDto<>(ResponseStatus.DECK_CREATE_SUCCESS, responseDto).getData());
    }

    /**
     * 덱 전체 조회
     * @param page 보더 id
     * @return status.code, message
     **/
//    @GetMapping
//    public ResponseEntity<Page<ResponseDataDto<Deck>>> getDeckList(@RequestParam(value = "page") int page) {
//        Page<ResponseDataDto<Deck>> responseDto = deckService.getDeckList(page - 1);
//        return ResponseEntity.ok(new ResponseDataDto<>(ResponseStatus.DECKS_READ_SUCCESS, responseDto).getData());
//    }
}
