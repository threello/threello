package com.sparta.threello.controller;


import com.sparta.threello.dto.CommentRequestDto;
import com.sparta.threello.dto.CreateCommentRequestDto;
import com.sparta.threello.dto.ResponseDataDto;
import com.sparta.threello.dto.ResponseMessageDto;
import com.sparta.threello.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;


    //댓글 생성
    @PostMapping("/cards/{cardId}/comments")
    public ResponseEntity<ResponseDataDto> createComment(@PathVariable Long cardId,
            @RequestBody CommentRequestDto requestDto,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(commentService.createComment(cardId, userDetails.getUser(),requestDto));
    }

    //전체 댓글 조회
    @GetMapping("/cards/{cardId}/comments")
    public ResponseEntity<ResponseDataDto> getComments(@PathVariable Long cardId) {
        return ResponseEntity.ok(commentService.getComments(cardId));
    }

    //특정 댓글 조회
    @GetMapping("/comments/{commentId}")
    public ResponseEntity<ResponseDataDto> getComment(@PathVariable Long commentId) {
        return ResponseEntity.ok(commentService.getComment(commentId));
    }

    //댓글 수정
    @PutMapping("/comments/{commentId}")
    public ResponseEntity<ResponseDataDto> updateComment(@PathVariable Long commentId,@RequestBody
            CommentRequestDto requestDto) {
        return ResponseEntity.ok(commentService.updateComment(commentId,requestDto));
    }

    //댓글 삭제
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<ResponseMessageDto> deleteComment(@PathVariable Long commentId) {
        return ResponseEntity.ok(commentService.deleteComment(commentId));
    }
}
