package com.ethereal.witch.models.shoppingcart;

import com.ethereal.witch.models.product.Product;
import com.ethereal.witch.models.user.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
public class CartShopping {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,generator = "increment")
    @Getter
    @Setter
    private Long cartid;
    @ManyToOne
    @JoinColumn(name = "cartproductid")
    private Product cartproduct;
    @ManyToOne
    @JoinColumn(name = "cartuserid")
    private User cartuser;
    @Getter
    @Setter
    private LocalDate dateregister = LocalDate.now();

    public CartShopping(){

    }

}
