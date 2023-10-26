package com.ethereal.witch.models.collection;

import com.ethereal.witch.models.product.Product;
import  jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Data
@Entity
public class Category{
    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "increment")
    private Long categoryid;

    @Getter
    @Setter
    private String nomecategory;

    @OneToMany(mappedBy = "productcategory")
    private List<Product> product;
    public Category() {
    }
    public Category(String nomecategory){
        this.nomecategory = nomecategory;
    }

}
