package com.sparta.threello.service;


import com.sparta.threello.dto.CardDetailRequestDto;
import com.sparta.threello.dto.CardDetailResponseDto;
import com.sparta.threello.dto.GetCardDetailResponseDto;
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

    //카드 상세 조회
    public ResponseDataDto getCardDetail(Long cardId) {
        CardDetail cardDetail = cardDetailRepository.findByCardId(cardId)
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_CARDDETAIL));
        GetCardDetailResponseDto responseDto = new GetCardDetailResponseDto(cardDetail);
        return new ResponseDataDto(ResponseStatus.CARDDETAIL_READ_SUCCESS, responseDto);
    }

    //카드 상세 수정
    @Transactional
    public ResponseDataDto updateCardDetail(Long cardId, CardDetailRequestDto requestDto) {
        CardDetail cardDetail = cardDetailRepository.findByCardId(cardId)
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_CARDDETAIL));
        cardDetail.updateCardDetail(requestDto);
        CardDetailResponseDto responseDto = new CardDetailResponseDto(cardDetail);
        return new ResponseDataDto(ResponseStatus.CARDDETAIL_UPDATE_SUCCESS, responseDto);
    }
}
