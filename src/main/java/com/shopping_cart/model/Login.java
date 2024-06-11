package com.shopping_cart.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "ALL_USERS")
public class Login {
    private String email;
    private String mobileNo;
    private String password;
}
