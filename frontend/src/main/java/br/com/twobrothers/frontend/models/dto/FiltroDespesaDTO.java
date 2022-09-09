package br.com.twobrothers.frontend.models.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class FiltroDespesaDTO {

    String descricao;
    String dataInicio;
    String dataFim;
    String periodoMes;
    String periodoAno;
    String tipo;

}
