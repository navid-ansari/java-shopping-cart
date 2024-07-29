package com.shopping_cart.model.card;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum CardEnum {
    MASTER_CARD("MASTERCARD", true),
    VISA_CARD("VISACARD", true),
    RUPEE_CARD("RUPEECARD", false);

    private static final Map<String, CardEnum> CARDS_ENUM_MAP;

    static {
        CARDS_ENUM_MAP = Arrays.stream(CardEnum.values()).collect(Collectors.toMap(Enum::name, Function.identity()));
    }

    private String cardProvider;
    private Boolean enabled;

    CardEnum(String cardProvider, Boolean enabled) {
        this.cardProvider = cardProvider;
        this.enabled = enabled;
    }

    public static List<CardEnum> getEnabledCardEnumNameList() {
        return Arrays.asList(CardEnum.values());
        //return Stream.of(EnabledCards.values()).collect(Collectors.toList());
    }

    public static List<String> getEnabledCardEnumValueList() {
        return Stream.of(CardEnum.values()).map(CardEnum::getCardProvider).collect(Collectors.toList());
    }

    public static Map<String, Boolean> getEnabledCardEnumMap() {
        Map<String, Boolean> cards = Stream.of(values()).collect(Collectors.toMap(k -> k.cardProvider, v -> v.enabled));
        return cards;

    }

    public static List<Card> getEnabledCardEnumMapToList() {
        Map<String, Boolean> cardsMap = Stream.of(values()).collect(Collectors.toMap(k -> k.cardProvider, v -> v.enabled));
        List<Card> cardList = cardsMap.entrySet()
                .stream()
                .map(e -> Card.builder().cardProvider(e.getKey()).enabled(e.getValue()).build())
                .collect(Collectors.toList());
        return cardList;
    }

    public static List<Card> getEnabledCardEnumMapToListFilter() {
        Map<String, Boolean> cardsMap = Stream.of(values()).collect(Collectors.toMap(k -> k.cardProvider, v -> v.enabled));
        List<Card> enabledCards = cardsMap.entrySet()
                .stream()
                .map(e -> Card.builder().cardProvider(e.getKey()).enabled(e.getValue()).build())
                .filter(e -> e.getEnabled().equals(true))
                .collect(Collectors.toList());
        return enabledCards;
    }

    @Override
    public String toString() {
        return "EnabledCards{" +
                "cardProvider='" + cardProvider + '\'' +
                ", enabled=" + enabled +
                '}';
    }

    public String getCardProvider() {
        return cardProvider;
    }

    public Boolean getEnabled() {
        return enabled;
    }
}
