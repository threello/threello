package com.sparta.threello.controller;


import com.sparta.threello.dto.CommentRequestDto;
import com.sparta.threello.dto.CommentResponseDto;
import com.sparta.threello.dto.ResponseDataDto;
import com.sparta.threello.dto.ResponseMessageDto;
import com.sparta.threello.security.UserDetailsImpl;
import com.sparta.threello.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cards/{cardId}/cardDetails/comments")
public class CommentController {

    private final CommentService commentService;

    // 댓글 생성
    @PostMapping
    public ResponseEntity<ResponseDataDto<CommentResponseDto>> createComment(
            @PathVariable Long cardId,
            @RequestBody CommentRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ResponseDataDto<CommentResponseDto> response = commentService.createComment(cardId, userDetails.getUser(), requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 전체 댓글 조회
    @GetMapping
    public ResponseEntity<ResponseDataDto<List<CommentResponseDto>>> getComments(@PathVariable Long cardId) {
        ResponseDataDto<List<CommentResponseDto>> response = commentService.getComments(cardId);
        return ResponseEntity.ok(response);
    }

    // 댓글 수정
    @PutMapping("/{commentId}")
    public ResponseEntity<ResponseDataDto<CommentResponseDto>> updateComment(
            @PathVariable Long cardId,
            @PathVariable Long commentId,
            @RequestBody CommentRequestDto requestDto) {
        ResponseDataDto<CommentResponseDto> response = commentService.updateComment(cardId, commentId, requestDto);
        return ResponseEntity.ok(response);
    }

    // 댓글 삭제
    @DeleteMapping("/{commentId}")
    public ResponseEntity<ResponseMessageDto> deleteComment(
            @PathVariable Long cardId,
            @PathVariable Long commentId) {
        ResponseMessageDto response = commentService.deleteComment(cardId, commentId);
        return ResponseEntity.ok(response);
    }
}