package com.shopping_cart.service;

import com.shopping_cart.dto.card.CardResponseDTO;
import com.shopping_cart.model.card.Card;
import com.shopping_cart.model.card.CardEnum;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CardService {

    public List<CardEnum> allCardsEnum() {
        List<CardEnum> cards = CardEnum.getEnabledCardEnumNameList();
        return cards;
    }

    public List<String> allCardsEnumValues() {
        List<String> cards = CardEnum.getEnabledCardEnumValueList();
        return cards;
    }

    public Map<String, Boolean> allCardsEnumToMap() {
        Map<String, Boolean> cards = CardEnum.getEnabledCardEnumMap();
        return cards;
    }

    public List<CardResponseDTO> allCardsEnumToList() {
        List<Card> cards = CardEnum.getEnabledCardEnumMapToList();
        List<CardResponseDTO> mapResponseToDTO = cards.stream().map(card -> mapResponseToDto(card)).collect(Collectors.toList());
        return mapResponseToDTO;
    }

    public List<CardResponseDTO> filterEnabledCardsFromEnumList() {
        List<Card> cards = CardEnum.getEnabledCardEnumMapToListFilter();
        List<CardResponseDTO> mapResponseToDTO = cards.stream().map(card -> mapResponseToDto(card)).collect(Collectors.toList());
        return mapResponseToDTO;
    }

    public CardResponseDTO mapResponseToDto(Card card) {
        return CardResponseDTO.builder().withCardProvider(card.getCardProvider()).withEnabled(card.getEnabled()).build();
    }
}
