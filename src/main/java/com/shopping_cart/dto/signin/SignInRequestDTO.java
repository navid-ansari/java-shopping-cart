package com.shopping_cart.dto.signin;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
public class SignInRequestDTO {

    @NotBlank(message = "The username is required")
    private String username;

    @NotBlank(message = "The password is required")
    private String password;
}
