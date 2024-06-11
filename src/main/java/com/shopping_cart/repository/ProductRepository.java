package com.shopping_cart.repository;

import com.shopping_cart.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
    @Query("{id:'?0'}")
    Product getProductById(String id);

    @Query("{}")
    List<Product> getAllProducts();
}
