package com.shopping_cart.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@ToString
@NoArgsConstructor
@Getter
@Setter
@Document(collection = "ALL_PRODUCTS")
public class Product {
    @Id
    private String id;
    private String name;
    private String title;
    private BigDecimal price;
    private String description;
    private String category;
    private String image;

}
