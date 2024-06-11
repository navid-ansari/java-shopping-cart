package com.shopping_cart.controller;

import com.shopping_cart.common.constant.ApiConstant;
import com.shopping_cart.service.HealthCheckService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = ApiConstant.Names.API_PATH)
@AllArgsConstructor
public class HealthCheckController {

    private HealthCheckService healthCheckService;

    /*public HealthCheckController(HealthCheckService healthCheckService) {
        this.healthCheckService = healthCheckService;
    }*/

    @RequestMapping(ApiConstant.Names.HEALTHCHECK)
    public String onHealthCheck() {
        return this.healthCheckService.getHealthCheck();
    }
}
