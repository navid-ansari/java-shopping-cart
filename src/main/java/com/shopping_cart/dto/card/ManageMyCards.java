package com.shopping_cart.dto.card;

import lombok.Builder;
import lombok.Data;

@Builder(setterPrefix = "with")
@Data
public class ManageMyCards {

    Boolean saveMyCardEnabled;
    Boolean deleteMyCardEnabled;
}
