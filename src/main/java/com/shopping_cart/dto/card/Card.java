package com.shopping_cart.dto.card;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Data;

@Builder(setterPrefix = "with")
@Data
@JsonPropertyOrder({"cardDetails", "cardManufacturer"}) // will define the property order in the response
@JsonInclude(value = JsonInclude.Include.NON_EMPTY) // will remove null values from the response
public class Card {
    String cardManufacturer;
    @JsonProperty("status") // will rename the cardDetails prop name to status in the response
    ManageMyCards cardDetails;

    String contry;
}
