package br.com.twobrothers.msvendas.models.dto;

import br.com.twobrothers.msvendas.models.enums.TipoProdutoEnum;
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

    private String sigla;
    private String marcaBateria;
    private String especificacao;
    private Integer quantidade;
    private Integer quantidadeMinima;
    private Long idUsuarioResponsavel;
    private TipoProdutoEnum tipoProduto;
    private List<AbastecimentoDTO> abastecimentos = new ArrayList<>();
    private List<PrecoFornecedorDTO> precosFornecedor = new ArrayList<>();
    private List<OrdemDTO> ordens = new ArrayList<>();

}
