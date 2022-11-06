package br.com.twobrothers.frontend.models.dto.filters;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class FiltroPostagemDTO {

    String titulo;
    String dataInicio;
    String dataFim;
    String periodoMes;
    String periodoAno;
    String categoria;

}

