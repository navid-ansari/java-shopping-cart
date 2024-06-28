package com.shopping_cart.controller;

import com.shopping_cart.common.constant.ApiConstant;
import com.shopping_cart.dto.donut.DonutResponseDTO;
import com.shopping_cart.dto.donut.ToppingResponseDTO;
import com.shopping_cart.service.DonutService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(ApiConstant.Names.API_PATH)
@AllArgsConstructor
public class DonutController {


    private final DonutService donutService;

    @GetMapping(ApiConstant.Names.DONUTS)
    public ResponseEntity<List<DonutResponseDTO>> onGetAllDonuts() throws IOException {
        //List<DonutResponseDTO> donutList = this.donutService.getAllDonuts();
        List<DonutResponseDTO> donutList = this.donutService.allDonuts();
        return new ResponseEntity<>(donutList, HttpStatus.OK);
    }

    @GetMapping(ApiConstant.Names.DONUT_BY_ID)
    public ResponseEntity<DonutResponseDTO> onGetDonutById(@PathVariable("donutId") String donutId) throws IOException {
        ///DonutResponseDTO donut = this.donutService.getDonutById(donutId);
        DonutResponseDTO donut = this.donutService.donutById(donutId);
        return new ResponseEntity<>(donut, HttpStatus.OK);
    }

    @GetMapping(ApiConstant.Names.TOPPINGS_BY_DONUT_ID)
    public ResponseEntity<List<ToppingResponseDTO>> onGetToppingsByDonutId(@PathVariable("donutId") String donutId) throws IOException {
        List<ToppingResponseDTO> toppingsList = this.donutService.toppingsByDonutId(donutId);
        return new ResponseEntity<>(toppingsList, HttpStatus.OK);
    }

    @GetMapping(ApiConstant.Names.TOPPING_BY_ID)
    public ResponseEntity<ToppingResponseDTO> onGetToppingsById(@PathVariable("donutId") String donutId, @PathVariable("toppingId") String toppingId) throws IOException {
        ToppingResponseDTO topping = this.donutService.toppingById(donutId, toppingId);
        return new ResponseEntity<>(topping, HttpStatus.OK);
    }
}
