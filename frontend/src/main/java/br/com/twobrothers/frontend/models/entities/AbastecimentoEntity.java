package br.com.twobrothers.frontend.models.entities;

import br.com.twobrothers.frontend.models.enums.FormaPagamentoEnum;
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
@Table(name = "tb_abastecimento")
public class AbastecimentoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String dataCadastro;
    private Integer quantidade;
    private Double custoUnitario;
    private Double custoTotal;
    private String observacao;
    private String usuarioResponsavel;

    @Enumerated(EnumType.STRING)
    private FormaPagamentoEnum formaPagamento;

    @ManyToOne(targetEntity = ProdutoEstoqueEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id")
    private ProdutoEstoqueEntity produto;

    @ManyToOne(targetEntity = FornecedorEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "fornecedor_id")
    private FornecedorEntity fornecedor;

}
