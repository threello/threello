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

    //댓글 생성
    public ResponseDataDto createComment(Long cardId, User user,
            CommentRequestDto requestDto) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_CARD));
        Comment comment = new Comment(requestDto.getDescription(), user, card);
        commentRepository.save(comment);
        CommentResponseDto commentResponseDto = new CommentResponseDto(comment);
        return new ResponseDataDto(ResponseStatus.COMMENT_CREATE_SUCCESS, commentResponseDto);
    }

    //전체 댓글 조회
    public ResponseDataDto getComments(Long cardId) {
        List<Comment> commentList = commentRepository.findAllByCardId(cardId);
        List<CommentResponseDto> commentResponseDtoList = commentList.stream()
                .map(CommentResponseDto::new).toList();
        return new ResponseDataDto(ResponseStatus.COMMENTS_READ_SUCCESS, commentResponseDtoList);
    }

    //특정 댓글 조회
    public ResponseDataDto getComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_COMMENT));
        CommentResponseDto commentResponseDto = new CommentResponseDto(comment);
        return new ResponseDataDto(ResponseStatus.COMMENT_READ_SUCCESS, commentResponseDto);
    }

    //댓글 수정
    @Transactional
    public ResponseDataDto updateComment(Long commentId, CommentRequestDto requestDto) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_COMMENT));
        comment.updateDescription(requestDto.getDescription());
        CommentResponseDto commentResponseDto = new CommentResponseDto(comment);
        return new ResponseDataDto(ResponseStatus.COMMENT_UPDATE_SUCCESS, commentResponseDto);
    }

    //댓글 삭제
    public ResponseMessageDto deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
        return new ResponseMessageDto(ResponseStatus.COMMENT_DELETE_SUCCESS);
    }

}
