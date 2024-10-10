package com.fdu.capstone_instrument_shopping_center.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name="order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name="userInfoId", nullable=false)
    @JsonIgnoreProperties(ignoreUnknown = true)
    String userInfoId;

    @Column(name="orderStatus", nullable = false, length = 50)
    @JsonIgnoreProperties(ignoreUnknown = true)
    private String orderStatus;

    @Column(name="orderDate",nullable = false)
    @JsonIgnoreProperties(ignoreUnknown = true)
    private LocalDateTime orderDate;

    @Column(name="totalAmount",nullable = false, precision = 10, scale = 2)
    @JsonIgnoreProperties(ignoreUnknown = true)
    private BigDecimal totalAmount;

    @Column(name="paymentStatus",nullable = false, length = 50)
    @JsonIgnoreProperties(ignoreUnknown = true)
    private String paymentStatus;

    @Column(name="paymentMethod",length = 50)
    @JsonIgnoreProperties(ignoreUnknown = true)
    private String paymentMethod;

    @Column(name="shippingAddress",nullable = false)
    @JsonIgnoreProperties(ignoreUnknown = true)
    private String shippingAddress;

    @Column(name="shippingMethod",length = 50)
    @JsonIgnoreProperties(ignoreUnknown = true)
    private String shippingMethod;

    @Column(name="shippingCost",precision = 10, scale = 2)
    @JsonIgnoreProperties(ignoreUnknown = true)
    private BigDecimal shippingCost;

    @Column(name="trackingNumber",length = 50)
    @JsonIgnoreProperties(ignoreUnknown = true)
    private String trackingNumber;


}
