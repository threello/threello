package com.sparta.threello.controller;

import com.sparta.threello.dto.*;
import com.sparta.threello.entity.Deck;
import com.sparta.threello.enums.ResponseStatus;
import com.sparta.threello.security.UserDetailsImpl;
import com.sparta.threello.service.DeckService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/boards/{boardId}/decks")
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
    public ResponseEntity<ResponseDataDto<DeckResponseDto>> createDeck(
            @PathVariable long boardId,
            @Valid @RequestBody DeckRequestDto deckRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ResponseDataDto<DeckResponseDto> responseDto = deckService.createDeck(
                boardId, deckRequestDto, userDetails.getUser());
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
    public ResponseEntity<ResponseDataDto<DeckResponseDto>> getDeck(
            @PathVariable Long boardId,
            @PathVariable Long deckId) {
        ResponseDataDto<DeckResponseDto> responseDto = deckService.getDeck(boardId, deckId);
        return ResponseEntity.ok(new ResponseDataDto<>(ResponseStatus.DECK_READ_SUCCESS, responseDto).getData());
    }

    /**
     * 덱 수정
     * @param boardId 보드아이디
     * @param deckId 덱아이디
     * @param requestDto DeckRequestDto
     * @return status.code, message
     **/
    @PatchMapping("/{deckId}")
    public ResponseEntity<ResponseDataDto<DeckResponseDto>> updateDeck(
            @PathVariable Long boardId,
            @PathVariable Long deckId,
            @RequestBody DeckRequestDto requestDto) {
        ResponseDataDto<DeckResponseDto> responseDto = deckService.updateDeck(boardId, deckId, requestDto);
        return ResponseEntity.ok(new ResponseDataDto<>(ResponseStatus.DECK_UPDATE_SUCCESS, responseDto).getData());
    }

    /**
     * 덱 삭제
     *
     * @param boardId 보드아이디
     * @param deckId  덱아이디
     * @return status.code, message
     **/
    @DeleteMapping("/{deckId}")
    public ResponseEntity<ResponseMessageDto> deleteDeck(
            @PathVariable Long boardId,
            @PathVariable Long deckId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        deckService.deleteDeck(boardId, deckId, userDetails.getUser());
        return ResponseEntity.ok(new ResponseMessageDto(ResponseStatus.DECK_DELETE_SUCCESS));
    }

    /**
     * 덱 포지션 변경
     *
     * @param boardId 보드아이디
     * @param deckId  덱아이디
     * @param requestDto DeckRequestDto
     * @return status.code, message
     **/
    @PatchMapping("/{deckId}/position")
    public ResponseEntity<ResponseDataDto<DeckResponseDto>> updateDeckPosition(
            @PathVariable Long boardId,
            @PathVariable Long deckId,
            @RequestBody DeckRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ResponseDataDto<DeckResponseDto> responseDto =
                deckService.updateDeckPosition(boardId, deckId, requestDto, userDetails.getUser());
        return ResponseEntity.ok(new ResponseDataDto<>(ResponseStatus.DECK_UPDATE_SUCCESS, responseDto).getData());
    }

}
