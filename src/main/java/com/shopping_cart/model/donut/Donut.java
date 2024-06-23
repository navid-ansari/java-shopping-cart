package com.shopping_cart.model.donut;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Donut {
    public String id;
    public String type;
    public String name;
    public double ppu;
    public Batters batters;
    public List<Topping> topping;
}
