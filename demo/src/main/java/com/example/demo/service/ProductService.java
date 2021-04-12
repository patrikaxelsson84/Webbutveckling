package com.example.demo.service;

import com.example.demo.shared.dto.ProductDto;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    String getProduct();

    Optional<ProductDto> getProductById(String id);
    ProductDto createProduct(ProductDto productDetails);

    Optional<ProductDto> updateProduct(String id, ProductDto productDto);

    boolean deleteProduct(String productId);

    List<ProductDto> getProducts();
}
