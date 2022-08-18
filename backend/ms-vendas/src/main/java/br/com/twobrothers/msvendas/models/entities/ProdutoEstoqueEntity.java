package br.com.twobrothers.msvendas.models.entities;

import br.com.twobrothers.msvendas.models.enums.TipoProdutoEnum;
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
@Table(name = "TB_PRODUTO")
public class ProdutoEstoqueEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataCadastro;
    private String sigla;
    private String marcaBateria;
    private String especificacao;
    private Integer quantidade;
    private Integer quantidadeMinima;
    private Long idUsuarioResponsavel;

    @Enumerated(EnumType.STRING)
    private TipoProdutoEnum tipoProduto;

    @OneToMany(targetEntity = AbastecimentoEntity.class, cascade = CascadeType.ALL)
    private List<AbastecimentoEntity> abastecimentos = new ArrayList<>();

    @OneToMany(targetEntity = PrecoFornecedorEntity.class, cascade = CascadeType.ALL)
    private List<PrecoFornecedorEntity> precosFornecedor = new ArrayList<>();

    @OneToMany(targetEntity = OrdemEntity.class, cascade = CascadeType.ALL)
    private List<OrdemEntity> ordens = new ArrayList<>();

}
