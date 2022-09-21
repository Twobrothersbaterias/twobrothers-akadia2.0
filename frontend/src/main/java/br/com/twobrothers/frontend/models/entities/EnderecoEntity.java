package br.com.twobrothers.frontend.models.entities;

import br.com.twobrothers.frontend.models.enums.EstadoEnum;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author Gabriel Lagrota
 * @email gabriellagrota23@gmail.com
 * @phone (11)97981-5415
 * @github https://github.com/LagrotaGabriel
 * @version 1.0
 * @since 30-08-22
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Table(name = "tb_endereco")
@SequenceGenerator(allocationSize = 1, sequenceName = "sq_endereco", name = "endereco")
public class EnderecoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "sq_endereco")
    private Long id;

    private LocalDateTime dataCadastro;
    private String logradouro;
    private Integer numero;
    private String bairro;
    private String cidade;
    private String cep;
    private String complemento;

    @Enumerated(EnumType.STRING)
    private EstadoEnum estado;

    @OneToOne(targetEntity = ClienteEntity.class, mappedBy = "endereco", fetch = FetchType.LAZY)
    private ClienteEntity cliente;

    @OneToOne(targetEntity = FornecedorEntity.class, mappedBy = "endereco", fetch = FetchType.LAZY)
    private FornecedorEntity fornecedor;

}
