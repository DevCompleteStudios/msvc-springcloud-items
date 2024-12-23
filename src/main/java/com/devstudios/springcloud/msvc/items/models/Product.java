package com.devstudios.springcloud.msvc.items.models;

import java.time.LocalDate;



public class Product {

    private Long id;
    private Double price;
    private String name;
    private LocalDate CreatedAt;



    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public LocalDate getCreatedAt() {
        return CreatedAt;
    }
    public void setCreatedAt(LocalDate createdAt) {
        CreatedAt = createdAt;
    }

}
