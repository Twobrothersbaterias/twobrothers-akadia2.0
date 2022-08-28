package br.com.twobrothers.msvendas.models.dto;

import br.com.twobrothers.msvendas.models.entities.EntradaOrdemEntity;
import br.com.twobrothers.msvendas.models.enums.TipoProdutoEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonProperty(required = false, defaultValue = "0")
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
