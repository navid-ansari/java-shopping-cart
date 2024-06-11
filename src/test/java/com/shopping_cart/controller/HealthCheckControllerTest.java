package com.shopping_cart.controller;

import com.shopping_cart.service.HealthCheckService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc // mocks the controller on which the test is being performed
@DisplayName("Healthcheck controller")
public class HealthCheckControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    HealthCheckService healthCheckServiceMock;

    /* Success scenarios */
    @Test
    @DisplayName("On healthcheck: status 200: get healthcheck")
    void onHealthCheck() throws Exception {

        // mock service method
        when(healthCheckServiceMock.getHealthCheck()).thenReturn("Healthcheck success");

        // perform api call and match response
        this.mockMvc.perform(get("/v1/api/healthcheck"))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.TEXT_PLAIN_VALUE+";charset=UTF-8"))
                .andExpect(content().string("Healthcheck success"));
    }

    /* Exception scenarios */
    @Test
    @DisplayName("On healthcheck: status 404: not found")
    void onHealthCheckInvalidUrl() throws Exception {

        // perform api call and match response
        this.mockMvc.perform(get("/v1/api/healthcheckInvalidUrl"))
                .andExpect(status().is(404));
    }
}
