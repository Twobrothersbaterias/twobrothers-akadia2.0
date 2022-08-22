package br.com.twobrothers.msvendas.models.entities;

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
@Table(name = "tb_fornecedor")
public class FornecedorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataCadastro;
    private String nome;
    private String cpfCnpj;
    private String telefone;
    private String email;
    private Long idUsuarioResponsavel;

    @ManyToOne(targetEntity = EnderecoEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_endereco")
    private EnderecoEntity endereco;

    @OneToMany(targetEntity = PrecoFornecedorEntity.class, cascade = CascadeType.ALL)
    private List<PrecoFornecedorEntity> precosFornecedor = new ArrayList<>();

    @OneToMany(targetEntity = AbastecimentoEntity.class, cascade = CascadeType.ALL)
    private List<AbastecimentoEntity> abastecimentos = new ArrayList<>();

}
