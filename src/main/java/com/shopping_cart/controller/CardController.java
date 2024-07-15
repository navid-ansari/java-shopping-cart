package com.shopping_cart.controller;

import com.shopping_cart.common.constant.ApiConstant;
import com.shopping_cart.dto.card.Card;
import com.shopping_cart.dto.card.ManageMyCards;
import com.shopping_cart.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiConstant.Names.API_PATH)
//@RequiredArgsConstructor
public class CardController {


    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping("/card")
    public ResponseEntity<Card> onGetCardDetails() {
        Card card = cardService.cardDetails();
        return new ResponseEntity<>(card, HttpStatus.OK);
    }


}
