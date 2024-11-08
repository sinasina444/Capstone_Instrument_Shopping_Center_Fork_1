package com.fdu.capstone_instrument_shopping_center.repositories;

import com.fdu.capstone_instrument_shopping_center.entity.Instrument;
import com.fdu.capstone_instrument_shopping_center.entity.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository  extends JpaRepository<ShoppingCart, Long> {
    ShoppingCart findByUserInfoId(Long userId);
}
