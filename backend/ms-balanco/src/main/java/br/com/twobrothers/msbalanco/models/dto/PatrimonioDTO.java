package br.com.twobrothers.msbalanco.models.dto;

import br.com.twobrothers.msbalanco.models.enums.StatusPatrimonioEnum;
import br.com.twobrothers.msbalanco.models.enums.TipoPatrimonioEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class PatrimonioDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime dataCadastro;

    private String nome;
    private TipoPatrimonioEnum tipoPatrimonio;
    private StatusPatrimonioEnum statusPatrimonio;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private String dataAgendamento;

    private Double valor;
    private Long idUsuarioResponsavel;

}
