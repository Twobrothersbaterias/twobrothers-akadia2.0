package br.com.twobrothers.frontend.models.dto.filters;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class FiltroAbastecimentoDTO {

    String dataInicio;
    String dataFim;
    String periodoMes;
    String periodoAno;
    String produto;
    String fornecedor;

}