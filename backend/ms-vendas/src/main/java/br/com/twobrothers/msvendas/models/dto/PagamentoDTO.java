package br.com.twobrothers.msvendas.models.dto;

import br.com.twobrothers.msvendas.models.entities.OrdemEntity;
import br.com.twobrothers.msvendas.models.enums.FormaPagamentoEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class PagamentoDTO {

    private Long id;
    private Double valor;
    private String observacao;
    private String dataPagamento;
    private String dataAgendamento;
    private FormaPagamentoEnum formaPagamento;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private OrdemEntity ordem;

}
