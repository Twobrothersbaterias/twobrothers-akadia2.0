package br.com.twobrothers.msvendas.models.dto;

import br.com.twobrothers.msvendas.models.enums.FormaPagamentoEnum;
import br.com.twobrothers.msvendas.models.enums.LojaEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @JsonProperty(required = false)
    private String veiculo;

    @JsonProperty(required = true)
    private Boolean emiteNfe;

    @JsonProperty(required = true)
    private Long idUsuarioResponsavel;

    @JsonProperty(required = true)
    private LojaEnum loja;

    @JsonProperty(required = true)
    private FormaPagamentoEnum formaPagamento;

    @JsonProperty(required = false, access = JsonProperty.Access.WRITE_ONLY)
    private ClienteDTO cliente;

    @JsonProperty(required = true)
    private RetiradaDTO retirada;

    private List<EntradaOrdemDTO> entradas = new ArrayList<>();

}
