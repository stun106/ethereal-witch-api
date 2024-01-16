package com.ethereal.witch.repository;

import com.ethereal.witch.models.product_type.TypeProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ITypeRepository extends JpaRepository<TypeProduct,Long> {
    @Override
    Optional<TypeProduct> findById(Long id);

    TypeProduct findByTypeid(Long id);

    TypeProduct findByTypename(String type);

}
