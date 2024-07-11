package com.sparta.threello.service;

import com.sparta.threello.dto.DeckRequestDto;
import com.sparta.threello.dto.DeckResponseDto;
import com.sparta.threello.dto.ResponseDataDto;
import com.sparta.threello.entity.Deck;
import com.sparta.threello.enums.ErrorType;
import com.sparta.threello.enums.ResponseStatus;
import com.sparta.threello.exception.CustomException;
import com.sparta.threello.repository.BoardRepository;
import com.sparta.threello.repository.DeckRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class DeckService {

    private final DeckRepository deckRepository;
    private final BoardRepository boardRepository;

    public ResponseDataDto<DeckResponseDto> createDeck(long id, DeckRequestDto deckRequestDto) {
        /*
        User user = userDetails.getUser();

        // 유저의 권한 확인 (UserType이 MANAGER만 생성가능)
        if (!user.getUserType().equals(UserType.MANAGER) {
            throw new CustomException(ErrorType.NOT_AVAILABLE_PERMISSION);
        }
        */

        // 상태 이름 중복 체크 (상태 이름이 존재하는 경우)
        if(deckRepository.findByTitle(deckRequestDto.getTitle()).isPresent()) {
            throw new CustomException(ErrorType.ALREADY_EXIST_DECK_TITLE);
        }

        Deck deck = new Deck(deckRequestDto.getTitle(), deckRequestDto.getPosition(), boardRepository.findById(id));

        deckRepository.save(deck);

        return new ResponseDataDto<>(ResponseStatus.DECK_CREATE_SUCCESS,
                new DeckResponseDto(deck));
    }

    public ResponseDataDto<Page<DeckResponseDto>> getDeckList(int page, int size, Long id) {
        // 포지션 순으로 정렬
        Sort.Direction direction = Sort.Direction.DESC;
        Sort sort = Sort.by(direction, "position");
        Pageable pageable = PageRequest.of(page, size, sort);

        return new ResponseDataDto<>(ResponseStatus.DECKS_READ_SUCCESS,
                deckRepository.findAllByBoardId(id, pageable).map(DeckResponseDto::new));
    }

    public ResponseDataDto<DeckResponseDto> getDeck(Long boardId, Long deckId) {

        Deck deck = deckRepository.findByIdAndBoardId(deckId,boardId);

        return new ResponseDataDto<>(ResponseStatus.DECK_READ_SUCCESS,
                new DeckResponseDto(deck));
    }
}
