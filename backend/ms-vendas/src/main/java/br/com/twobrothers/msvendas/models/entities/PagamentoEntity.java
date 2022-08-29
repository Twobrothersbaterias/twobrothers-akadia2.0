package br.com.twobrothers.msvendas.models.entities;

import br.com.twobrothers.msvendas.models.enums.FormaPagamentoEnum;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Table(name = "tb_pagamento")
@SequenceGenerator(allocationSize = 1, sequenceName = "sq_pagamento", name = "pagamento")
public class PagamentoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "sq_pagamento")
    private Long id;
    private Double valor;
    private String observacao;
    private String dataPagamento;
    private String dataAgendamento;

    @Enumerated(EnumType.STRING)
    private FormaPagamentoEnum formaPagamento;

    @ManyToOne(targetEntity = OrdemEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "ordem_id")
    private OrdemEntity ordem;

}
