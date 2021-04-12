package com.example.demo.service.impl;

import com.example.demo.exception.BadRequestException;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.entity.ProductEntity;
import com.example.demo.service.ProductService;
import com.example.demo.shared.Util;
import com.example.demo.shared.dto.ProductDto;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final Util util;

    public ProductServiceImpl(ProductRepository productRespository, Util util) {
        this.productRepository = productRespository;
        this.util = util;
    }

    public String getProduct() {
        return "getProduct";
    }

    public Optional<ProductDto> getProductById(String productId) {
        Optional<ProductEntity> productIdEntity = productRepository.findByProductId(productId);
        return productIdEntity.map(productEntity -> {
            ProductDto productDto = new ProductDto();
            BeanUtils.copyProperties(productEntity, productDto);
            return productDto;
        });
    }

    public ProductDto createProduct(ProductDto productDetailsIn) {

        ProductEntity productEntity = new ProductEntity();//src
        BeanUtils.copyProperties(productDetailsIn, productEntity);//this is just the four parameters

        String productId = util.generateHash(productDetailsIn.getName());
        productEntity.setProductId(productId.substring(3));

        ProductEntity productEntityOut = productRepository.save(productEntity);//this saved the entity copy ade return
        ProductDto productDtoOut = new ProductDto();//just small details
        BeanUtils.copyProperties(productEntityOut, productDtoOut);//popylera
        return productDtoOut;
    }

    public Optional<ProductDto> updateProduct(String id, ProductDto productDto) {
        Optional<ProductEntity> productIdEntity = productRepository.findByProductId(id);
        if (productIdEntity.isEmpty())
            return Optional.empty();
        return productIdEntity.map(productEntity -> {
            ProductDto response = new ProductDto();
            //set all non-null properties in entity

            productEntity.setProductId(productDto.getProductId() != null ? util.generateHash(productDto.getName()).substring(3) : productEntity.getProductId());
            productEntity.setName(productDto.getName() != null ? productDto.getName() : productEntity.getName());
            productEntity.setCost(productDto.getCost() != 0 ? productDto.getCost() : productEntity.getCost());
            productEntity.setCategory(productDto.getCategory() != null ? productDto.getCategory() : productEntity.getCategory());

            ProductEntity updateProductEntity = productRepository.save(productEntity);
            BeanUtils.copyProperties(updateProductEntity, response);
            return response;
        });
    }

    @Transactional
    public boolean deleteProduct(String id) {
        long removedProductCount = productRepository.deleteByProductId(id);
        return removedProductCount > 0;
    }

    @Override
    public List<ProductDto> getProducts() {
        Iterable<ProductEntity> productEntities = productRepository.findAll();
        ArrayList<ProductDto> productDtos = new ArrayList<>();
        for (ProductEntity productEntity : productEntities) {
            ProductDto productDto = new ProductDto();
            BeanUtils.copyProperties(productEntity, productDto);
            productDtos.add(productDto);
        }
        return productDtos;
    }
}