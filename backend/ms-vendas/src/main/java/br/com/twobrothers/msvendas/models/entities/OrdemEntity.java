package br.com.twobrothers.msvendas.models.entities;

import br.com.twobrothers.msvendas.models.enums.FormaPagamentoEnum;
import br.com.twobrothers.msvendas.models.enums.LojaEnum;
import br.com.twobrothers.msvendas.models.enums.TipoOrdemEnum;
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
@Table(name = "tb_ordem")
public class OrdemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataCadastro;
    private Double valor;
    private Integer quantidade;
    private String veiculo;
    private Boolean emiteNfe;
    private Long idUsuarioResponsavel;

    @Enumerated(EnumType.STRING)
    private TipoOrdemEnum tipoOrdem;
    @Enumerated(EnumType.STRING)
    private LojaEnum loja;
    @Enumerated(EnumType.STRING)
    private FormaPagamentoEnum formaPagamento;

    @ManyToOne(targetEntity = ClienteEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private ClienteEntity cliente;

    //TODO Ponto de atenção
    @OneToOne(targetEntity = RetiradaEntity.class, fetch = FetchType.LAZY, mappedBy = "ordem", cascade = CascadeType.ALL)
    private RetiradaEntity retirada;

    @ManyToMany(targetEntity = ProdutoEstoqueEntity.class, fetch = FetchType.LAZY, mappedBy = "ordens", cascade = CascadeType.ALL)
    private List<ProdutoEstoqueEntity> produtos = new ArrayList<>();

    @OneToMany(targetEntity = TrocaEntity.class, fetch = FetchType.LAZY, mappedBy = "ordem", cascade = CascadeType.ALL)
    private List<TrocaEntity> trocas = new ArrayList<>();

}
