package br.com.twobrothers.frontend.models.dto.filters;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class FiltroPatrimonioDTO {

    String descricao;
    String dataInicio;
    String dataFim;
    String periodoMes;
    String periodoAno;
    String tipo;

}

