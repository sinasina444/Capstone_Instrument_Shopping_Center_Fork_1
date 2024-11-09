package com.fdu.capstone_instrument_shopping_center.services.Impl;

import com.fdu.capstone_instrument_shopping_center.entity.CartItem;
import com.fdu.capstone_instrument_shopping_center.entity.Instrument;
import com.fdu.capstone_instrument_shopping_center.entity.ShoppingCart;
import com.fdu.capstone_instrument_shopping_center.entity.UserInfo;
import com.fdu.capstone_instrument_shopping_center.repositories.CartItemRepository;
import com.fdu.capstone_instrument_shopping_center.repositories.InstrumentRepository;
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

    private InstrumentRepository instrumentRepository;

    @Autowired
    public ShoppingCartServiceImpl(ShoppingCartRepository shoppingCartRepository,
                                   CartItemRepository cartItemRepository,
                                   UserInfoRepository userInfoRepository,
                                   InstrumentRepository instrumentRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.cartItemRepository = cartItemRepository;
        this.userInfoRepository = userInfoRepository;
        this.instrumentRepository = instrumentRepository;
    }

    @Override
    public ShoppingCart addItemToCart(String username, Instrument instrument, int quantity) {
        Optional<UserInfo> optionalUserInfo =  userInfoRepository.findUserInfoByUsername(username);
        if(optionalUserInfo.isEmpty()) {
            log.error("User name is invalid. Please check username again.");
            throw new RuntimeException("No User Info found when adding item to shopping cart.");
        }
        UserInfo userInfo = optionalUserInfo.get();
        Long userId = userInfo.getId();

        ShoppingCart shoppingCart = shoppingCartRepository.findByUserInfoId(userId);
        if(shoppingCart == null) {
            log.info("Creating a new shopping cart for user : {}.", userId);
            shoppingCart = new ShoppingCart();
            // find userInfo by userId
            Optional<UserInfo> optUserInfo = userInfoRepository.findById(userId);
            if(optUserInfo.isEmpty()) {
                log.error("No User Info found when creating a new shopping cart. Please check if userId is valid.");
                throw new RuntimeException("No User Info found when creating a shopping cart.");
            }
            shoppingCart.setUserInfo(optUserInfo.get());
            // need to set shopping cart for userInfo
            userInfo.setShoppingCart(shoppingCart);
        }
        // check if repository has the same instrument with sku value
        Optional<Instrument> OptInstrumentInDB = instrumentRepository.findBySku(instrument.getSku());
        if(OptInstrumentInDB.isEmpty()) {
            log.error("Instrument not found for SKU: {}.", instrument.getSku());
            throw new RuntimeException("Instrument not found in the inventory.");
        }
        Instrument instrumentInDB = OptInstrumentInDB.get();

        // check if any same items in shopping cart
        Optional<CartItem> cartItemOptional  = shoppingCart.getCartItems().stream()
                .filter(cartItem -> cartItem.getInstrument()
                        .getSku().equals(instrument.getSku())).findFirst();

        if(cartItemOptional.isPresent()) {
            // update the number of existing shopping items
            CartItem existingCartItem = cartItemOptional.get();
            int ItemQuantity = existingCartItem.getQuantity();
            ItemQuantity += quantity;
            if(ItemQuantity < 0) {
                log.error("Item quantity is less than 0.");
                throw new RuntimeException("Item quantity is less than 0.");
            }
            existingCartItem.setQuantity(ItemQuantity);
            //update unit price by the instrument price
            existingCartItem.setUnitPrice(BigDecimal.valueOf(instrumentInDB.getPrice()));
        } else {
            // create a new Cart Item
            CartItem cartItem = new CartItem();
            cartItem.setUnitPrice(BigDecimal.valueOf(instrumentInDB.getPrice()));
            // add instrument details from instrument repository of DB
            cartItem.setInstrument(instrumentInDB);
            cartItem.setShoppingCart(shoppingCart);
            cartItem.setQuantity(quantity);
            shoppingCart.getCartItems().add(cartItem);
            shoppingCart.setUserInfo(userInfo);
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
    public ShoppingCart getShoppingCartByUserName(String username) {
        Optional<UserInfo> optionalUserInfo = userInfoRepository.findUserInfoByUsername(username);
        if(optionalUserInfo.isEmpty()) {
            log.error("Username is invalid , failed to get shopping cart info.");
            throw new RuntimeException("Username is invalid , failed to get shopping cart info.");
        }
        UserInfo userInfo = optionalUserInfo.get();
        Long userId = userInfo.getId();
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
