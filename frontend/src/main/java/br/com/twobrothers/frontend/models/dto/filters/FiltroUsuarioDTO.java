package br.com.twobrothers.frontend.models.dto.filters;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class FiltroUsuarioDTO {

    String descricao;
    String dataInicio;
    String dataFim;
    String periodoMes;
    String periodoAno;
    String username;

}
