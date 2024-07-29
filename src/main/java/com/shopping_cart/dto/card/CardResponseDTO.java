package com.shopping_cart.dto.card;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(setterPrefix = "with")
@JsonPropertyOrder({"cardProvider", "enabled"}) // will define the property order in the response
@JsonInclude(value = JsonInclude.Include.NON_EMPTY) // will remove null values from the response
public class CardResponseDTO {
    private String cardProvider;
    //@JsonProperty("status") // will rename the cardDetails prop name to status in the response
    private Boolean enabled;
}
