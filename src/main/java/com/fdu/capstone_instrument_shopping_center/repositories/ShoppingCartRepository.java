package com.fdu.capstone_instrument_shopping_center.repositories;

import com.fdu.capstone_instrument_shopping_center.entity.Instrument;
import com.fdu.capstone_instrument_shopping_center.entity.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository  extends JpaRepository<ShoppingCart, Long> {
    ShoppingCart findByUserInfoId(Long userId);


    @Query("SELECT sc FROM ShoppingCart sc JOIN sc.cartItems ci WHERE ci.instrument.sku = :sku AND sc.userInfo.id = :userId")
    ShoppingCart findByInstrumentSkuAndUserId(@Param("sku") String sku, @Param("userId") Long userId);
}
