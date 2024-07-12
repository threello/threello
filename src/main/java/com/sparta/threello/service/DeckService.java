package com.sparta.threello.service;

import com.sparta.threello.dto.*;
import com.sparta.threello.entity.Deck;
import com.sparta.threello.entity.User;
import com.sparta.threello.enums.ErrorType;
import com.sparta.threello.enums.ResponseStatus;
import com.sparta.threello.enums.UserType;
import com.sparta.threello.exception.CustomException;
import com.sparta.threello.repository.DeckRepository;
import com.sparta.threello.repository.board.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@Service
public class DeckService {

    private final DeckRepository deckRepository;
    private final BoardRepository boardRepository;

    /** [createDeck()] 덱 생성
     **/
    public ResponseDataDto<DeckResponseDto> createDeck(long id, DeckRequestDto deckRequestDto, User loginUser) {

        // 유저의 권한 확인 (UserType이 MANAGER만 생성가능)
        userAuthorityCheck(loginUser);

        // 상태 이름 중복 체크 (상태 이름이 존재하는 경우)
        if(deckRepository.findByTitle(deckRequestDto.getTitle()).isPresent()) {
            throw new CustomException(ErrorType.ALREADY_EXIST_DECK_TITLE);
        }

        Deck deck = new Deck(deckRequestDto.getTitle(), deckRequestDto.getPosition(), boardRepository.findById(id));

        deckRepository.save(deck);

        return new ResponseDataDto<>(ResponseStatus.DECK_CREATE_SUCCESS,
                new DeckResponseDto(deck));
    }

    /** [getDeckList()] 덱 전체 조회
     **/
    public ResponseDataDto<Page<DeckResponseDto>> getDeckList(int page, int size, Long id) {
        // 포지션 순으로 정렬
        Sort.Direction direction = Sort.Direction.ASC;
        Sort sort = Sort.by(direction, "position");
        Pageable pageable = PageRequest.of(page, size, sort);

        return new ResponseDataDto<>(ResponseStatus.DECKS_READ_SUCCESS,
                deckRepository.findAllByBoardId(id, pageable).map(DeckResponseDto::new));
    }

    /** [getDeck()] 덱 조회
     **/
    public ResponseDataDto<DeckResponseDto> getDeck(Long boardId, Long deckId) {

        Deck deck = deckRepository.findByIdAndBoardId(deckId,boardId);

        return new ResponseDataDto<>(ResponseStatus.DECK_READ_SUCCESS, new DeckResponseDto(deck));
    }

    /** [updateDeck()] 덱 수정
     **/
    public ResponseDataDto<DeckResponseDto> updateDeck(Long boardId, Long deckId, DeckRequestDto requestDto) {

        Optional<Deck> optionalDeck = Optional.ofNullable(deckRepository.findByIdAndBoardId(deckId, boardId));

        if (optionalDeck.isPresent()) {
            Deck deck = optionalDeck.get();

            // request로 title만 받아왔을 때 업데이트
            if(null != requestDto.getTitle() && null == requestDto.getPosition()) {
                deck.updateTitle(requestDto.getTitle());
            }

            deckRepository.save(deck);
            return new ResponseDataDto<>(ResponseStatus.DECK_UPDATE_SUCCESS, new DeckResponseDto(deck));

        } else {
            throw new CustomException(ErrorType.NOT_FOUND_DECK);
        }
    }

    /** [deleteDeck()] 덱 삭제
     **/
    public ResponseMessageDto deleteDeck(Long boardId, Long deckId, User loginUser) {

        Optional<Deck> optionalDeck = Optional.ofNullable(deckRepository.findByIdAndBoardId(deckId, boardId));

        // 유저의 권한 확인 (UserType이 MANAGER만 생성가능)
        userAuthorityCheck(loginUser);

        if (optionalDeck.isPresent()) {
            Deck deck = optionalDeck.get();
            deckRepository.delete(deck);
            return new ResponseMessageDto(ResponseStatus.DECK_DELETE_SUCCESS);
        } else {
            throw new CustomException(ErrorType.NOT_FOUND_DECK);
        }
    }

    /** [updateDeck()] 덱 포지션 변경
     **/
    public ResponseDataDto<DeckResponseDto> updateDeckPosition(Long boardId, Long deckId, DeckRequestDto requestDto, User loginUser) {

        Optional<Deck> optionalDeck = Optional.ofNullable(deckRepository.findByIdAndBoardId(deckId, boardId));

        // 유저의 권한 확인 (UserType이 MANAGER만 생성가능)
        userAuthorityCheck(loginUser);

        if (optionalDeck.isPresent()) {
            Deck deck = optionalDeck.get();

            // request로 position만 받아왔을 때 업데이트
            if(null == requestDto.getTitle() && null != requestDto.getPosition()) {

                deck.updatePosition(requestDto.getPosition());
            }

            deckRepository.save(deck);
            return new ResponseDataDto<>(ResponseStatus.DECK_UPDATE_SUCCESS, new DeckResponseDto(deck));
        } else {
            throw new CustomException(ErrorType.NOT_FOUND_DECK);
        }
    }

    // 유저의 권한 확인 (UserType이 MANAGER만 생성가능)
    private void userAuthorityCheck(User loginUser) {
        if (!UserType.MANAGER.equals(loginUser.getUserType())) {
            throw new CustomException(ErrorType.NOT_AVAILABLE_PERMISSION);
        }
    }
}
