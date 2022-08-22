package br.com.twobrothers.msdespesas.models.entities;

import br.com.twobrothers.msdespesas.models.enums.StatusDespesaEnum;
import br.com.twobrothers.msdespesas.models.enums.TipoDespesaEnum;
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
@Table(name = "tb_despesa")
public class DespesaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataCadastro;
    private String dataPagamento;
    private String dataAgendamento;
    private String descricao;
    private Double valor;

    @Enumerated(EnumType.STRING)
    private StatusDespesaEnum statusDespesa;

    @Enumerated(EnumType.STRING)
    private TipoDespesaEnum tipoDespesa;

    private Long idUsuarioResponsavel;

}
