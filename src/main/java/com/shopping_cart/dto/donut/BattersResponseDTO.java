package com.shopping_cart.dto.donut;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BattersResponseDTO {
    public List<BatterResponseDTO> batter;
}
