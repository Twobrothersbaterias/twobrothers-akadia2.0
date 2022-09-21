package br.com.twobrothers.frontend.models.dto.filters;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class FiltroClienteDTO {

    String descricao;
    String dataInicio;
    String dataFim;
    String periodoMes;
    String periodoAno;
    String cpfCnpj;
    String telefone;

}

