package com.fdu.capstone_instrument_shopping_center.services;

import com.fdu.capstone_instrument_shopping_center.entity.Instrument;
import com.fdu.capstone_instrument_shopping_center.entity.ShoppingCart;

public interface ShoppingCartService {

    public ShoppingCart addItemToCart(String username, Instrument instrument, int quantity);

    public ShoppingCart removeItemFromCart(Long userId, Long itemId);

    public ShoppingCart getShoppingCartByUserId(Long userId);

    public ShoppingCart getShoppingCartByUserName(String username);

    public void clearCart(Long userId);


}
