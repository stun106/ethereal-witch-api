package com.ethereal.witch.service;

import com.ethereal.witch.models.shoppingcart.CartShopping;
import com.ethereal.witch.models.user.UserClient;
import com.ethereal.witch.repository.ICartShoppingRepository;
import com.ethereal.witch.repository.IUserRepository;
import com.ethereal.witch.service.exception.EntityNotfoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
@Service
@RequiredArgsConstructor
public class CartService {
    private final ICartShoppingRepository iCartShoppingRepository;
    private final IUserRepository iUserRepository;
    @Transactional
    public CartShopping saveCart(CartShopping cart){
        return iCartShoppingRepository.save(cart);
    }
    @Transactional(readOnly = true)
    public CartShopping findByCartid(Long id){
        return iCartShoppingRepository.findById(id).orElseThrow(() ->
                new EntityNotfoundException(String.format("Cart id {%d} not found", id)));
    }
    @Transactional(readOnly = true)
    public List<Object> findCartInfoByUser(HttpServletRequest request){
        UserClient user = iUserRepository.findByid((Long) request.getAttribute("idUser"));
        return  iCartShoppingRepository.findCartShoppingInfoByUsername(user.getId());
    }
    @Transactional
    public void destroyCart(Long id){
        iCartShoppingRepository.delete(findByCartid(id));
    }
}
