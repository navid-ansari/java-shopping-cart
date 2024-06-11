package com.shopping_cart.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Document(collection = "ALL_USERS")
public class User {

    @Id
    private String id;

    //@CreatedDate
    //public Date createdDate;

    private String mobileNo;
    private String email;
    private String password;

}
