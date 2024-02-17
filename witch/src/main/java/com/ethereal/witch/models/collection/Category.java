package com.ethereal.witch.models.collection;

import com.ethereal.witch.models.product.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import  jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Data
@Entity
public class Category {
    @Id
    @Getter
    @Setter
    @Schema(example="1")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "increment")
    private Long categoryid;
    @Getter
    @Setter
    @JsonIgnore
    private String nomecategory;
    @JsonIgnore
    @OneToMany(mappedBy = "productcategory")
    private List<Product> product;
    public Category() {
    }
    public Category(String nomecategory){
        this.nomecategory = nomecategory;
    }
}
