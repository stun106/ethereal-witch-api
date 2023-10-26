package com.ethereal.witch.controllers;

import com.ethereal.witch.interfaces.ICartShoppingRepository;
import com.ethereal.witch.interfaces.IProductRepository;
import com.ethereal.witch.interfaces.IUserRepository;

import com.ethereal.witch.models.shoppingcart.CartShopping;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("witch/cart")
public class CartController {

    @Autowired
    private ICartShoppingRepository cartShoppingRepository;
    @Autowired
    private IProductRepository productRepository;
    @Autowired
    private IUserRepository userRepository;
    @PostMapping("/create/auth")
    public ResponseEntity create(@RequestBody CartShopping cart, HttpServletRequest request) {

        var cartId = cartShoppingRepository.findByCartid(cart.getCartid());
        var cartProduct = productRepository.findByProductid(cart.getCartproduct().getProductid());
        var idUser = userRepository.findByid((Long) request.getAttribute("idUser"));

        if (cartId != null) {
            Map<String, String> flashmsg = new HashMap<>();
            flashmsg.put("error", "Algo deu errado, Verifique seus dados!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(flashmsg);
        }

        cart.setCartproduct(cartProduct);
        cart.setCartuser(idUser);
        this.cartShoppingRepository.save(cart);

        Map<String, String> flashmsg = new HashMap<>();
        flashmsg.put("msg", "Produto " + cartProduct.getNomeproduct() + " Adicionado!");
        return ResponseEntity.status(HttpStatus.CREATED).body(flashmsg);

    }
    @GetMapping("/user/auth")
    public List<Object[]> getAllCartProductsByUser(HttpServletRequest request){
        var idUser = userRepository.findByid((Long) request.getAttribute("idUser"));
        return this.cartShoppingRepository.findCartShoppingInfoByUsername(idUser.getId());
    }
}