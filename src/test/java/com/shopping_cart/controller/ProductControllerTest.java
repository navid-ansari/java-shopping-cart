package com.shopping_cart.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopping_cart.dto.product.ProductRequestDTO;
import com.shopping_cart.dto.product.ProductResponseDTO;
import com.shopping_cart.exception.custom.product.AddNewProductException;
import com.shopping_cart.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Product controller")
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    ProductService productService;

    /* Success scenarios */
    @Test
    @DisplayName("Add product: status 201: created")
    void onAddProduct() throws Exception {

        // set values in request body dto
        ProductRequestDTO productRequestDTO = new ProductRequestDTO();
        productRequestDTO.setName("Air filter");
        productRequestDTO.setTitle("Honda CB Trigger air filter");
        productRequestDTO.setPrice(new BigDecimal("229"));
        productRequestDTO.setDescription("Air filter for Honda CB Trigger 150");
        productRequestDTO.setCategory("Auto part");
        productRequestDTO.setImage("www.somecloudhost/imagefolder/imageid");

        // set response values in response body dto
        ProductResponseDTO productResponseDTO = new ProductResponseDTO();
        productResponseDTO.setId("6655fc68f5a6fe52093f1704");
        productResponseDTO.setName("Air filter");
        productResponseDTO.setTitle("Honda CB Trigger air filter");
        productResponseDTO.setPrice(new BigDecimal("229"));
        productResponseDTO.setDescription("Air filter for Honda CB Trigger 150");
        productResponseDTO.setCategory("Auto part");
        productResponseDTO.setImage("www.somecloudhost/imagefolder/imageid");

        // mock service method
        Mockito.when(productService.addProduct(Mockito.any(ProductRequestDTO.class))).thenReturn(productResponseDTO);

        ObjectMapper mapper = new ObjectMapper();
        var requestBodyJson = mapper.writeValueAsString(productRequestDTO);

        // perform api call and match response
        this.mockMvc.perform(post("/v1/api/add-product").content(requestBodyJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(201))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is("6655fc68f5a6fe52093f1704")))
                .andExpect(jsonPath("$.name", is("Air filter")))
                .andExpect(jsonPath("$.title", is("Honda CB Trigger air filter")))
                //.andExpect(jsonPath("$.price", is(new BigDecimal("229"))))
                .andExpect(jsonPath("$.description", is("Air filter for Honda CB Trigger 150")))
                .andExpect(jsonPath("$.category", is("Auto part")))
                .andExpect(jsonPath("$.image", is("www.somecloudhost/imagefolder/imageid")));
    }

    /* Exception scenarios */
    @Test
    @DisplayName("Add product: status 500: internal server exception")
    void onAddProductFailException() throws Exception {

        ProductRequestDTO productRequestDTO = new ProductRequestDTO();
        productRequestDTO.setName("Air filter");
        productRequestDTO.setTitle("Honda CB Trigger air filter");
        productRequestDTO.setPrice(new BigDecimal("229"));
        productRequestDTO.setDescription("Air filter for Honda CB Trigger 150");
        productRequestDTO.setCategory("Auto part");
        productRequestDTO.setImage("www.somecloudhost/imagefolder/imageid");

        // mock service method
        Mockito.when(productService.addProduct(Mockito.any(ProductRequestDTO.class))).thenThrow(new AddNewProductException("Failed to add new product"));

        ObjectMapper mapper = new ObjectMapper();
        var requestBodyJson = mapper.writeValueAsString(productRequestDTO);

        // perform api call and match response
        this.mockMvc.perform(post("/v1/api/add-product").content(requestBodyJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(500))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.statusCode").value(500))
                .andExpect(jsonPath("$.message").value("Failed to add new product"));
    }

    /* Request body validation scenarios */
    @Test
    @DisplayName("Add product: status 400: bad request")
    void onAddProductRequestBodyValidation() throws Exception {

        // set values in request body dto
        ProductRequestDTO productRequestDTO = new ProductRequestDTO();

        ObjectMapper mapper = new ObjectMapper();
        var requestBodyJson = mapper.writeValueAsString(productRequestDTO);

        // perform api call and match response
        this.mockMvc.perform(post("/v1/api/add-product").content(requestBodyJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errors", hasSize(6)))
                .andExpect(jsonPath("$.errors", hasItem("The Product name is required")))
                .andExpect(jsonPath("$.errors", hasItem("The Product title is required")))
                .andExpect(jsonPath("$.errors", hasItem("The Product price is required")))
                .andExpect(jsonPath("$.errors", hasItem("The Product description is required")))
                .andExpect(jsonPath("$.errors", hasItem("The Product category is required")))
                .andExpect(jsonPath("$.errors", hasItem("The Product image is required")));
    }
}
