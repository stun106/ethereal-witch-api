package com.ethereal.witch.interfaces;

import com.ethereal.witch.models.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface IProductRepository extends JpaRepository<Product, Long> {
    Product findByNomeproduct(String nomeProduct);
    Product findByProductid(Long id);

}
