package com.shopping_cart.controller;

import com.shopping_cart.common.constant.ApiConstant;
import com.shopping_cart.dto.product.ProductRequestDTO;
import com.shopping_cart.dto.product.ProductResponseDTO;
import com.shopping_cart.service.ProductService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiConstant.Names.API_PATH)
@AllArgsConstructor
public class ProductController {

    private ProductService productService;

    /*public ProductController(ProductService productService) { // taken care by lombok @AllArgsConstructor
        this.productService = productService;
    }*/

    @PostMapping(ApiConstant.Names.ADD_PRODUCT)
    public ResponseEntity<ProductResponseDTO> onAddProduct(@Valid @RequestBody ProductRequestDTO productRequestDTO, HttpServletResponse response) {
        response.addHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE);

        ProductResponseDTO product = productService.addProduct(productRequestDTO);
        return new ResponseEntity<>(product, HttpStatus.CREATED);

        /*try {
            ProductResponseDTO product = productService.addProduct(productRequestDTO);
            return ResponseEntity.ok(product);
        } catch (Exception e) {
            throw e;
        }*/
    }

    @GetMapping(ApiConstant.Names.PRODUCT_BY_ID)
    public ResponseEntity<ProductResponseDTO> onGetProduct(@PathVariable("id") @NotBlank String id) {
        ProductResponseDTO product = productService.getProduct(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping(ApiConstant.Names.PRODUCTS)
    public ResponseEntity<List<ProductResponseDTO>> onGetProducts() {
        List<ProductResponseDTO> products = productService.getProducts();
        return ResponseEntity.ok(products);

        /*try {
            List<ProductResponseDTO> products = productService.getProducts();
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            throw e;
        }*/
    }
}
