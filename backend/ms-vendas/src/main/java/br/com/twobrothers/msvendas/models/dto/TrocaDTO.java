package br.com.twobrothers.msvendas.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class TrocaDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime dataCadastro;

    @JsonProperty(required = true, defaultValue = "0.0")
    private Double valorTroca;

    @JsonProperty(required = true)
    private Integer quantidade;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private OrdemDTO ordem;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private AbastecimentoDTO abastecimento;

}
