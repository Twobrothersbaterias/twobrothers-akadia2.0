package br.com.twobrothers.msvendas.models.dto;

import br.com.twobrothers.msvendas.models.entities.OrdemEntity;
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

    private Long id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime dataCadastro;

    private Double valorTroca;
    private OrdemEntity ordem;
    private AbastecimentoDTO abastecimento;

}
