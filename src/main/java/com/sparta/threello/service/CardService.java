package com.sparta.threello.service;

import com.sparta.threello.dto.*;
import com.sparta.threello.entity.*;
import com.sparta.threello.enums.ErrorType;
import com.sparta.threello.enums.ResponseStatus;
import com.sparta.threello.exception.CustomException;
import com.sparta.threello.repository.*;
import java.util.List;
import java.util.Objects;

import com.sparta.threello.repository.cardRepository.CardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CardService {

    private final CardRepository cardRepository;
    private final DeckRepository deckRepository;
    private final CardMemberRepository cardMemberRepository;
    private final CardDetailRepository cardDetailRepository;
    private final UserRepository userRepository;

    //카드 생성
    @Transactional
    public CardResponseDto createCard(Long deckId, CreateCardRequestDto requestDto, User user) {
        Deck deck = getDeck(deckId);

        Card card = saveCard(requestDto, deck);
        addCardMember(card, user);
        saveCardDetail(card);

        return new CardResponseDto(card);
    }

    // 카드 전체 조회 (deck별)
    public List<CardResponseDto> getCards(Long deckId) {
        return cardRepository.findAllByDeckIdOrderByPositionAsc(deckId).stream()
                .map(CardResponseDto::new)
                .toList();
    }

    // 특정 카드 조회
    public CardResponseDto getCard(Long cardId) {
        Card card = getCardById(cardId);
        CardResponseDto cardResponseDto = new CardResponseDto(card);
        return cardResponseDto;
    }

    // 작업자별 카드 조회(덱별로)
    public List<CardResponseDto> getUserCards(Long deckId, Long userId) {
        return cardRepository.findAllByDeckIdAndUserId(deckId, userId).stream()
                .map(CardResponseDto::new)
                .toList();
    }

    // 상태별 카드 조회
    public ResponseDataDto<List<CardPerStatusResponseDto>> getStatusCards(Long deckId, GetStatusCardRequestDto requestDto) {
        return new ResponseDataDto<>(
                ResponseStatus.CARDS_READ_BY_CARDSTATUS_SUCCESS,
                cardRepository.findAllByCardStatusAndDeckIdOrderByPositionAsc(requestDto.getCardStatus(), deckId).stream()
                        .map(CardPerStatusResponseDto::new)
                        .toList()
        );
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
    public ResponseDataDto<UpdateCardPositionResponseDto> updateCardPosition(Long deckId,Long cardId,
            UpdateCardPositionRequestDto requestDto) {
        Card card = getCardById(cardId);

        verify(deckId, card);

        Deck newDeck = getDeck(requestDto.getDeckId());
        card.updatePosition(newDeck, requestDto.getPosition());

        return new ResponseDataDto<>(ResponseStatus.CARD_POSITION_UPDATE_SUCCESS, new UpdateCardPositionResponseDto(card));
    }

    // 카드 삭제
    @Transactional
    public ResponseMessageDto deleteCard(Long cardId) {
        cardRepository.deleteById(cardId);
        return new ResponseMessageDto(ResponseStatus.CARD_DELETE_SUCCESS);
    }

    // 카드 멤버 초대
    @Transactional
    public ResponseMessageDto inviteCardMember(Long cardId, CardMemberRequestDto requestDto) {
        Card card = getCardById(cardId);
        String email = requestDto.getEmail();
        User user = findUserByEmail(email);

        //이미 초대된 사용자인지 확인
        Boolean isAlreadyInvited =cardMemberRepository.existsCardMemberByCardIdAndUserId(cardId,user.getId());
        if (isAlreadyInvited) {
            throw new CustomException(ErrorType.ALREADY_INVITED_USER);
        }

        addCardMember(card, user);
        return new ResponseMessageDto(ResponseStatus.CARD_INVITE_MEMBER_SUCCESS);
    }


    // 카드 멤버 전체 조회
    public ResponseDataDto<List<CardMemberResponseDto>> getCardMembers(Long cardId) {
        return new ResponseDataDto<>(
                ResponseStatus.CARD_MEMBER_READS_SUCCESS,
                cardMemberRepository.findAllByCardId(cardId).stream()
                        .map(CardMemberResponseDto::new)
                        .toList()
        );
    }

    // 카드 멤버 삭제
    @Transactional
    public ResponseMessageDto deleteCardMember(Long cardId, Long cardMemberId) {
        CardMember cardMember = cardMemberRepository.findById(cardMemberId)
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_CARDMEMBER));
        if (!Objects.equals(cardId, cardMember.getCard().getId())) {
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

    @Transactional
    protected Card saveCard(CreateCardRequestDto requestDto, Deck deck) {
        Card card = new Card(requestDto, deck);
        return cardRepository.save(card);
    }

    @Transactional
    protected void addCardMember(Card card, User user) {
        CardMember cardMember = new CardMember(card, user);
        cardMemberRepository.save(cardMember);
    }

    @Transactional
    protected void saveCardDetail(Card card) {
        CardDetail cardDetail = new CardDetail(card);
        cardDetailRepository.save(cardDetail);
    }

    private User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_USER));
    }

    // 검증 메서드
    private void verify(Long deckId, Card card) {
        if (!Objects.equals(deckId, card.getDeck().getId())) {
            throw new CustomException(ErrorType.NOT_FOUND_CARD_IN_THE_DECK);
        }
    }
}