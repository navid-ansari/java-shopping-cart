package com.shopping_cart.dto.donut;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ToppingResponseDTO {
    public String id;
    public String type;
}
