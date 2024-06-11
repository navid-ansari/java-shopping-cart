package com.shopping_cart.service;

import com.shopping_cart.dto.product.ProductRequestDTO;
import com.shopping_cart.dto.product.ProductResponseDTO;
import com.shopping_cart.exception.custom.product.AddNewProductException;
import com.shopping_cart.exception.custom.product.GetAllProductsException;
import com.shopping_cart.exception.custom.product.ProductNotFoundException;
import com.shopping_cart.model.Product;
import com.shopping_cart.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
@DisplayName("Product service")
public class ProductServiceTest {

    @MockBean
    ProductRepository productRepository;

    @Autowired
    ProductService productService;

    /* Success scenarios*/
    @Test
    @DisplayName("Add product: status 201: created")
    void addProduct() {

        String id = "6655fc68f5a6fe52093f1704";

        Product product = new Product();
        product.setId(id);
        product.setName("Air filter");
        product.setTitle("Honda CB Trigger air filter");
        product.setPrice(new BigDecimal("229"));
        product.setDescription("Air filter for Honda CB Trigger 150");
        product.setCategory("Auto part");
        product.setImage("www.somecloudhost/imagefolder/imageid");

        // mock repository method
        when(productRepository.save(Mockito.any(Product.class))).thenReturn(product);

        // set values in request dto
        ProductRequestDTO productRequestDTO = new ProductRequestDTO();
        productRequestDTO.setName("Air filter");
        productRequestDTO.setTitle("Honda CB Trigger air filter");
        productRequestDTO.setPrice(new BigDecimal("229"));
        productRequestDTO.setDescription("Air filter for Honda CB Trigger 150");
        productRequestDTO.setCategory("Auto part");
        productRequestDTO.setImage("www.somecloudhost/imagefolder/imageid");

        // call service method
        ProductResponseDTO response = productService.addProduct(productRequestDTO);

        // matchers
        assertEquals(product.getName(), response.getName());
        assertEquals(product.getTitle(), response.getTitle());
        assertEquals(product.getPrice(), response.getPrice());
        assertEquals(product.getDescription(), response.getDescription());
        assertEquals(product.getCategory(), response.getCategory());
        assertEquals(product.getImage(), response.getImage());
    }

    @Test
    @DisplayName("Get product by id: status 200: get single product")
    void getProduct() {

        String id = "6655fc68f5a6fe52093f1704";

        Product product = new Product();
        product.setId(id);
        product.setName("Air filter");
        product.setTitle("Honda CB Trigger air filter");
        product.setPrice(new BigDecimal("229"));
        product.setDescription("Air filter for Honda CB Trigger 150");
        product.setCategory("Auto part");
        product.setImage("www.somecloudhost/imagefolder/imageid");

        // mock repository method
        when(productRepository.getProductById(Mockito.anyString())).thenReturn(product);

        // call service method
        ProductResponseDTO response = productService.getProduct(id);

        // matchers
        assertEquals(product.getName(), response.getName());
        assertEquals(product.getTitle(), response.getTitle());
        assertEquals(product.getPrice(), response.getPrice());
        assertEquals(product.getDescription(), response.getDescription());
        assertEquals(product.getCategory(), response.getCategory());
        assertEquals(product.getImage(), response.getImage());
    }

    @Test
    @DisplayName("Get products: Status 200: get product list")
    void getProducts() {

        Product product = new Product();
        product.setName("Air filter");
        product.setTitle("Honda CB Trigger air filter");
        product.setPrice(new BigDecimal("229"));
        product.setDescription("Air filter for Honda CB Trigger 150");
        product.setCategory("Auto part");
        product.setImage("www.somecloudhost/imagefolder/imageid");

        // mock repository method
        when(productRepository.findAll()).thenReturn(List.of(product));

        // call service method
        List<ProductResponseDTO> response = productService.getProducts();

        // matchers
        assertEquals(1, response.size());
        assertEquals(product.getName(), response.get(0).getName());
        assertEquals(product.getTitle(), response.get(0).getTitle());
        assertEquals(product.getPrice(), response.get(0).getPrice());
        assertEquals(product.getDescription(), response.get(0).getDescription());
        assertEquals(product.getCategory(), response.get(0).getCategory());
        assertEquals(product.getImage(), response.get(0).getImage());
    }

    /* Exception scenarios*/
    @Test
    @DisplayName("Add products: Status 500: internal server exception")
    void addProductFailedException() {

        // mock repository method
        when(productRepository.save(Mockito.any(Product.class))).thenThrow(new AddNewProductException("Failed to add new product"));

        // set values in request dto
        ProductRequestDTO productRequestDTO = new ProductRequestDTO();
        productRequestDTO.setName("Air filter");
        productRequestDTO.setTitle("Honda CB Trigger air filter");
        productRequestDTO.setPrice(new BigDecimal("229"));
        productRequestDTO.setDescription("Air filter for Honda CB Trigger 150");
        productRequestDTO.setCategory("Auto part");
        productRequestDTO.setImage("www.somecloudhost/imagefolder/imageid");

        // call service method
        Exception exception = assertThrows(AddNewProductException.class, () ->
                productService.addProduct(productRequestDTO));
        assertEquals("Failed to add new product", exception.getMessage());
    }

    @Test
    @DisplayName("Get product by id: Status 404: product not found")
    void addProductNotFoundException() {

        String id = "6655fc68f5a6fe52093f1704";

        // mock repository method
        when(productRepository.getProductById(Mockito.anyString())).thenThrow(new ProductNotFoundException("Product not found"));

        // set values in request dto
        ProductRequestDTO productRequestDTO = new ProductRequestDTO();
        productRequestDTO.setName("Air filter");
        productRequestDTO.setTitle("Honda CB Trigger air filter");
        productRequestDTO.setPrice(new BigDecimal("229"));
        productRequestDTO.setDescription("Air filter for Honda CB Trigger 150");
        productRequestDTO.setCategory("Auto part");
        productRequestDTO.setImage("www.somecloudhost/imagefolder/imageid");

        // call service method
        Exception exception = assertThrows(ProductNotFoundException.class, () ->
                productService.getProduct(id));
        assertEquals("Product not found", exception.getMessage());
    }

    @Test
    @DisplayName("Get products: Status 500: internal server exception")
    void getProductsFailedException() {

        // mock repository method
        when(productRepository.findAll()).thenThrow(new GetAllProductsException("Failed to get all products"));

        // set values in request dto
        ProductRequestDTO productRequestDTO = new ProductRequestDTO();
        productRequestDTO.setName("Air filter");
        productRequestDTO.setTitle("Honda CB Trigger air filter");
        productRequestDTO.setPrice(new BigDecimal("229"));
        productRequestDTO.setDescription("Air filter for Honda CB Trigger 150");
        productRequestDTO.setCategory("Auto part");
        productRequestDTO.setImage("www.somecloudhost/imagefolder/imageid");

        // call service method
        Exception exception = assertThrows(GetAllProductsException.class, () ->
                productService.getProducts());
        assertEquals("Failed to get all products", exception.getMessage());
    }
}
