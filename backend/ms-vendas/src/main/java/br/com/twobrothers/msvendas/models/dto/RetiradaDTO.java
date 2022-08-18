package br.com.twobrothers.msvendas.models.dto;

import br.com.twobrothers.msvendas.models.enums.StatusRetiradaEnum;
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
public class RetiradaDTO {

    private Long id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime dataRetirada;

    private String observacao;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private String dataAgendamento;

    private String tecnicoEntrada;
    private String tecnicoSaida;
    private StatusRetiradaEnum statusRetirada;
    private OrdemDTO ordem;

}
