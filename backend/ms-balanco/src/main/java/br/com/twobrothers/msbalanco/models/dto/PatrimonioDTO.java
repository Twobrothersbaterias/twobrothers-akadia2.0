package br.com.twobrothers.msbalanco.models.dto;

import br.com.twobrothers.msbalanco.models.enums.StatusPatrimonioEnum;
import br.com.twobrothers.msbalanco.models.enums.TipoPatrimonioEnum;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class PatrimonioDTO {

    private Long id;
    private String nome;
    private LocalDateTime dataCadastro;
    private TipoPatrimonioEnum tipoPatrimonio;
    private StatusPatrimonioEnum statusPatrimonio;
    private String dataAgendamentoPatrimonio;
    private Double valorPatrimonio;
    private Long idUsuarioResponsavel;

}
