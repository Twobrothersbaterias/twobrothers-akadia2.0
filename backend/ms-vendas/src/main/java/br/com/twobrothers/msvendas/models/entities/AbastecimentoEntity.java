package br.com.twobrothers.msvendas.models.entities;

import br.com.twobrothers.msvendas.models.enums.FormaPagamentoEnum;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Table(name = "tb_abastecimento")
public class AbastecimentoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime dataCadastro;
    private Integer quantidade;
    private Double custoUnitario;
    private Double custoTotal;
    private String observacao;
    private Long idUsuarioResponsavel;

    @Enumerated(EnumType.STRING)
    private FormaPagamentoEnum formaPagamento;

    @ManyToOne(targetEntity = ProdutoEstoqueEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id")
    private ProdutoEstoqueEntity produto;

    //TODO Ponto de atenção
    @OneToOne(targetEntity = TrocaEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_troca")
    private TrocaEntity troca;

    @ManyToOne(targetEntity = FornecedorEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "fornecedor_id")
    private FornecedorEntity fornecedor;

}
