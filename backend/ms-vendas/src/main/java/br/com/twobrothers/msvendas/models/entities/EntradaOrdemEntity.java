package br.com.twobrothers.msvendas.models.entities;

import br.com.twobrothers.msvendas.models.enums.TipoEntradaEnum;
import br.com.twobrothers.msvendas.models.enums.TipoOrdemEnum;
import lombok.*;

import javax.persistence.*;

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
@Table(name = "tb_produto_ordem")
@SequenceGenerator(allocationSize = 1, sequenceName = "sq_produto_ordem", name = "produto_ordem")
public class EntradaOrdemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "sq_produto_ordem")
    private Long id;

    private Double valor;
    private Integer quantidade;
    private String observacao;

    @Enumerated(EnumType.STRING)
    private TipoOrdemEnum tipoOrdem;

    @Enumerated(EnumType.STRING)
    private TipoEntradaEnum tipoEntrada;

    @ManyToOne(targetEntity = OrdemEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "ordem_id")
    private OrdemEntity ordem;

    @ManyToOne(targetEntity = ProdutoEstoqueEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id")
    private ProdutoEstoqueEntity produto;

}
