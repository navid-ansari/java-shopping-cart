package com.shopping_cart.dto.donut;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class BattersResponseDTO {
    public List<BatterResponseDTO> batter;
}
