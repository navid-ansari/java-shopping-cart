package com.shopping_cart.service;

import com.shopping_cart.dto.product.ProductRequestDTO;
import com.shopping_cart.dto.product.ProductResponseDTO;
import com.shopping_cart.exception.custom.product.AddNewProductException;
import com.shopping_cart.exception.custom.product.GetAllProductsException;
import com.shopping_cart.exception.custom.product.ProductNotFoundException;
import com.shopping_cart.model.Product;
import com.shopping_cart.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductService {

    //@Autowired
    private ProductRepository productRepository;

    public ProductResponseDTO addProduct(ProductRequestDTO productRequestDTO){
        Product product = mapProductDtoToEntity(productRequestDTO);
        Product response = productRepository.save(product);
        System.out.println(response);
        // response.setId(null); // use this to test 500 error

        if(response == null && response.getId() == null) {
            throw new AddNewProductException("Failed to add new product");
        }
        return mapProductEntityToDto(response);
    }
    public ProductResponseDTO getProduct(String id){
        Product response = productRepository.getProductById(id);
        if(response == null) {  // product found in db
            throw new ProductNotFoundException("Product not found");
        }
        return mapProductEntityToDto(response);
    }

    public List<ProductResponseDTO> getProducts() {
        //List<Product> productList = productRepository.getAllProducts(); // also working

        List<Product> productList = productRepository.findAll();

        //productList = null; // uncomment this to test exception

        if(productList == null) {
            throw new GetAllProductsException("Failed to get all products");
        }
        if(productList.size() == 0) { // productList.isEmpty()
            return Collections.emptyList();
        }
        return productList.stream().map(product -> mapProductEntityToDto(product)).collect(Collectors.toList());
    }

    public Product mapProductDtoToEntity(ProductRequestDTO productRequestDTO) {
        Product product = new Product();
        product.setName(productRequestDTO.getName());
        product.setTitle(productRequestDTO.getTitle());
        product.setPrice(productRequestDTO.getPrice());
        product.setDescription(productRequestDTO.getDescription());
        product.setCategory(productRequestDTO.getCategory());
        product.setImage(productRequestDTO.getImage());
        return product;
    }

    public ProductResponseDTO mapProductEntityToDto(Product product) {
        ProductResponseDTO productResponseDTO = new ProductResponseDTO();
        productResponseDTO.setId(product.getId());
        productResponseDTO.setName(product.getName());
        productResponseDTO.setTitle(product.getTitle());
        productResponseDTO.setPrice(product.getPrice());
        productResponseDTO.setDescription(product.getDescription());
        productResponseDTO.setCategory(product.getCategory());
        productResponseDTO.setImage(product.getImage());
        return productResponseDTO;
    }
}
