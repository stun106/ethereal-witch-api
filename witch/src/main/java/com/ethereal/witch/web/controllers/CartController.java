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
import com.ethereal.witch.web.dto.ProductResponseDto;
import com.ethereal.witch.web.dto.UserResponseDto;
import com.ethereal.witch.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
@Tag(name = "CartShopping", description = "Contains all the information related CartShopping of user and product, register, editing and reading a product in cart.")
@RestController
@RequestMapping("witch/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final UserService userService;
    private final ProductService productService;
    @Operation(summary = "Creates a new resource.", description = "Resource for created a new cartshop of user.",
            responses = {@ApiResponse(responseCode = "201", description = "Resourse created sucessfully.",
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = CartShopping.class))),
                    @ApiResponse(responseCode = "401",description = "Need authentication.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "400",description = "Password invalid.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
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
    @Operation(summary = "Returns all products of user.", description = "Resource for returns all products of user.",
            responses = {@ApiResponse(responseCode = "201", description = "Products of user returns sucessfully.",
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = CartShopping.class))),
                    @ApiResponse(responseCode = "400",description = "Password invalid.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "401",description = "Need authentication.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404",description = "User not found.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            })
    @GetMapping("/user/auth")
    public ResponseEntity<List<Object>> getAllCartProductsByUser(HttpServletRequest request){
        List<Object> cart = cartService.findCartInfoByUser(request);
        return ResponseEntity.status(HttpStatus.OK).body(cart);
    }
    @Operation(summary = "Delete resource by id.", description = "Resource for delete product of cart.",
            responses = {@ApiResponse(responseCode = "201", description = "Resourse deleted sucessfully.",
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = ProductResponseDto.class))),
                    @ApiResponse(responseCode = "400",description = "Password invalid.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "401",description = "Need authentication.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404",description = "Resources not found.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @DeleteMapping("/del/{id}/auth")
    public ResponseEntity destroy(@PathVariable("id") Long id) {
        CartShopping cart = cartService.findByCartid(id);
        cartService.destroyCart(id);
        Map<String, String> flashmsg = new HashMap<>();
        flashmsg.put("msg", cart.getCartproduct().getNomeproduct() + " foi removido com sucesso!");
        return ResponseEntity.status(HttpStatus.OK).body(flashmsg);
        }
}