package com.sparta.threello.service;


import com.sparta.threello.dto.CardDetailRequestDto;
import com.sparta.threello.dto.CardDetailResponseDto;
import com.sparta.threello.dto.ResponseDataDto;
import com.sparta.threello.dto.ResponseMessageDto;
import com.sparta.threello.entity.Card;
import com.sparta.threello.entity.CardDetail;
import com.sparta.threello.enums.ErrorType;
import com.sparta.threello.enums.ResponseStatus;
import com.sparta.threello.exception.CustomException;
import com.sparta.threello.repository.CardDetailRepository;
import com.sparta.threello.repository.cardRepository.CardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CardDetailService {

    private final CardDetailRepository cardDetailRepository;
    private final CardRepository cardRepository;

    //카드 상세 생성
    public ResponseDataDto createCardDetail(Long cardId, CardDetailRequestDto requestDto) {
        Card card = cardRepository.findById(cardId).
                orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_CARD));
        CardDetail cardDetail = new CardDetail(requestDto.getDescription(), card);
        cardDetailRepository.save(cardDetail);
        CardDetailResponseDto responseDto = new CardDetailResponseDto(cardDetail);
        return new ResponseDataDto(ResponseStatus.CARDDETAIL_CREATE_SUCCESS, responseDto);
    }

    //카드 상세 조회
    public ResponseDataDto getCardDetail(Long cardDetailsId) {
        CardDetail cardDetail = cardDetailRepository.findById(cardDetailsId)
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_CARDDETAIL));
        CardDetailResponseDto responseDto = new CardDetailResponseDto(cardDetail);
        return new ResponseDataDto(ResponseStatus.CARDDETAIL_READ_SUCCESS, responseDto);
    }

    //카드 상세 수정
    @Transactional
    public ResponseDataDto updateCardDetail(Long cardDetailsId, CardDetailRequestDto requestDto) {
        CardDetail cardDetail = cardDetailRepository.findById(cardDetailsId)
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_CARDDETAIL));
        cardDetail.updateCardDetail(requestDto);
        CardDetailResponseDto responseDto = new CardDetailResponseDto(cardDetail);
        return new ResponseDataDto(ResponseStatus.CARDDETAIL_UPDATE_SUCCESS, responseDto);
    }

    //카드 상세 삭제
    public ResponseMessageDto deleteCardDetail(Long cardDetailsId) {
        cardDetailRepository.deleteById(cardDetailsId);
        return new ResponseMessageDto(ResponseStatus.CARDDETAIL_DELETE_SUCCESS);
    }
}
