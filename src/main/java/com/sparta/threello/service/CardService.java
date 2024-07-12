package com.sparta.threello.service;

import com.sparta.threello.dto.CardResponseDto;
import com.sparta.threello.dto.CreateCardRequestDto;
import com.sparta.threello.dto.GetStatusCardRequestDto;
import com.sparta.threello.dto.ResponseDataDto;
import com.sparta.threello.dto.ResponseMessageDto;
import com.sparta.threello.dto.UpdateCardPositionRequestDto;
import com.sparta.threello.dto.UpdateCardRequestDto;
import com.sparta.threello.entity.Card;
import com.sparta.threello.entity.CardMember;
import com.sparta.threello.entity.Deck;
import com.sparta.threello.entity.User;
import com.sparta.threello.enums.CardStatus;
import com.sparta.threello.enums.ErrorType;
import com.sparta.threello.enums.ResponseStatus;
import com.sparta.threello.exception.CustomException;
import com.sparta.threello.repository.CardMemberRepository;
import com.sparta.threello.repository.DeckRepository;
import com.sparta.threello.repository.cardRepository.CardRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;
    private final DeckRepository deckRepository;
    private final CardMemberRepository cardMemberRepository;

    //카드 생성
    public ResponseDataDto createCard(Long deckId,
            CreateCardRequestDto requestDto, User user) {
        //덱 조회
        Deck deck = deckRepository.findById(deckId)
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_DECK));
        //카드 생성
        Card card = new Card(requestDto.getTitle(), requestDto.getPosition(), CardStatus.PROCESSING,
                requestDto.getCardDeckPosition(), deck);
        //카드 저장
        cardRepository.save(card);
        //카드 멤버 생성
        CardMember cardMember = new CardMember(user, card);
        //카드 멤버 저장
        cardMemberRepository.save(cardMember);
        //카드 응답 DTO 생성
        CardResponseDto responseDto = new CardResponseDto(card);
        return new ResponseDataDto(ResponseStatus.CARD_CREATE_SUCCESS, responseDto);
    }

    //카드 전체 조회
    public ResponseDataDto getCards(Long deckId) {
        Sort sort = Sort.by("createdAt").ascending(); // 생성 일자로 오름차순 정렬
        List<Card> cardList = cardRepository.findAllByDeckId(deckId, sort);
        List<CardResponseDto> cardResponseDataList = cardList.stream().map(CardResponseDto::new).toList();
        return new ResponseDataDto(ResponseStatus.CARDS_READ_SUCCESS, cardResponseDataList);
    }

    //특정 카드 조회
    public ResponseDataDto getCard(Long cardId) {
        Card card = cardRepository.findById(cardId).orElse(null);
        CardResponseDto cardResponseDto = new CardResponseDto(card);
        return new ResponseDataDto(ResponseStatus.CARD_READ_SUCCESS, cardResponseDto);
    }

    //작업자별 카드 조회
    public ResponseDataDto getUserCards(Long deckId, Long userId) {
        List<Card> cardList = cardRepository.findAllByDeckIdAndUserId(deckId, userId);
        List<CardResponseDto> cardResponseDataList = cardList.stream().map(CardResponseDto::new).toList();
        return new ResponseDataDto(ResponseStatus.CARDS_READ_BY_MEMBER_SUCCESS, cardResponseDataList);
    }

    //상태별 카드 조회
    public ResponseDataDto getStatusCards(Long deckId, GetStatusCardRequestDto requestDto) {
        Sort sort = Sort.by("createdAt").ascending(); // 생성 일자로 오름차순 정렬
        List<Card> cardList = cardRepository.findAllByCardStatus(requestDto.getCardStatus(), sort);
        List<CardResponseDto> cardResponseDataList = cardList.stream().map(CardResponseDto::new).toList();
        return new ResponseDataDto(ResponseStatus.CARDS_READ_BY_CARDSTATUS_SUCCESS, cardResponseDataList);
    }

    //카드 수정
    @Transactional
    public ResponseDataDto updateCard(Long cardId, UpdateCardRequestDto RequestDto) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_CARD));
        card.update(RequestDto);
        return new ResponseDataDto(ResponseStatus.CARD_UPDATE_SUCCESS, card);
    }

    //카드 포지션 변경
    @Transactional
    public ResponseDataDto updateCardPosition(Long cardId, UpdateCardPositionRequestDto requestDto) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_CARD));
        card.updatePosition(requestDto);
        CardResponseDto responseDto = new CardResponseDto(card);
        return new ResponseDataDto(ResponseStatus.CARD_POSITION_UPDATE_SUCCESS, responseDto);
    }

    //카드 삭제
    public ResponseMessageDto deleteCard(Long cardId) {
        cardRepository.deleteById(cardId);
        return new ResponseMessageDto(ResponseStatus.CARD_DELETE_SUCCESS);
    }
}
