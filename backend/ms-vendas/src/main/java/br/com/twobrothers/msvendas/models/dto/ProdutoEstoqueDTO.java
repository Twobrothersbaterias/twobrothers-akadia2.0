package br.com.twobrothers.msvendas.models.dto;

import br.com.twobrothers.msvendas.models.enums.TipoProdutoEnum;
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
public class ProdutoEstoqueDTO {

    private Long id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime dataCadastro;

    @JsonProperty(required = true)
    private String sigla;

    @JsonProperty(required = true)
    private String marcaBateria;

    @JsonProperty(required = true)
    private String especificacao;

    @JsonProperty(defaultValue = "0")
    private Integer quantidadeMinima;

    @JsonProperty(required = true)
    private Long idUsuarioResponsavel;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer quantidade;

    @JsonProperty(required = true)
    private TipoProdutoEnum tipoProduto;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<AbastecimentoDTO> abastecimentos = new ArrayList<>();

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<PrecoFornecedorDTO> precosFornecedor = new ArrayList<>();

    private List<EntradaOrdemDTO> entradas = new ArrayList<>();

}
