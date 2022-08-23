package br.com.twobrothers.msvendas.models.dto;

import br.com.twobrothers.msvendas.models.enums.FormaPagamentoEnum;
import br.com.twobrothers.msvendas.models.enums.LojaEnum;
import br.com.twobrothers.msvendas.models.enums.TipoOrdemEnum;
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

    private Double valor;
    private Integer quantidade;
    private String veiculo;
    private Boolean emiteNfe;
    private Long idUsuarioResponsavel;
    private TipoOrdemEnum tipoOrdem;
    private LojaEnum loja;
    private FormaPagamentoEnum formaPagamento;
    private ClienteDTO cliente;
    private RetiradaDTO retirada;
    private List<ProdutoEstoqueDTO> produtos = new ArrayList<>();
    private List<TrocaDTO> trocas = new ArrayList<>();

}
