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
@Getter
@Setter
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "increment")
    private Long idendereco;
    @Column(nullable = false)
    private String cep;
    @Column(nullable = false)
    private String cidade;
    @Column(nullable = false)
    private String estado;
    private String logradouro;
    @Column(nullable = false)
    private String bairro;
    private String numero;
    private String complemento;
    @OneToOne(mappedBy = "endereco")
    private UserClient enderecouser;
}
