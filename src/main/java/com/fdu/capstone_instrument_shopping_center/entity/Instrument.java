package com.fdu.capstone_instrument_shopping_center.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="instrument")
public class Instrument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name="name",nullable=false)
    @JsonIgnoreProperties(ignoreUnknown = true)
    String name;

    @Column(name="category")
    @JsonIgnoreProperties(ignoreUnknown = true)
    String category;

    @Column(name="brand")
    @JsonIgnoreProperties(ignoreUnknown = true)
    String brand;

    @Column(name="price")
    @JsonIgnoreProperties(ignoreUnknown = true)
    Double price;

    @Column(name="description")
    @JsonIgnoreProperties(ignoreUnknown = true)
    String description;

    @Column(name="material")
    @JsonIgnoreProperties(ignoreUnknown = true)
    String material;

    @Column(name="imageURL")
    @JsonIgnoreProperties(ignoreUnknown = true)
    String imageURL;

    @Column(name="stockQuantity")
    @JsonIgnoreProperties(ignoreUnknown = true)
    int stockQuantity;

    @Column(name="rating")
    @JsonIgnoreProperties(ignoreUnknown = true)
    double rating;

}
