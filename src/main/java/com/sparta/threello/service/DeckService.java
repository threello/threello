package com.sparta.threello.service;

import com.sparta.threello.dto.*;
import com.sparta.threello.entity.Board;
import com.sparta.threello.entity.Deck;
import com.sparta.threello.entity.User;
import com.sparta.threello.enums.ErrorType;
import com.sparta.threello.enums.ResponseStatus;
import com.sparta.threello.enums.UserType;
import com.sparta.threello.exception.CustomException;
import com.sparta.threello.repository.DeckRepository;
import com.sparta.threello.repository.board.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeckService {

    private final DeckRepository deckRepository;
    private final BoardRepository boardRepository;

    /** [createDeck()] 덱 생성
     * @param boardId
     * @param deckRequestDto
     * @param loginUser
     * @return status.code, message, deckResponseDto
     **/
    @Transactional
    public ResponseDataDto<DeckResponseDto> createDeck(long boardId, DeckRequestDto deckRequestDto, User loginUser) {
        userAuthorityCheck(loginUser);

        Board board = getBoard(boardId);
        checkDeckTitleExists(deckRequestDto.getTitle(), board);

        Deck deck = new Deck(deckRequestDto.getTitle(), deckRequestDto.getPosition(), Optional.ofNullable(board));
        deckRepository.save(deck);

        return new ResponseDataDto<>(ResponseStatus.DECK_CREATE_SUCCESS, new DeckResponseDto(deck));
    }

    /** [getDeckList()] 덱 전체 조회
     * * @param boardId
     * @param page
     * @param size
     * @param boardId
     * @return status.code, message, deckPage
     **/
    public ResponseDataDto<Page<DeckResponseDto>> getDeckList(int page, int size, Long boardId) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "position"));
        Page<DeckResponseDto> deckPage = deckRepository.findAllByBoardId(boardId, pageable)
                .map(DeckResponseDto::new);

        return new ResponseDataDto<>(ResponseStatus.DECKS_READ_SUCCESS, deckPage);
    }

    /** [getDeck()] 덱 조회
     * @param deckId
     * @param boardId
     * @return status.code, message, deckResponseDto
     **/
    public ResponseDataDto<DeckResponseDto> getDeck(Long boardId, Long deckId) {
        Deck deck = getDeckByIdAndBoardId(deckId, boardId);
        return new ResponseDataDto<>(ResponseStatus.DECK_READ_SUCCESS, new DeckResponseDto(deck));
    }

    /** [updateDeck()] 덱 수정
     * @param boardId
     * @param deckId
     * @param deckRequestDto
     * @return status.code, message, deckResponseDto
     **/
    @Transactional
    public ResponseDataDto<DeckResponseDto> updateDeck(Long boardId, Long deckId, DeckRequestDto deckRequestDto) {
        Deck deck = getDeckByIdAndBoardId(deckId, boardId);

        if (deckRequestDto.getTitle() != null && deckRequestDto.getPosition() == null) {
            deck.updateTitle(deckRequestDto.getTitle());
        }
        else if(deckRequestDto.getTitle() == null) {
            throw new NullPointerException("수정할 타이틀을 입력해주세요.");
        }
        else throw new IllegalArgumentException("컬럼의 위치는 변경할 수 없습니다.");

        deckRepository.save(deck);
        return new ResponseDataDto<>(ResponseStatus.DECK_UPDATE_SUCCESS, new DeckResponseDto(deck));
    }

    /** [deleteDeck()] 덱 삭제
     * @param boardId
     * @param deckId
     * @param loginUser
     * @return status.code, message,
     **/
    @Transactional
    public ResponseMessageDto deleteDeck(Long boardId, Long deckId, User loginUser) {
        userAuthorityCheck(loginUser);
        Deck deck = getDeckByIdAndBoardId(deckId, boardId);
        deckRepository.delete(deck);

        return new ResponseMessageDto(ResponseStatus.DECK_DELETE_SUCCESS);
    }

    /** [updateDeckPosition()] 덱 포지션 변경
     * @param boardId
     * @param deckId
     * @param deckRequestDto
     * @param loginUser
     **/
    @Transactional
    public ResponseDataDto<DeckResponseDto> updateDeckPosition(Long boardId, Long deckId, DeckRequestDto deckRequestDto, User loginUser) {
        userAuthorityCheck(loginUser);
        Deck deck = getDeckByIdAndBoardId(deckId, boardId);

        if (deckRequestDto.getPosition() != null && deckRequestDto.getTitle() == null) {
            deck.updatePosition(deckRequestDto.getPosition());
        }
        else if(deckRequestDto.getPosition() == null) {
            throw new NullPointerException("컬럼의 위치를 변경해주세요.");
        }
        else throw new IllegalArgumentException("타이틀은 변경할 수 없습니다.");

        deckRepository.save(deck);
        return new ResponseDataDto<>(ResponseStatus.DECK_UPDATE_SUCCESS, new DeckResponseDto(deck));
    }

    // 유저의 권한 확인 (UserType이 MANAGER만 생성가능)
    private void userAuthorityCheck(User loginUser) {
        if (!UserType.MANAGER.equals(loginUser.getUserType())) {
            throw new CustomException(ErrorType.NOT_AVAILABLE_PERMISSION);
        }
    }

    // 보드 조회
    private Board getBoard(Long boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_BOARD));
    }

    // 덱 타이틀 중복 체크
    private void checkDeckTitleExists(String title, Board board) {
        if (deckRepository.findByTitleAndBoard(title, board).isPresent()) {
            throw new CustomException(ErrorType.ALREADY_EXIST_DECK_TITLE);
        }
    }

    // 덱 조회
    private Deck getDeckByIdAndBoardId(Long deckId, Long boardId) {
        Deck deck = deckRepository.findByIdAndBoardId(deckId, boardId);
        if (deck == null) {
            throw new CustomException(ErrorType.NOT_FOUND_DECK);
        }
        return deck;
    }
}