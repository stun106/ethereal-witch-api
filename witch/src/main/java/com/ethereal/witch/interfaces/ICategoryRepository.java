package com.ethereal.witch.interfaces;

import com.ethereal.witch.models.collection.Category;
import com.ethereal.witch.models.product.Product;
import com.ethereal.witch.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface ICategoryRepository extends JpaRepository<Category, Long> {
    Category findByCategoryid(Long nomeCategory);

    @Query("SELECT c.nomecategory FROM Category c WHERE c.categoryid = :categoryid")
    Object[] findByid(@Param("categoryid") Long id);

    @Query("SELECT c.nomecategory FROM Category c WHERE LOWER(c.nomecategory) LIKE LOWER(CONCAT('%', :nomecategory ,'%'))")
    List<Object[]> findBynamecategoyIlike(@Param("nomecategory") String nomecategory);

    @Query("SELECT c.nomecategory " + "FROM Category c")
    List<Object[]> findAllNameCategory();
}

