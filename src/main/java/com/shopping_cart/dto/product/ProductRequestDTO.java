package com.shopping_cart.dto.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

/*@ToString
@NoArgsConstructor
@Getter
@Setter*/
@Data // equivalent to @ToString, @Getter, @Setter, @AllArgsConstructor etc
@NoArgsConstructor
public class ProductRequestDTO {

    private String id;

    @NotBlank(message = "The Product name is required")
    //@Pattern(regexp = "/^[a-zA-Z]*$/", message = "The Product name is invalid")
    private String name;

    @NotBlank(message = "The Product title is required")
    private String title;

    //@NotBlank(message = "The Product price is required")
    @NotNull(message = "The Product price is required")
    private BigDecimal price;

    @NotBlank(message = "The Product description is required")
    private String description;

    @NotBlank(message = "The Product category is required")
    private String category;

    @NotBlank(message = "The Product image is required")
    private String image;
}
