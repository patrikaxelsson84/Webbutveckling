package com.example.demo.controllers;

import com.example.demo.model.request.ProductDetailsRequestModel;
import com.example.demo.model.response.ProductResponseModel;
import com.example.demo.service.ProductService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.example.demo.shared.dto.ProductDto;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("products") //localhost:8080/products
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductResponseModel> getProducts() {
        List<ProductDto> productDtos = productService.getProducts();
        ArrayList<ProductResponseModel> responseList = new ArrayList<>();
        for (ProductDto productDto : productDtos) {
            ProductResponseModel response= new ProductResponseModel();
            BeanUtils.copyProperties(productDto, response);
            responseList.add(response);
        }
        return responseList;
    }
    @GetMapping(value="/{productid}")
    public ProductResponseModel getProduct(@PathVariable String productid){
        ProductResponseModel responseModel = new ProductResponseModel();
        Optional<ProductDto> optionalProductDto = productService.getProductById(productid);

        if(optionalProductDto.isPresent()) {
            ProductDto productDto = optionalProductDto.get();
            BeanUtils.copyProperties(productDto, responseModel);//this will copy this 2 values the rest is null
            return responseModel;
        }
        throw new RuntimeException("No product with id " + productid);
    }

    @PostMapping
    public ProductResponseModel createProduct(@RequestBody ProductDetailsRequestModel productDetailsModel){
        //copy json to dto in
        ProductDto productDtoIn = new ProductDto();
        productDtoIn.setProductId(UUID.randomUUID().toString());
        BeanUtils.copyProperties(productDetailsModel, productDtoIn);

        //pass dto in to service layer
        ProductDto productDtoOut = productService.createProduct(productDtoIn);

        //copy dto out from service layer to response
        ProductResponseModel response = new ProductResponseModel();
        BeanUtils.copyProperties(productDtoOut, response);
        return response;
    }

    @PutMapping("/{productId}")
    public ProductResponseModel updateProduct(@PathVariable String productId, @RequestBody ProductDetailsRequestModel requestData) throws NotFoundException {
        //copy json to dto in
        ProductDto productDtoIn = new ProductDto();
        BeanUtils.copyProperties(requestData, productDtoIn);

        //pass dto in to service layer
        Optional<ProductDto> productDtoOut = productService.updateProduct(productId, productDtoIn);
        if(productDtoOut.isEmpty()){
            throw new NotFoundException("No found");
        }
        ProductDto productDto = productDtoOut.get();
        ProductResponseModel responseModel = new ProductResponseModel();
        BeanUtils.copyProperties(productDto, responseModel);
        return responseModel;
    }
    @DeleteMapping("/{productId}")
    public ResponseEntity<Object> deleteProduct (@PathVariable String productId){

        productService.deleteProduct(productId);
        return  new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}