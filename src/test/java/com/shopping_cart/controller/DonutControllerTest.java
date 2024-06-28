package com.shopping_cart.controller;

import com.shopping_cart.dto.donut.BatterResponseDTO;
import com.shopping_cart.dto.donut.BattersResponseDTO;
import com.shopping_cart.dto.donut.DonutResponseDTO;
import com.shopping_cart.dto.donut.ToppingResponseDTO;
import com.shopping_cart.exception.custom.donut.DonutNotFoundException;
import com.shopping_cart.exception.custom.donut.ToppingNotFoundException;
import com.shopping_cart.service.DonutService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@DisplayName("Donut controller")
public class DonutControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    DonutService donutService;

    /* Success scenarios */
    @Test
    @DisplayName("Get all Donuts (one Donut): status 200 : success")
    public void onGetAllDonutsOneDonut() throws Exception {

        DonutResponseDTO donut = DonutResponseDTO.builder().id("0001").type("donut").name("Cake").ppu(0.55)
                .batters(BattersResponseDTO.builder().batter(List.of(BatterResponseDTO.builder().id("1001").type("Regular").build())).build())
                .topping(List.of(ToppingResponseDTO.builder().id("5002").type("Glazed").build())).build();

        // mock service method
        when(donutService.allDonuts()).thenReturn(List.of(donut));

        // perform api call and match response
        this.mockMvc.perform(get("/v1/api/donuts").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id", is("0001")))
                .andExpect(jsonPath("$[0].type", is("donut")))
                .andExpect(jsonPath("$[0].name", is("Cake")))
                .andExpect(jsonPath("$[0].ppu", is(0.55)))
                .andExpect(jsonPath("$[0].batters.batter[0].id", is("1001")))
                .andExpect(jsonPath("$[0].batters.batter[0].type", is("Regular")))
                .andExpect(jsonPath("$[0].topping[0].id", is("5002")))
                .andExpect(jsonPath("$[0].topping[0].type", is("Glazed")));
    }

    @Test
    @DisplayName("Get all Donuts (two Donuts): status 200 : success")
    public void onGetAllDonutsTwoDonuts() throws Exception {

        List<DonutResponseDTO> donutList = List.of(
                DonutResponseDTO.builder().id("0001").type("donut").name("Cake").ppu(0.55)
                        .batters(BattersResponseDTO.builder().batter(List.of(BatterResponseDTO.builder().id("1001").type("Regular").build())).build())
                        .topping(List.of(ToppingResponseDTO.builder().id("5002").type("Glazed").build())).build(),
                DonutResponseDTO.builder().id("0003").type("donut").name("Old Fashioned").ppu(0.55)
                        .batters(BattersResponseDTO.builder().batter(List.of(BatterResponseDTO.builder().id("1002").type("Chocolate").build())).build())
                        .topping(List.of(ToppingResponseDTO.builder().id("5004").type("Maple").build())).build()
        );

        // mock service method
        when(donutService.allDonuts()).thenReturn(donutList);

        // perform api call and match response
        this.mockMvc.perform(get("/v1/api/donuts").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[*].id", containsInAnyOrder("0001", "0003")))
                .andExpect(jsonPath("$[*].type", containsInAnyOrder("donut", "donut")))
                .andExpect(jsonPath("$[*].name", containsInAnyOrder("Cake", "Old Fashioned")))
                .andExpect(jsonPath("$[*].ppu", containsInAnyOrder(0.55, 0.55)))
                .andExpect(jsonPath("$[*].batters.batter[*].id", containsInAnyOrder("1001", "1002")))
                .andExpect(jsonPath("$[*].batters.batter[*].type", containsInAnyOrder("Regular", "Chocolate")))
                .andExpect(jsonPath("$[*].topping[*].id", containsInAnyOrder("5002", "5004")))
                .andExpect(jsonPath("$[*].topping[*].type", containsInAnyOrder("Glazed", "Maple")));
    }

    @Test
    @DisplayName("Get Donut by id : status 200 : success")
    public void onGetDonutById() throws Exception {

        String donutId = "0001";
        DonutResponseDTO donut = DonutResponseDTO.builder().id("0001").type("donut").name("Cake").ppu(0.55)
                .batters(BattersResponseDTO.builder().batter(List.of(BatterResponseDTO.builder().id("1001").type("Regular").build())).build())
                .topping(List.of(ToppingResponseDTO.builder().id("5002").type("Glazed").build())).build();

        // mock service method
        when(donutService.donutById(donutId)).thenReturn(donut);

        // perform api call and match response
        StringBuilder sb = new StringBuilder();
        sb.append("/v1/api/donut/").append(donutId);
        this.mockMvc.perform(get(sb.toString()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is("0001")))
                .andExpect(jsonPath("$.type", is("donut")))
                .andExpect(jsonPath("$.name", is("Cake")))
                .andExpect(jsonPath("$.ppu", is(0.55)))
                .andExpect(jsonPath("$.batters.batter[0].id", is("1001")))
                .andExpect(jsonPath("$.batters.batter[0].type", is("Regular")))
                .andExpect(jsonPath("$.topping[0].id", is("5002")))
                .andExpect(jsonPath("$.topping[0].type", is("Glazed")));
    }

    @Test
    @DisplayName("Get Toppings by Donut id : status 200 : success")
    public void onGetToppingsByDonutId() throws Exception {
        String donutId = "0001";
        List<ToppingResponseDTO> toppingList = List.of(
                ToppingResponseDTO.builder().id("5002").type("Glazed").build(),
                ToppingResponseDTO.builder().id("5005").type("Sugar").build()
        );

        // mock service method
        when(donutService.toppingsByDonutId(donutId)).thenReturn(toppingList);

        // perform api call and match response
        String url = (new StringBuilder()).append("/v1/api/donut/").append(donutId).append("/toppings").toString();
        this.mockMvc.perform(get(url).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[*].id", containsInAnyOrder("5002", "5005")))
                .andExpect(jsonPath("$[*].type", containsInAnyOrder("Glazed", "Sugar")));
    }

    @Test
    @DisplayName("Get Topping by id : status 200 : success")
    public void onGetToppingById() throws Exception {
        String donutId = "0001";
        String toppingId = "5005";

        ToppingResponseDTO topping = ToppingResponseDTO.builder().id("5005").type("Sugar").build();

        // mock service method
        when(donutService.toppingById(donutId, toppingId)).thenReturn(topping);

        // perform api call and match response
        String url = (new StringBuilder()).append("/v1/api/donut/").append(donutId).append("/topping/").append(toppingId).toString();
        this.mockMvc.perform(get(url).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is("5005")))
                .andExpect(jsonPath("$.type", is("Sugar")));

    }

    /* Exception scenarios */
    @Test
    @DisplayName("Get Donut by id : status 404 : Donut not found")
    public void onGetDonutByIdNotFoundException() throws Exception {
        String donutId = "0001";

        // mock service method
        when(donutService.donutById(donutId)).thenThrow(new DonutNotFoundException("Donut with id " + donutId + " not found"));

        // perform api call and match response
        StringBuilder sb = new StringBuilder();
        sb.append("/v1/api/donut/").append(donutId);
        this.mockMvc.perform(get(sb.toString()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(404))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.statusCode").value(404))
                .andExpect(jsonPath("$.message").value("Donut with id " + donutId + " not found"));
    }

    @Test
    @DisplayName("Get Toppings by Donut id : status 404 : Donut not found")
    public void onGetToppingsByDonutIdDonutNotFoundException() throws Exception {
        String donutId = "0001";

        // mock service method
        when(donutService.toppingsByDonutId(donutId)).thenThrow(new DonutNotFoundException("Donut with id " + donutId + " not found"));

        // perform api call and match response
        String url = (new StringBuilder()).append("/v1/api/donut/").append(donutId).append("/toppings").toString();
        this.mockMvc.perform(get(url).contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.statusCode").value(404))
                .andExpect(jsonPath("$.message").value("Donut with id " + donutId + " not found"));
    }

    @Test
    @DisplayName("Get Topping by id : status 404 : Donut not found")
    public void onGetToppingByIdDonutNotFoundException() throws Exception {
        String donutId = "0001";
        String toppingId = "5005";

        // mock service method
        when(donutService.toppingById(donutId, toppingId)).thenThrow(new DonutNotFoundException("Donut with id " + donutId + " not found"));

        // perform api call and match response
        String url = (new StringBuilder()).append("/v1/api/donut/").append(donutId).append("/topping/").append(toppingId).toString();
        this.mockMvc.perform(get(url).contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.statusCode").value(404))
                .andExpect(jsonPath("$.message").value("Donut with id " + donutId + " not found"));
    }

    @Test
    @DisplayName("Get Topping by id : status 404 : Topping not found")
    public void onGetToppingByIdToppingNotFoundException() throws Exception {
        String donutId = "0001";
        String toppingId = "5005";

        // mock service method
        when(donutService.toppingById(donutId, toppingId)).thenThrow(new ToppingNotFoundException("Topping with id " + toppingId + " not found"));

        // perform api call and match response
        String url = (new StringBuilder()).append("/v1/api/donut/").append(donutId).append("/topping/").append(toppingId).toString();
        this.mockMvc.perform(get(url).contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.statusCode").value(404))
                .andExpect(jsonPath("$.message").value("Topping with id " + toppingId + " not found"));
    }
}
