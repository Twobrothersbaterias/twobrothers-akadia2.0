package br.com.twobrothers.frontend.models.dto;

import br.com.twobrothers.frontend.models.enums.PersistenciaEnum;
import br.com.twobrothers.frontend.models.enums.StatusDespesaEnum;
import br.com.twobrothers.frontend.models.enums.TipoDespesaEnum;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class DespesaDTO {
    private Long id;
    private LocalDateTime dataCadastro;
    private String dataPagamento;
    private String dataAgendamento;
    private String descricao;
    private Double valor;
    private StatusDespesaEnum statusDespesa;
    private TipoDespesaEnum tipoDespesa;
    private PersistenciaEnum persistencia;
    private Long idUsuarioResponsavel;
}