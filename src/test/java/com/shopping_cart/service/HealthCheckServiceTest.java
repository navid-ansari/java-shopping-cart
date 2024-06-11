package com.shopping_cart.service;

import com.shopping_cart.repository.HealtCheckRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@DisplayName("Healthcheck service")
public class HealthCheckServiceTest {

    @MockBean
    HealtCheckRepository healtCheckRepository;

    @Autowired
    HealthCheckService healthCheckService;

    @Test
    @DisplayName("On healthcheck: status 200: get healthcheck")
    void getHealthCheck() {

        String message = "Healthcheck success";

        // mock repository method
        when(healtCheckRepository.healthCheck()).thenReturn(message);

        // call service
        String response = healthCheckService.getHealthCheck();

        // matcher
        assertEquals(message, response);
    }
}
