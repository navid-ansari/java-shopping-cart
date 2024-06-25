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
public class DonutResponseDTO {
    public String id;
    public String type;
    public String name;
    public double ppu;
    public BattersResponseDTO batters;
    public List<ToppingResponseDTO> topping;
}