package com.ethereal.witch.models.endereco;

import com.ethereal.witch.models.user.UserClient;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "increment")
    @Getter
    @Setter
    private Long idendereco;
    @Getter
    @Setter
    @Column(nullable = false)
    private String cep;
    @Column(nullable = false)
    @Getter
    @Setter
    private String logradouro;
    @Column(nullable = false)
    @Getter
    @Setter
    private String bairro;
    @Getter
    @Setter
    private String numero;
    @Getter
    @Setter
    private String complemento;
    @ManyToOne
    @JoinColumn(name = "userid", referencedColumnName = "id")
    private UserClient listuser;
}
