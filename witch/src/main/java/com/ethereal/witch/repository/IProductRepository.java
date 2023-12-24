package com.ethereal.witch.interfaces;

import com.ethereal.witch.models.collection.Category;
import com.ethereal.witch.models.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface IProductRepository extends JpaRepository<Product, Long> {
    Product findByNomeproduct(String nomeProduct);
    Product findByProductid(Long id);

    @Query("SELECT p.nomeproduct, p.valor, p.image FROM Product p WHERE p.productid = :id")
    Object[] findIdProduct(@Param("id") Long id);
    @Query("SELECT p.nomeproduct " +
            "FROM Product p " +
            "JOIN TypeProduct tp ON nometype = tp " +
            "WHERE tp.typename = :nome")
    List<Object[]> findProductByType(@Param("nome") String type);
    @Query("SELECT p.nomeproduct " +
            "FROM Product p " +
            "JOIN Category c ON p.productcategory = c " +
            "WHERE c.nomecategory = :nome")
    List<Object[]> findProductCategory(@Param("nome") String nCat);
}
