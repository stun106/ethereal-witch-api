package com.ethereal.witch.models.user;


import com.ethereal.witch.models.shoppingcart.CartShopping;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "increment")
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    @Column(name = "username", nullable = false,unique = true)
    private String username;
    @Getter
    @Setter
    private String password;
    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    private AccessUser access;
    @OneToMany(mappedBy = "cartuser")
    private List<CartShopping> carts;

    public User() {
    }

    public User(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }
}
