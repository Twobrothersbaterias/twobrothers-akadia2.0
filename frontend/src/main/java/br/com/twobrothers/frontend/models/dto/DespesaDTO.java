package br.com.twobrothers.frontend.models.dto;

import br.com.twobrothers.frontend.models.entities.DespesaEntity;
import br.com.twobrothers.frontend.models.enums.PersistenciaEnum;
import br.com.twobrothers.frontend.models.enums.StatusDespesaEnum;
import br.com.twobrothers.frontend.models.enums.TipoDespesaEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class DespesaDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String dataCadastro;
    private String dataPagamento;
    private String dataAgendamento;
    private String descricao;
    private Double valor;
    private StatusDespesaEnum statusDespesa;
    private TipoDespesaEnum tipoDespesa;
    private PersistenciaEnum persistencia;

    private UsuarioDTO usuarioResponsavel;

}