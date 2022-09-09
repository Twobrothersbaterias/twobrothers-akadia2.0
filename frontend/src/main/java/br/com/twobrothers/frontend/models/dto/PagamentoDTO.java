package br.com.twobrothers.frontend.models.dto;

import br.com.twobrothers.frontend.models.entities.OrdemEntity;
import br.com.twobrothers.frontend.models.enums.FormaPagamentoEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * @author Gabriel Lagrota
 * @email gabriellagrota23@gmail.com
 * @phone (11)97981-5415
 * @github https://github.com/LagrotaGabriel
 * @version 1.0
 * @since 30-08-22
 */
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
