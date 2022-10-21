package br.com.twobrothers.frontend.models.dto;

import br.com.twobrothers.frontend.models.enums.LojaEnum;
import br.com.twobrothers.frontend.models.enums.NfeEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
public class OrdemDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime dataCadastro;

    private String veiculo;

    @JsonProperty(required = true)
    private NfeEnum tipoNfe;

    @JsonProperty(required = true)
    private Long idUsuarioResponsavel;

    @JsonProperty(required = true)
    private LojaEnum loja;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private ClienteDTO cliente;

    @JsonProperty(required = true)
    private RetiradaDTO retirada;

    private List<PagamentoDTO> pagamentos = new ArrayList<>();

    private List<EntradaOrdemDTO> entradas = new ArrayList<>();

}
