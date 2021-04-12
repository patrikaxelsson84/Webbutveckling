package com.example.demo.repository.entity;

import javax.persistence.*;
import java.io.Serializable;

//data access layer and mysql "save data or something" and send it back
// This tells Hibernate to make a table out of this class in mySql and talk to database
@Entity(name="products")
public class ProductEntity implements Serializable {

    //this gives the id a value automatic
    @Id
    @GeneratedValue
    private Long id;

    //this is where the columns are made to mysql and nullable is not accepted
    @Column(length = 50, nullable = false)
    private String productId;

    @Column(length = 50, nullable = false, unique = true)
    private String name;

    @Column(length = 10, nullable = false)
    private int cost;

    @Column(length = 50, nullable = false)
    private String category;

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