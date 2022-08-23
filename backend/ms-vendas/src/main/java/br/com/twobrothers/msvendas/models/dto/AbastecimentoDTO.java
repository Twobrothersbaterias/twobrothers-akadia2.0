package br.com.twobrothers.msvendas.models.dto;

import br.com.twobrothers.msvendas.models.enums.FormaPagamentoEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class AbastecimentoDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime dataCadastro;

    @JsonProperty(required = true)
    private Integer quantidade;

    //TODO Deve ser calculado com base no custo total (custoTotal/quantidade)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Double custoUnitario;

    @JsonProperty(required = true)
    private Double custoTotal;

    @JsonProperty(required = false)
    private String observacao;

    @JsonProperty(required = true)
    private Long idUsuarioResponsavel;
    @JsonProperty(required = true)
    private FormaPagamentoEnum formaPagamento;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY, required = true)
    private ProdutoEstoqueDTO produtoEstoque;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY, required = false)
    private TrocaDTO troca;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY, required = false)
    private FornecedorDTO fornecedor;

}
