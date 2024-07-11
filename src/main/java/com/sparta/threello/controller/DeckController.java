package com.sparta.threello.controller;

import com.sparta.threello.dto.DeckRequestDto;
import com.sparta.threello.dto.DeckResponseDto;
import com.sparta.threello.dto.ResponseDataDto;
import com.sparta.threello.enums.ResponseStatus;
import com.sparta.threello.service.DeckService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/boards/{boardId}/decks")
@RestController
public class DeckController {
    private final DeckService deckService;

    /**
     * 덱 생성
     * @param deckRequestDto 요청 객체
     * //@param userDetails 회원 정보
     * @return status.code, message
     **/
    @PostMapping
    public ResponseEntity<ResponseDataDto<DeckResponseDto>> createDeck(@PathVariable long boardId, @RequestBody DeckRequestDto deckRequestDto/*, @AuthenticationPrincipal UserDetailsImpl authentication*/) {
        ResponseDataDto<DeckResponseDto> responseDto = deckService.createDeck(boardId, deckRequestDto/*, authentication*/);
        return ResponseEntity.ok(new ResponseDataDto<>(ResponseStatus.DECK_CREATE_SUCCESS, responseDto).getData());
    }

    /**
     * 덱 전체 조회
     * @param page 페이지
     * @param size 페이지크기
     * @return status.code, message
     **/
    @GetMapping
    public ResponseEntity<ResponseDataDto<Page<DeckResponseDto>>> getDeckList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @PathVariable Long boardId) {
        ResponseDataDto<Page<DeckResponseDto>> responseDto = deckService.getDeckList(page, size, boardId);
        return ResponseEntity.ok(new ResponseDataDto<>(ResponseStatus.DECKS_READ_SUCCESS, responseDto).getData());
    }

    /**
     * 덱 조회
     * @param boardId 보드아이디
     * @param deckId 덱아이디
     * @return status.code, message
     **/
    @GetMapping("/{deckId}")
    public ResponseEntity<ResponseDataDto<DeckResponseDto>> getDeck(@PathVariable Long boardId, @PathVariable Long deckId) {
        ResponseDataDto<DeckResponseDto> responseDto = deckService.getDeck(boardId, deckId);
        return ResponseEntity.ok(new ResponseDataDto<>(ResponseStatus.DECK_READ_SUCCESS, responseDto).getData());
    }

    /**
     * 덱 수정
     * @param boardId 보드아이디
     * @param deckId 덱아이디
     * @return status.code, message
     **/
    @PutMapping("/{deckId}")
    public ResponseEntity<ResponseDataDto<DeckResponseDto>> updateDeck(@PathVariable Long boardId, @PathVariable Long deckId, @RequestBody String title) {
        ResponseDataDto<DeckResponseDto> responseDto = deckService.updateDeck(boardId, deckId, title);
        return ResponseEntity.ok(new ResponseDataDto<>(ResponseStatus.DECK_UPDATE_SUCCESS, responseDto).getData());
    }

}
