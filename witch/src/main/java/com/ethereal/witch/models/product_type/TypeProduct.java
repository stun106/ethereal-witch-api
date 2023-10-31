package com.ethereal.witch.models.product_type;

import com.ethereal.witch.models.product.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
public class TypeProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "increment")
    @Getter
    @Setter
    private Long typeid;
    @Getter
    @Setter
    @Column(name = "typename")
    private String typename;
    @OneToMany(mappedBy = "nometype")
    private List<Product> type;

    public TypeProduct() {
    }

    public TypeProduct(String typename) {
        this.typename = typename;
    }
}
