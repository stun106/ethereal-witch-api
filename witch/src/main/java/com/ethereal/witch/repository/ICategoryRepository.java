package com.ethereal.witch.repository;

import com.ethereal.witch.models.collection.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ICategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByCategoryid(Long idCategory);
    Category findByNomecategory(String category);
    @Query("SELECT c.nomecategory FROM Category c WHERE c.categoryid = :categoryid")
    Object[] findByid(@Param("categoryid") Long id);

    @Query("SELECT c.nomecategory " + "FROM Category c")
    List<Category> findAllNameCategory();
}

