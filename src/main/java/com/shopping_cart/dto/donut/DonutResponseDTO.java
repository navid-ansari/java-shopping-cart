package com.shopping_cart.dto.donut;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class DonutResponseDTO {
    public String id;
    public String type;
    public String name;
    public double ppu;
    public BattersResponseDTO batters;
    public List<ToppingResponseDTO> topping;

}