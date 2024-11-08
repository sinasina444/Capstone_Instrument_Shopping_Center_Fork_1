package com.fdu.capstone_instrument_shopping_center.services.Impl;

import com.fdu.capstone_instrument_shopping_center.entity.CartItem;
import com.fdu.capstone_instrument_shopping_center.entity.Instrument;
import com.fdu.capstone_instrument_shopping_center.entity.ShoppingCart;
import com.fdu.capstone_instrument_shopping_center.entity.UserInfo;
import com.fdu.capstone_instrument_shopping_center.repositories.CartItemRepository;
import com.fdu.capstone_instrument_shopping_center.repositories.ShoppingCartRepository;
import com.fdu.capstone_instrument_shopping_center.repositories.UserInfoRepository;
import com.fdu.capstone_instrument_shopping_center.services.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@Slf4j
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private ShoppingCartRepository shoppingCartRepository;

    private CartItemRepository cartItemRepository;

    private UserInfoRepository userInfoRepository;

    @Autowired
    public ShoppingCartServiceImpl(ShoppingCartRepository shoppingCartRepository,
                                   CartItemRepository cartItemRepository,
                                   UserInfoRepository userInfoRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.cartItemRepository = cartItemRepository;
        this.userInfoRepository = userInfoRepository;
    }

    @Override
    public ShoppingCart addItemToCart(Long userId, Instrument instrument, int quantity) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserInfoId(userId);
        if(shoppingCart == null) {
            log.info("Creating a new shopping cart for user : {}.", userId);
            shoppingCart = new ShoppingCart();
            // find userInfo by userId
            Optional<UserInfo> userInfo = userInfoRepository.findById(userId);
            if(userInfo.isEmpty()) {
                log.error("No User Info found when creating a new shopping cart. Please check if userId is valid.");
                throw new RuntimeException("No User Info found when creating a shopping cart.");
            }
            shoppingCart.setUserInfo(userInfo.get());
        }
        // check if any same items in shopping cart
        Optional<CartItem> cartItemOptional = shoppingCart.getCartItems().stream()
                .filter(cartItem -> cartItem.getInstrument()
                        .getId().equals(instrument.getId())).findFirst();
        if(cartItemOptional.isPresent()) {
            // update the number of existing shopping items
            CartItem existingCartItem = cartItemOptional.get();
            int ItemQuantity = existingCartItem.getQuantity();
            ItemQuantity += quantity;
            existingCartItem.setQuantity(ItemQuantity);
            //update unit price by the instrument price
            existingCartItem.setUnitPrice(BigDecimal.valueOf(instrument.getPrice()));
        } else {
            // create a new Cart Item
            CartItem cartItem = new CartItem();
            cartItem.setUnitPrice(BigDecimal.valueOf(instrument.getPrice()));
            cartItem.setInstrument(instrument);
            cartItem.setShoppingCart(shoppingCart);
            cartItem.setQuantity(quantity);
            shoppingCart.getCartItems().add(cartItem);
        }
        return shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public ShoppingCart removeItemFromCart(Long userId, Long itemId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserInfoId(userId);
        if(shoppingCart != null) {
            shoppingCart.getCartItems().removeIf(item -> item.getId().equals(itemId));
            return shoppingCartRepository.save(shoppingCart);
        }
        return null;
    }

    @Override
    public ShoppingCart getShoppingCartByUserId(Long userId) {
        return shoppingCartRepository.findByUserInfoId(userId);
    }

    @Override
    public void clearCart(Long userId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserInfoId(userId);
        if(shoppingCart != null) {
            shoppingCart.getCartItems().clear();
            shoppingCartRepository.save(shoppingCart);
        }
    }
}
