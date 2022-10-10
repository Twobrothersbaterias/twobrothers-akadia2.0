package br.com.twobrothers.frontend.models.dto;

import br.com.twobrothers.frontend.models.enums.FormaPagamentoEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class AbastecimentoDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String dataCadastro;

    @JsonProperty(required = true)
    private Integer quantidade;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Double custoUnitario;

    @JsonProperty(required = true)
    private Double custoTotal;

    @JsonProperty
    private String observacao;

    @JsonProperty(required = true)
    private Long idUsuarioResponsavel;
    @JsonProperty(required = true)
    private FormaPagamentoEnum formaPagamento;

    @JsonIgnore
    private ProdutoEstoqueDTO produtoEstoque;

    @JsonIgnore
    private FornecedorDTO fornecedor;

}
