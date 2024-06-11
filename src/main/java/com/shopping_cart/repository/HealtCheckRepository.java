package com.shopping_cart.repository;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public interface HealtCheckRepository {
    public String healthCheck();
}
