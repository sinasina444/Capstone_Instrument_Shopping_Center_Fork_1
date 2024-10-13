package com.fdu.capstone_instrument_shopping_center.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name="cart_item")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPrice;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private Long productId;

    @Column(nullable = false)
    private String productName;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "shopping_cart_id")
//    private ShoppingCart shoppingCart;

    public BigDecimal getTotalPrice() {
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }
}
