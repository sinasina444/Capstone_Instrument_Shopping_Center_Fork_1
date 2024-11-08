package com.fdu.capstone_instrument_shopping_center.repositories;

import com.fdu.capstone_instrument_shopping_center.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

}
