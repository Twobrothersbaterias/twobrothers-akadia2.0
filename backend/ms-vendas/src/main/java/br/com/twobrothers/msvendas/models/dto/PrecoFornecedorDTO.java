package br.com.twobrothers.msvendas.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class PrecoFornecedorDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime dataCadastro;

    @JsonProperty(required = true)
    private Double valor;
    private String observacao;

    @JsonProperty(required = true)
    private Long idUsuarioResponsavel;

    @JsonProperty(required = true, access = JsonProperty.Access.WRITE_ONLY)
    private ProdutoEstoqueDTO produto;

    @JsonProperty(required = true, access = JsonProperty.Access.WRITE_ONLY)
    private FornecedorDTO fornecedor;

}
