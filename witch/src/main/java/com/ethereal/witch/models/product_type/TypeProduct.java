package com.ethereal.witch.models.product_type;

import com.ethereal.witch.models.product.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(example = "1")
    private Long typeid;
    @Getter
    @Setter
    @JsonIgnore
    @Column(name = "typename")
    private String typename;
    @JsonIgnore
    @OneToMany(mappedBy = "nometype")
    private List<Product> type;

    public TypeProduct() {
    }

    public TypeProduct(String typename) {
        this.typename = typename;
    }
}
