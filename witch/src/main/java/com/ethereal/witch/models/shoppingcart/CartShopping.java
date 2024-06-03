package com.ethereal.witch.models.shoppingcart;

import com.ethereal.witch.models.product.Product;
import com.ethereal.witch.models.user.UserClient;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Data
@Entity
public class CartShopping {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,generator = "increment")
    @Getter
    @Setter
    @JsonIgnore
    private Long cartid;
    @ManyToOne
    @Schema(implementation = Product.class)
    @JoinColumn(name = "cartproductid")
    private Product cartproduct;
    @Getter
    @Setter
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "cartuserid")
    private UserClient cartuser;
    @Getter
    @Setter
    @JsonIgnore
    private LocalDate dateregister = LocalDate.now();

    public CartShopping(){

    }

}
