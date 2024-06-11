package com.shopping_cart.dto.signup;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@NoArgsConstructor
@Getter
@Setter
public class SignUpRequestDTO {

    @NotBlank(message = "The Mobile number is required")
    //@Size(min=10, max=10, message = "The Mobile number is invalid")
    //@Pattern(regexp = "(^$|[0-9])", message = "The Mobile number should be number only")
    private String mobileNo;

    @NotBlank(message = "The email id is required") //works for null, undefined and blank("") value
    //@Pattern(regexp = "/^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\\\.[a-zA-Z]{2,}$/.", message = "The email id is invalid")
    private String email;

    @NotBlank(message = "The Password is required")
    //@Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!*()]).{8,}$", message = "The Password must be 8 characters long and combination of uppercase letters, lowercase letters, numbers, special characters.")
    private String password;

}
