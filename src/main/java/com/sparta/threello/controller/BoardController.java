package com.sparta.threello.controller;

import com.sparta.threello.dto.BoardRequestDto;
import com.sparta.threello.dto.BoardResponseDto;
import com.sparta.threello.dto.ResponseDataDto;
import com.sparta.threello.enums.ResponseStatus;
import com.sparta.threello.service.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    /**
     * [createBoard] 보드 생성
     * @param requestDto 요청 객체
     * @param userDetails 회원 정보
     * @return status.code, message, responseDto
     **/
//    @PostMapping
//    public ResponseEntity<ResponseDataDto<BoardResponseDto>> createBoard(@Valid @RequestBody BoardRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
//        BoardResponseDto responseDto = boardService.createBoard(requestDto, userDetails.getUser());
//        return ResponseEntity.ok(new ResponseDataDto<>(ResponseStatus.BOARD_CREATE_SUCCESS, responseDto));
//    }
}
