package com.shopping_cart.service;

import com.shopping_cart.dto.card.Card;
import com.shopping_cart.dto.card.ManageMyCards;
import org.springframework.stereotype.Service;

@Service
public class CardService {

    public Card cardDetails() {
        // option 1
        //var cardDetail = Card.builder().withCardManufacturer("MASTERCARD").withCardDetails(ManageMyCards.builder().withDeleteMyCardEnabled(true).withSaveMyCardEnabled(true).build()).build();

        // option 2
        var cardDetail = Card.builder().withCardManufacturer("MASTERCARD").withCardDetails(getManageCard()).build();
        return cardDetail;
    }

    public ManageMyCards getManageCard() {
        return ManageMyCards.builder().withDeleteMyCardEnabled(true).withSaveMyCardEnabled(true).build();
    }
}
