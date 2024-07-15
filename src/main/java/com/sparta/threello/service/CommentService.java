package com.sparta.threello.service;

import com.sparta.threello.dto.CommentRequestDto;
import com.sparta.threello.dto.CommentResponseDto;
import com.sparta.threello.dto.ResponseDataDto;
import com.sparta.threello.dto.ResponseMessageDto;
import com.sparta.threello.entity.Card;
import com.sparta.threello.entity.Comment;
import com.sparta.threello.entity.User;
import com.sparta.threello.enums.ErrorType;
import com.sparta.threello.enums.ResponseStatus;
import com.sparta.threello.exception.CustomException;
import com.sparta.threello.repository.CommentRepository;
import com.sparta.threello.repository.cardRepository.CardRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final CardRepository cardRepository;

    // 댓글 생성
    public ResponseDataDto<CommentResponseDto> createComment(Long cardId, User user, CommentRequestDto requestDto) {
        Card card = getCardById(cardId);
        Comment comment = new Comment(requestDto.getDescription(), user, card);
        commentRepository.save(comment);
        return new ResponseDataDto<>(ResponseStatus.COMMENT_CREATE_SUCCESS, new CommentResponseDto(comment));
    }

    // 전체 댓글 조회
    public ResponseDataDto<List<CommentResponseDto>> getComments(Long cardId) {
        List<Comment> commentList = commentRepository.findAllByCardId(cardId);
        List<CommentResponseDto> commentResponseDtoList = commentList.stream()
                .map(CommentResponseDto::new)
                .toList();
        return new ResponseDataDto<>(ResponseStatus.COMMENTS_READ_SUCCESS, commentResponseDtoList);
    }

    // 댓글 수정
    @Transactional
    public ResponseDataDto<CommentResponseDto> updateComment(Long cardId, Long commentId, CommentRequestDto requestDto) {
        Comment comment = getCommentByIdAndCardId(commentId, cardId);
        comment.updateDescription(requestDto.getDescription());
        return new ResponseDataDto<>(ResponseStatus.COMMENT_UPDATE_SUCCESS, new CommentResponseDto(comment));
    }

    // 댓글 삭제
    @Transactional
    public ResponseMessageDto deleteComment(Long cardId, Long commentId) {
        getCommentByIdAndCardId(commentId, cardId);
        commentRepository.deleteById(commentId);
        return new ResponseMessageDto(ResponseStatus.COMMENT_DELETE_SUCCESS);
    }

    // 공통 메서드

    // 카드 조회
    private Card getCardById(Long cardId) {
        return cardRepository.findById(cardId)
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_CARD));
    }

    // 댓글과 카드 검증 및 댓글 조회
    private Comment getCommentByIdAndCardId(Long commentId, Long cardId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_COMMENT));
        if (!cardId.equals(comment.getCard().getId())) {
            throw new CustomException(ErrorType.NOT_FOUND_COMMENT_IN_CARD);
        }
        return comment;
    }
}