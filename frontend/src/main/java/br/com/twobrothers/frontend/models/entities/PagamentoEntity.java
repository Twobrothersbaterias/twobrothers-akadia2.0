package br.com.twobrothers.frontend.models.entities;

import br.com.twobrothers.frontend.models.enums.FormaPagamentoEnum;
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
