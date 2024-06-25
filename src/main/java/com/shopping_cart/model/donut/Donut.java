package com.shopping_cart.model.donut;

import lombok.*;

import java.util.List;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Donut {
    public String id;
    public String type;
    public String name;
    public double ppu;
    public Batters batters;
    public List<Topping> topping;
}
