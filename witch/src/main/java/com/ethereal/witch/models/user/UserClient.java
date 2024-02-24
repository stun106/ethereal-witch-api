package com.ethereal.witch.models.user;


import com.ethereal.witch.models.endereco.Endereco;
import com.ethereal.witch.models.shoppingcart.CartShopping;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
public class UserClient {
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
    private AccessUser access = AccessUser.ADMIN;
    @Getter
    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id", referencedColumnName = "idendereco")
    private Endereco endereco;

    public UserClient() {
    }

    public UserClient(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }
}
