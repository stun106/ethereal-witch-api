package com.ethereal.witch.repository;

import com.ethereal.witch.models.shoppingcart.CartShopping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ICartShoppingRepository extends JpaRepository<CartShopping, Long> {
    CartShopping findByCartid(Long id);
    @Override
    Optional<CartShopping> findById(Long id);

   @Query("SELECT p.nomeproduct, p.valor " +
            "FROM CartShopping cs " +
            "JOIN cs.cartuser u " +
            "JOIN cs.cartproduct p " +
            "WHERE u.id = :userId")
    List<Object> findCartShoppingInfoByUsername(@Param("userId") Long UserId);
}

