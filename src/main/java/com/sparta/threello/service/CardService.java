package com.sparta.threello.service;

import com.sparta.threello.dto.CardMemberRequestDto;
import com.sparta.threello.dto.CardMemberResponseDto;
import com.sparta.threello.dto.CardResponseDto;
import com.sparta.threello.dto.CreateCardRequestDto;
import com.sparta.threello.dto.GetStatusCardRequestDto;
import com.sparta.threello.dto.ResponseDataDto;
import com.sparta.threello.dto.ResponseMessageDto;
import com.sparta.threello.dto.UpdateCardPositionRequestDto;
import com.sparta.threello.dto.UpdateCardRequestDto;
import com.sparta.threello.entity.Card;
import com.sparta.threello.entity.CardDetail;
import com.sparta.threello.entity.CardMember;
import com.sparta.threello.entity.Deck;
import com.sparta.threello.entity.User;
import com.sparta.threello.enums.ErrorType;
import com.sparta.threello.enums.ResponseStatus;
import com.sparta.threello.exception.CustomException;
import com.sparta.threello.repository.CardDetailRepository;
import com.sparta.threello.repository.CardMemberRepository;
import com.sparta.threello.repository.DeckRepository;
import com.sparta.threello.repository.UserRepository;
import com.sparta.threello.repository.cardRepository.CardRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;
    private final DeckRepository deckRepository;
    private final CardMemberRepository cardMemberRepository;
    private final CardDetailRepository cardDetailRepository;
    private final UserRepository userRepository;

    //카드 생성
    @Transactional
    public ResponseDataDto<CardResponseDto> createCard(Long deckId, CreateCardRequestDto requestDto,
            User user) {
        Deck deck = getDeck(deckId);

        Card card = saveCard(requestDto, deck);
        addCardMember(card, user);
        saveCardDetail(card);

        CardResponseDto responseDto = new CardResponseDto(card);
        return new ResponseDataDto<>(ResponseStatus.CARD_CREATE_SUCCESS, responseDto);
    }

    // 카드 전체 조회 (deck별)
    public ResponseDataDto<List<CardResponseDto>> getCards(Long deckId) {
        List<Card> cardList = cardRepository.findAllByDeckIdOrderByPositionAsc(deckId);
        List<CardResponseDto> cardResponseDataList = cardList.stream()
                .map(CardResponseDto::new)
                .toList();
        return new ResponseDataDto<>(ResponseStatus.CARDS_READ_SUCCESS, cardResponseDataList);
    }

    // 특정 카드 조회
    public ResponseDataDto<CardResponseDto> getCard(Long cardId) {
        Card card = getCardById(cardId);
        CardResponseDto cardResponseDto = new CardResponseDto(card);
        return new ResponseDataDto<>(ResponseStatus.CARD_READ_SUCCESS, cardResponseDto);
    }

    // 작업자별 카드 조회(덱별로)
    public ResponseDataDto<List<CardResponseDto>> getUserCards(Long deckId, Long userId) {
        List<Card> cardList = cardRepository.findAllByDeckIdAndUserId(deckId, userId);
        List<CardResponseDto> cardResponseDataList = cardList.stream()
                .map(CardResponseDto::new)
                .toList();
        return new ResponseDataDto<>(ResponseStatus.CARDS_READ_BY_MEMBER_SUCCESS,
                cardResponseDataList);
    }

    // 상태별 카드 조회 JpaRepository 이용하여 포지션순으로 카드 정렬
    public ResponseDataDto<List<CardResponseDto>> getStatusCards(Long deckId,
            GetStatusCardRequestDto requestDto) {
        List<Card> cardList = cardRepository.findAllByCardStatusAndDeckIdOrderByPositionAsc(
                requestDto.getCardStatus(), deckId);
        List<CardResponseDto> cardResponseDataList = cardList.stream()
                .map(CardResponseDto::new)
                .toList();
        return new ResponseDataDto<>(ResponseStatus.CARDS_READ_BY_CARDSTATUS_SUCCESS,
                cardResponseDataList);
    }

    // 카드 수정
    @Transactional
    public ResponseDataDto<CardResponseDto> updateCard(Long cardId,
            UpdateCardRequestDto requestDto) {
        Card card = getCardById(cardId);
        card.updateCard(requestDto);
        return new ResponseDataDto<>(ResponseStatus.CARD_UPDATE_SUCCESS, new CardResponseDto(card));
    }

    // 카드 포지션 변경
    @Transactional
    public ResponseDataDto<CardResponseDto> updateCardPosition(Long cardId,
            UpdateCardPositionRequestDto requestDto) {
        Card card = getCardById(cardId);
        card.updatePosition(requestDto);
        CardResponseDto responseDto = new CardResponseDto(card);
        return new ResponseDataDto<>(ResponseStatus.CARD_POSITION_UPDATE_SUCCESS, responseDto);
    }

    // 카드 삭제
    @Transactional
    public ResponseMessageDto deleteCard(Long cardId) {
        cardRepository.deleteById(cardId);
        return new ResponseMessageDto(ResponseStatus.CARD_DELETE_SUCCESS);
    }

    // 카드 멤버 초대
    public ResponseMessageDto inviteCardMember(Long cardId, CardMemberRequestDto requestDto) {
        Card card = getCardById(cardId);
        User user = userRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_USER));
        addCardMember(card, user);
        return new ResponseMessageDto(ResponseStatus.CARD_INVITE_MEMBER_SUCCESS);
    }

    // 카드 멤버 전체 조회
    public ResponseDataDto getCardMembers(Long cardId) {
        List<CardMember> cardMemberList = cardMemberRepository.findAllByCardId(cardId);
        List<CardMemberResponseDto> cardMemberResponseDtoList = cardMemberList.stream()
                .map(CardMemberResponseDto::new).toList();
        return new ResponseDataDto(ResponseStatus.CARD_MEMBER_READS_SUCCESS,
                cardMemberResponseDtoList);
    }

    // 카드 멤버 삭제
    @Transactional
    public ResponseMessageDto deleteCardMember(Long cardId, Long cardMemberId) {
        CardMember cardMember = cardMemberRepository.findById(cardMemberId)
                .orElseThrow(()-> new CustomException(ErrorType.NOT_FOUND_CARDMEMBER));
        if(cardId != cardMember.getCard().getId()) {
            throw new CustomException(ErrorType.NOT_FOUND_CARDMEMBER_IN_CARD);
        }
        cardMemberRepository.deleteById(cardMemberId);
        return new ResponseMessageDto(ResponseStatus.CARD_MEMBER_DELETE_SUCCESS);
    }

    // 메소드
    private Deck getDeck(Long deckId) {
        return deckRepository.findById(deckId)
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_DECK));
    }


    private Card getCardById(Long cardId) {
        return cardRepository.findById(cardId)
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_CARD));
    }

    private Card saveCard(CreateCardRequestDto requestDto, Deck deck) {
        Card card = new Card(requestDto, deck);
        return cardRepository.save(card);
    }

    private void addCardMember(Card card, User user) {
        CardMember cardMember = new CardMember(card, user);
        cardMemberRepository.save(cardMember);
    }

    private void saveCardDetail(Card card) {
        CardDetail cardDetail = new CardDetail(card);
        cardDetailRepository.save(cardDetail);
    }

}