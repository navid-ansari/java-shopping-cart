package com.shopping_cart.service;

import com.shopping_cart.dto.donut.DonutResponseDTO;
import com.shopping_cart.dto.donut.ToppingResponseDTO;
import com.shopping_cart.exception.custom.donut.DonutNotFoundException;
import com.shopping_cart.exception.custom.donut.ToppingNotFoundException;
import com.shopping_cart.model.donut.Batter;
import com.shopping_cart.model.donut.Batters;
import com.shopping_cart.model.donut.Donut;
import com.shopping_cart.model.donut.Topping;
import com.shopping_cart.repository.DonutRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Donut service")
public class DonutServiceTest {

    @Autowired
    DonutService donutService;

    @MockBean
    DonutRepository donutRepository;

    /* Success scenarios */
    @Test
    @Order(1)
    @DisplayName("Get all Donuts : status 200 : success")
    public void allDonuts() throws IOException {

        List<Donut> donutList = List.of(
                Donut.builder().id("0001").type("donut").name("Cake").ppu(0.55)
                        .batters(Batters.builder().batter(List.of(Batter.builder().id("1001").type("Regular").build())).build())
                        .topping(List.of(Topping.builder().id("5002").type("Glazed").build())).build(),
                Donut.builder().id("0003").type("donut").name("Old Fashioned").ppu(0.55)
                        .batters(Batters.builder().batter(List.of(Batter.builder().id("1002").type("Chocolate").build())).build())
                        .topping(List.of(Topping.builder().id("5004").type("Maple").build())).build());

        // mock repository
        when(donutRepository.getAllDonuts()).thenReturn(donutList);

        // call service method
        List<DonutResponseDTO> donuts = donutService.allDonuts();

        // assertions
        assertEquals(donutList.size(), donuts.size());

    }

    @Test
    @Order(2)
    @DisplayName("Get Donut by id : status 200 : success")
    public void donutById() throws IOException {

        String donutId = "0001";

        Donut donut = Donut.builder().id("0001").type("donut").name("Cake").ppu(0.55)
                .batters(Batters.builder().batter(List.of(Batter.builder().id("1001").type("Regular").build())).build())
                .topping(List.of(Topping.builder().id("5002").type("Glazed").build())).build();

        List<Donut> donutList = List.of(
                donut,
                Donut.builder().id("0003").type("donut").name("Old Fashioned").ppu(0.55)
                        .batters(Batters.builder().batter(List.of(Batter.builder().id("1002").type("Chocolate").build())).build())
                        .topping(List.of(Topping.builder().id("5004").type("Maple").build())).build());


        // mock repository
        when(donutRepository.getAllDonuts()).thenReturn(donutList);

        // call service method
        DonutResponseDTO response = donutService.donutById(donutId);

        // assertion
        assertThat(donut).usingRecursiveComparison().isEqualTo(response);
        assertAll("Donut values",
                () -> assertEquals(donut.getId(), response.getId()),
                () -> assertEquals(donut.getType(), response.getType()),
                () -> assertEquals(donut.getName(), response.getName()),
                () -> assertEquals(donut.getPpu(), response.getPpu())
        );
        assertAll("Topping values",
                () -> assertEquals(donut.getTopping().get(0).getId(), response.getTopping().get(0).getId()),
                () -> assertEquals(donut.getTopping().get(0).getType(), response.getTopping().get(0).getType())
        );
        assertAll("Batter values",
                () -> assertEquals(donut.getBatters().getBatter().get(0).getId(), response.getBatters().getBatter().get(0).getId()),
                () -> assertEquals(donut.getBatters().getBatter().get(0).getType(), response.getBatters().getBatter().get(0).getType())
        );
    }

    @Test
    @Order(3)
    @DisplayName("Get Topping by Donut id : status 200 : success")
    public void toppingByDonutId() throws IOException {

        String donutId = "0001";

        Topping topping = Topping.builder().id("5002").type("Glazed").build();

        List<Donut> donutList = List.of(
                Donut.builder().id("0001").type("donut").name("Cake").ppu(0.55)
                        .batters(Batters.builder().batter(List.of(Batter.builder().id("1001").type("Regular").build())).build())
                        .topping(List.of(topping)).build(),
                Donut.builder().id("0003").type("donut").name("Old Fashioned").ppu(0.55)
                        .batters(Batters.builder().batter(List.of(Batter.builder().id("1002").type("Chocolate").build())).build())
                        .topping(List.of(Topping.builder().id("5004").type("Maple").build())).build());

        // mock repository
        when(donutRepository.getAllDonuts()).thenReturn(donutList);

        // call service method
        List<ToppingResponseDTO> toppingList = donutService.toppingsByDonutId(donutId);

        // assertions
        assertEquals(donutList.get(0).getTopping().size(), toppingList.size());

        assertAll("Topping values",
                () -> assertThat(donutList.get(0).getTopping().get(0)).usingRecursiveComparison().isEqualTo(toppingList.get(0)));

        assertEquals(donutList.get(0).getTopping().get(0).getId(), toppingList.get(0).getId());
        assertEquals(donutList.get(0).getTopping().get(0).getType(), toppingList.get(0).getType());

    }

