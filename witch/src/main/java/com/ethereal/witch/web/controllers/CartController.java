package com.ethereal.witch.web.controllers;

import com.ethereal.witch.models.product.Product;
import com.ethereal.witch.models.user.User;
import com.ethereal.witch.repository.ICartShoppingRepository;
import com.ethereal.witch.repository.IProductRepository;
import com.ethereal.witch.repository.IUserRepository;

import com.ethereal.witch.models.shoppingcart.CartShopping;

import com.ethereal.witch.service.CartService;
import com.ethereal.witch.service.ProductService;
import com.ethereal.witch.service.UserService;
import jakarta.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("witch/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final UserService userService;
    private final ProductService productService;
    @Autowired
    private ICartShoppingRepository cartShoppingRepository;
    @Autowired
    private IProductRepository productRepository;
    @Autowired
    private IUserRepository userRepository;
    @PostMapping("/create/auth")
    public ResponseEntity create(@RequestBody CartShopping cart, HttpServletRequest request) {
        Product product = productService.findById(cart.getCartproduct().getProductid());
        User user = userService.findById((Long) request.getAttribute("idUser"));
        cart.setCartproduct(product);
        cart.setCartuser(user);
        CartShopping cartshp = cartService.saveCart(cart);
        Map<String, String> flashmsg = new HashMap<>();
        flashmsg.put("msg", "Produto " + cartshp.getCartproduct().getNomeproduct()  + " Adicionado!");
        return ResponseEntity.status(HttpStatus.CREATED).body(flashmsg);

    }

    @GetMapping("/user/auth")
    public ResponseEntity<List<Object>> getAllCartProductsByUser(HttpServletRequest request){
        List<Object> cart = cartService.findCartInfoByUser(request);
        return ResponseEntity.status(HttpStatus.OK).body(cart);
    }

    @DeleteMapping("/del/{id}/auth")
    public ResponseEntity destroy(@PathVariable("id") Long id) {
        CartShopping cart = cartService.findByCartid(id);
        cartService.destroyCart(id);
        Map<String, String> flashmsg = new HashMap<>();
        flashmsg.put("msg", cart.getCartproduct().getNomeproduct() + " foi removido com sucesso!");
        return ResponseEntity.status(HttpStatus.OK).body(flashmsg);
        }
}