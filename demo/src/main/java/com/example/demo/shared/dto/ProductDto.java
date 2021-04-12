package com.example.demo.shared.dto;

import java.io.Serializable;

public class ProductDto implements Serializable {
    //long felt id, string felt userId, firstname, lastname, email, password, encryptedPassword
    //a convention this is just for bigger work when you want to get different layers
    private long id;
    private String productId, name, category;
    private int cost;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}