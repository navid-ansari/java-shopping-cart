package com.shopping_cart.dto.signup;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@NoArgsConstructor
@Getter
@Setter
public class SignUpResponseDTO {

    private String id;
    private String mobileNo;
    private String email;
    private String password;

}
