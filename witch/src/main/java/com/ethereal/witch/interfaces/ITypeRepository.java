package com.ethereal.witch.interfaces;

import com.ethereal.witch.models.product.Product;
import com.ethereal.witch.models.product_type.TypeProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository
public interface ITypeRepository extends JpaRepository<TypeProduct,Long> {
    TypeProduct findByTypeid(Long id);
    @Query("SELECT t.typename FROM TypeProduct t WHERE t.typeid = :typeId")
    TypeProduct findByid(@Param("typeId") Long typeId);

}
