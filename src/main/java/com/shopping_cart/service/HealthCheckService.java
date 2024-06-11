package com.shopping_cart.service;

import com.shopping_cart.repository.HealtCheckRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class HealthCheckService {

    //@Autowired
    private HealtCheckRepository healtCheckRepository;

    public String getHealthCheck() {
        return healtCheckRepository.healthCheck();
    }
}
