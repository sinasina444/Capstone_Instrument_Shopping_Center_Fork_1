package com.fdu.capstone_instrument_shopping_center.controllers;

import com.fdu.capstone_instrument_shopping_center.entity.Instrument;
import com.fdu.capstone_instrument_shopping_center.entity.ShoppingCart;
import com.fdu.capstone_instrument_shopping_center.repositories.ShoppingCartRepository;
import com.fdu.capstone_instrument_shopping_center.services.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ShoppingCart")
public class ShoppingCartController {
    private ShoppingCartService shoppingCartService;

    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    public ShoppingCartController(ShoppingCartService shoppingCartService,
                                  ShoppingCartRepository shoppingCartRepository) {
        this.shoppingCartService = shoppingCartService;
        this.shoppingCartRepository = shoppingCartRepository;
    }

    @PostMapping("/addItem")
    public ShoppingCart AddItemToShoppingCart(@RequestParam String username,
                                              @RequestBody Instrument instrument,
                                              @RequestParam int quantity) {
        return shoppingCartService.addItemToCart(username, instrument, quantity);
    }

    @DeleteMapping("/removeItem")
    public ShoppingCart removeItemFromShoppingCart(@RequestParam Long userId,
                                                   @RequestParam Long itemId) {
        return shoppingCartService.removeItemFromCart(userId, itemId);
    }

    @DeleteMapping("/removeItemBySku")
    public ShoppingCart removeItemFromShoppingCart(@RequestParam Long userId,
                                                   @RequestParam String sku) {
        return shoppingCartService.removeItemFromCartBySkuAndUserId(userId, sku);
    }

    @GetMapping("/getShoppingCartById")
    public ShoppingCart getShoppingCartById(@RequestParam Long userId) {
        return shoppingCartService.getShoppingCartByUserId(userId);
    }

    @GetMapping("/getShoppingCart")
    public ShoppingCart getShoppingCart(@RequestParam String username) {
        return shoppingCartService.getShoppingCartByUserName(username);
    }

    @DeleteMapping("/clearShoppingCartById")
    public void clearShoppingCart(@RequestParam Long userId) {
        shoppingCartService.clearCart(userId);
    }

    @DeleteMapping("/clearShoppingCart")
    public void clearShoppingCart(@RequestParam String username) {
        shoppingCartService.clearCart(username);
    }
}
