package com.shopping_cart.repository;

import com.shopping_cart.model.Login;
import com.shopping_cart.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    @Query("{}")
    User signUpUser(User user);

    @Query("{email:'?0'}")
    User findUserByEmail(String email);

    @Query("{mobileNo: ?0}")
    User findUserByMobileNo(String mobileNo);

    @Query("{email:'?0'}")
    User getUserByEmail(String email);

    @Query("{mobileNo:'?0'}")
    User getUserByMobileNo(String mobileNo);
}
