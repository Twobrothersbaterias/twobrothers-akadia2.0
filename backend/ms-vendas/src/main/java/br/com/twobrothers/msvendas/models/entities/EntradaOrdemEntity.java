package br.com.twobrothers.msvendas.models.entities;

import br.com.twobrothers.msvendas.models.enums.TipoEntradaEnum;
import br.com.twobrothers.msvendas.models.enums.TipoOrdemEnum;
import lombok.*;

import javax.persistence.*;

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
    private Long produtoEstoqueId;

    @Enumerated(EnumType.STRING)
    private TipoOrdemEnum tipoOrdem;

    @Enumerated(EnumType.STRING)
    private TipoOrdemEnum tipoEntrada;

    @ManyToOne(targetEntity = OrdemEntity.class, fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "ordem_id")
    private OrdemEntity ordem;

}
