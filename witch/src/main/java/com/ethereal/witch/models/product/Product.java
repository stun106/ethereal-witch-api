package com.ethereal.witch.models.product;

import com.ethereal.witch.models.collection.Category;

import com.ethereal.witch.models.product_type.TypeProduct;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
public class Product {
    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.AUTO,generator = "increment")
    @Schema(example = "1")
    private Long productid;
    @Getter
    @Setter
    @JsonIgnore
    @Column(unique = true)
    private String nomeproduct;
    @Getter
    @Setter
    @JsonIgnore
    private BigDecimal valor;
    @Getter
    @Setter
    @JsonIgnore
    private String image;
    @Getter
    @Setter
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "nometype")
    private TypeProduct nometype;
    @Getter
    @Setter
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "productcategory")
    private Category productcategory;
    @Getter
    @Setter
    @JsonIgnore
    private LocalDate createdat = LocalDate.now();

    public Product(){

    }
    public Product(String nomeproduct, BigDecimal valor) {
        this.nomeproduct = nomeproduct;
        this.valor = valor;
    }
}
