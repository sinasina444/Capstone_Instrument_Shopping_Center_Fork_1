package com.fdu.capstone_instrument_shopping_center.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name="instrument")
public class Instrument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "sku", unique = true, nullable = false)
    private String sku;

    @Column(name="name",nullable=false)
    @JsonIgnoreProperties(ignoreUnknown = true)
    String name;

    @Column(name="category",nullable=false)
    @JsonIgnoreProperties(ignoreUnknown = true)
    String category;

    @Column(name="brand",nullable=false)
    @JsonIgnoreProperties(ignoreUnknown = true)
    String brand;

    @Column(name="price")
    @JsonIgnoreProperties(ignoreUnknown = true)
    Double price;

    @Column(name="description", length = 1000)
    @JsonIgnoreProperties(ignoreUnknown = true)
    String description;

    @Column(name="detail", length = 1000)
    @JsonIgnoreProperties(ignoreUnknown = true)
    String detail;

    @Column(name="material")
    @JsonIgnoreProperties(ignoreUnknown = true)
    String material;

    @Column(name="imageURL")
    @JsonIgnoreProperties(ignoreUnknown = true)
    String imageURL;

    @Column(name="audioURL")
    @JsonIgnoreProperties(ignoreUnknown = true)
    String audioURL;

    @Column(name="stockQuantity")
    @JsonIgnoreProperties(ignoreUnknown = true)
    int stockQuantity;

    @Column(name="rating")
    @JsonIgnoreProperties(ignoreUnknown = true)
    double rating;

    public Instrument() {}

    public Instrument(String name, String brand, String category) {
        this.name = name;
        this.brand = brand;
        this.category = category;
    }

    @PrePersist
    public void generateSku() {
        this.sku = UUID.randomUUID().toString();
    }
}
