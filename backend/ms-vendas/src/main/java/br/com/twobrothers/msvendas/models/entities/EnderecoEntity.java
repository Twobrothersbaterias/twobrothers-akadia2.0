package br.com.twobrothers.msvendas.models.entities;

import br.com.twobrothers.msvendas.models.enums.EstadoEnum;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Table(name = "tb_endereco")
public class EnderecoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataCadastro;
    private String logradouro;
    private String bairro;
    private String cep;
    private String complemento;

    @Enumerated(EnumType.STRING)
    private EstadoEnum estado;

    @OneToMany(targetEntity = ClienteEntity.class, cascade = CascadeType.ALL)
    private List<ClienteEntity> clientes = new ArrayList<>();

    @OneToMany(targetEntity = FornecedorEntity.class, cascade = CascadeType.ALL)
    private List<FornecedorEntity> fornecedores = new ArrayList<>();

}
