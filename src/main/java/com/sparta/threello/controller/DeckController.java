package com.sparta.threello.controller;

import com.sparta.threello.dto.DeckRequestDto;
import com.sparta.threello.dto.ResponseDataDto;
import com.sparta.threello.entity.Deck;
import com.sparta.threello.enums.ResponseStatus;
import com.sparta.threello.service.DeckService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/boards/{id}/decks")
@RestController
public class DeckController {
    private final DeckService deckService;

    @PostMapping
    public ResponseEntity<ResponseDataDto<Deck>> createDeck(@PathVariable long id, @RequestBody DeckRequestDto deckRequestDto/*, @AuthenticationPrincipal UserDetailsImpl authentication*/) {
        ResponseDataDto<Deck> responseDto = deckService.createDeck(id, deckRequestDto/*, authentication*/);
        return ResponseEntity.ok(new ResponseDataDto<>(ResponseStatus.CARD_UPDATE_SUCCESS, responseDto).getData());
    }
}
