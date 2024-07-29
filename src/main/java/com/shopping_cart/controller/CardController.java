package com.shopping_cart.controller;

import com.shopping_cart.common.constant.ApiConstant;
import com.shopping_cart.dto.card.CardResponseDTO;
import com.shopping_cart.model.card.CardEnum;
import com.shopping_cart.service.CardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(ApiConstant.Names.API_PATH)
public class CardController {


    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping("/all-cards-enum")
    public ResponseEntity<List<CardEnum>> onGetAllCardsEnum() {
        List<CardEnum> cards = cardService.allCardsEnum();
        return new ResponseEntity<>(cards, HttpStatus.OK);
    }

    @GetMapping("/all-cards-enum-values")
    public ResponseEntity<List<String>> onGetAllCardsEnumValues() {
        List<String> cards = cardService.allCardsEnumValues();
        return new ResponseEntity<>(cards, HttpStatus.OK);
    }

    @GetMapping("/all-cards-enum-to-map")
    public ResponseEntity<Map<String, Boolean>> onGetAllCardsEnumToMap() {
        Map<String, Boolean> cards = cardService.allCardsEnumToMap();
        return new ResponseEntity<>(cards, HttpStatus.OK);
    }

    @GetMapping("/all-cards-enum-to-list")
    public ResponseEntity<List<CardResponseDTO>> onGetAllCardsEnumToList() {
        List<CardResponseDTO> cards = cardService.allCardsEnumToList();
        return new ResponseEntity<>(cards, HttpStatus.OK);
    }

    @GetMapping("/filter-enabled-cards")
    public ResponseEntity<List<CardResponseDTO>> onGetFilterEnabledCardsFromEnumList() {
        List<CardResponseDTO> cards = cardService.filterEnabledCardsFromEnumList();
        return new ResponseEntity<>(cards, HttpStatus.OK);
    }

}
