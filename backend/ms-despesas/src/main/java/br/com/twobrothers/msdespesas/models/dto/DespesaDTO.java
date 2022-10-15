package br.com.twobrothers.msdespesas.models.dto;

import br.com.twobrothers.msdespesas.models.enums.StatusDespesaEnum;
import br.com.twobrothers.msdespesas.models.enums.TipoDespesaEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString @EqualsAndHashCode
public class DespesaDTO {

    @JsonIgnore
    private Long id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime dataCadastro;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private String dataPagamento;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private String dataAgendamento;
    private String descricao;
    private Double valor;
    private StatusDespesaEnum statusDespesa;
    private TipoDespesaEnum tipoDespesa;

    //TODO private Usuario usuarioResponsavel;

}