    @Test
    @Order(4)
    @DisplayName("Get Topping by id : status 200 : success")
    public void toppingById() throws IOException {
        String donutId = "0001";
        String toppingId = "5002";
        Topping topping = Topping.builder().id("5002").type("Glazed").build();

        List<Donut> donutList = List.of(
                Donut.builder().id("0001").type("donut").name("Cake").ppu(0.55)
                        .batters(Batters.builder().batter(List.of(Batter.builder().id("1001").type("Regular").build())).build())
                        .topping(List.of(topping)).build(),
                Donut.builder().id("0003").type("donut").name("Old Fashioned").ppu(0.55)
                        .batters(Batters.builder().batter(List.of(Batter.builder().id("1002").type("Chocolate").build())).build())
                        .topping(List.of(Topping.builder().id("5004").type("Maple").build())).build());

        // mock repository
        when(donutRepository.getAllDonuts()).thenReturn(donutList);

        // call service method
        ToppingResponseDTO response = donutService.toppingById(donutId, toppingId);

        // assertions
        assertAll("Topping values",
                () -> assertThat(donutList.get(0).getTopping().get(0)).usingRecursiveComparison().isEqualTo(response));

        assertEquals(donutList.get(0).getTopping().get(0).getId(), response.getId());
        assertEquals(donutList.get(0).getTopping().get(0).getType(), response.getType());
    }

    /* Exception scenarios*/
    @Test
    @Order(5)
    @DisplayName("Get Donut by id : status 404 : Donut not found")
    public void donutByIdDonutNotFoundException() throws IOException {
        String donutId = "0002";

        Donut donut = Donut.builder().id("0001").type("donut").name("Cake").ppu(0.55)
                .batters(Batters.builder().batter(List.of(Batter.builder().id("1001").type("Regular").build())).build())
                .topping(List.of(Topping.builder().id("5002").type("Glazed").build())).build();

        List<Donut> donutList = List.of(
                donut,
                Donut.builder().id("0003").type("donut").name("Old Fashioned").ppu(0.55)
                        .batters(Batters.builder().batter(List.of(Batter.builder().id("1002").type("Chocolate").build())).build())
                        .topping(List.of(Topping.builder().id("5004").type("Maple").build())).build());

        // mock repository
        when(donutRepository.getAllDonuts()).thenReturn(donutList);

        // call service method
        Exception exception = assertThrows(DonutNotFoundException.class, () ->
                donutService.donutById(donutId));

        assertEquals("Donut with id " + donutId + " not found", exception.getMessage());
    }

    @Test
    @Order(6)
    @DisplayName("Get Toppings by Donut id")
    public void toppingsByDonutIdDonutNotFoundException() throws IOException {
        String donutId = "0002";
        Donut donut = Donut.builder().id("0001").type("donut").name("Cake").ppu(0.55)
                .batters(Batters.builder().batter(List.of(Batter.builder().id("1001").type("Regular").build())).build())
                .topping(List.of(Topping.builder().id("5002").type("Glazed").build())).build();

        List<Donut> donutList = List.of(
                donut,
                Donut.builder().id("0003").type("donut").name("Old Fashioned").ppu(0.55)
                        .batters(Batters.builder().batter(List.of(Batter.builder().id("1002").type("Chocolate").build())).build())
                        .topping(List.of(Topping.builder().id("5004").type("Maple").build())).build());

        // mock repository
        when(donutRepository.getAllDonuts()).thenReturn(donutList);

        // call service method
        Exception exception = assertThrows(DonutNotFoundException.class, () ->
                donutService.toppingsByDonutId(donutId));

        assertEquals("Donut with id " + donutId + " not found", exception.getMessage());
    }

    @Test
    @Order(8)
    @DisplayName("Get Topping by id")
    public void toppingByIdDonutNotFoundException() throws IOException {
        String donutId = "0002";
        String toppingId = "0001";

        Donut donut = Donut.builder().id("0001").type("donut").name("Cake").ppu(0.55)
                .batters(Batters.builder().batter(List.of(Batter.builder().id("1001").type("Regular").build())).build())
                .topping(List.of(Topping.builder().id("5002").type("Glazed").build())).build();

        List<Donut> donutList = List.of(
                donut,
                Donut.builder().id("0003").type("donut").name("Old Fashioned").ppu(0.55)
                        .batters(Batters.builder().batter(List.of(Batter.builder().id("1002").type("Chocolate").build())).build())
                        .topping(List.of(Topping.builder().id("5004").type("Maple").build())).build());

        // mock repository
        when(donutRepository.getAllDonuts()).thenReturn(donutList);

        // call service method
        Exception exception = assertThrows(DonutNotFoundException.class, () ->
                donutService.toppingById(donutId, toppingId));

        assertEquals("Donut with id " + donutId + " not found", exception.getMessage());
    }

    @Test
    @Order(9)
    @DisplayName("Get Topping by id")
    public void toppingByIdToppingNotFoundException() throws IOException {
        String donutId = "0001";
        String toppingId = "5003";

        Donut donut = Donut.builder().id("0001").type("donut").name("Cake").ppu(0.55)
                .batters(Batters.builder().batter(List.of(Batter.builder().id("1001").type("Regular").build())).build())
                .topping(List.of(Topping.builder().id("5002").type("Glazed").build())).build();

        List<Donut> donutList = List.of(
                donut,
                Donut.builder().id("0003").type("donut").name("Old Fashioned").ppu(0.55)
                        .batters(Batters.builder().batter(List.of(Batter.builder().id("1002").type("Chocolate").build())).build())
                        .topping(List.of(Topping.builder().id("5004").type("Maple").build())).build());

        // mock repository
        when(donutRepository.getAllDonuts()).thenReturn(donutList);

        // call service method
        Exception exception = assertThrows(ToppingNotFoundException.class, () ->
                donutService.toppingById(donutId, toppingId));

        assertEquals("Topping with id " + toppingId + " not found", exception.getMessage());
    }
}
