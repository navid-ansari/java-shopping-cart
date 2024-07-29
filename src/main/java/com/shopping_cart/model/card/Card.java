package com.shopping_cart.model.card;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Card {
    private String cardProvider;
    private Boolean enabled;
}
