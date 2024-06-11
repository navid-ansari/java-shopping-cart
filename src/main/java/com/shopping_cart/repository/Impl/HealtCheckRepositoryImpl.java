package com.shopping_cart.repository.Impl;

import com.shopping_cart.repository.HealtCheckRepository;
import org.springframework.stereotype.Service;

@Service
public class HealtCheckRepositoryImpl implements HealtCheckRepository {
    //@Override
    public String healthCheck(){
        return "Healthcheck success";
    }
}
