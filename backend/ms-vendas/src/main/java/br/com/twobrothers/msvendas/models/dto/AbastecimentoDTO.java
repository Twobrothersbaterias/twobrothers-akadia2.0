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

    private Long id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime dataCadastro;
    private Integer quantidade;

    //TODO Deve ser calculado com base no custo total (custoTotal/quantidade)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Double custoUnitario;

    private Double custoTotal;
    private String observacao;
    private Long idUsuarioResponsavel;
    private FormaPagamentoEnum formaPagamento;
    private ProdutoEstoqueDTO produtoEstoque;
    private TrocaDTO troca;
    private FornecedorDTO fornecedor;

}
