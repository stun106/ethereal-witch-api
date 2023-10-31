package com.ethereal.witch.models.product;

import com.ethereal.witch.models.collection.Category;

import com.ethereal.witch.models.product_type.TypeProduct;
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
    private Long productid;
    @Getter
    @Setter
    @Column(unique = true)
    private String nomeproduct;
    @Getter
    @Setter
    private BigDecimal valor;
    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "nometype")
    private TypeProduct nometype;
    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "productcategory")
    private Category productcategory;
    @Getter
    @Setter
    private LocalDate createdat = LocalDate.now();

    public Product(){

    }
    public Product(String nomeproduct, BigDecimal valor) {
        this.nomeproduct = nomeproduct;
        this.valor = valor;
    }
}
