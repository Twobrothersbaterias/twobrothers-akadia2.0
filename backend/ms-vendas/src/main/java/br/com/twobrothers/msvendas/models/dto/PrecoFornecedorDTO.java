package br.com.twobrothers.msvendas.models.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonProperty(required = false)
    private String observacao;

    @JsonProperty(required = true)
    private Long idUsuarioResponsavel;

    @JsonIgnore
    private ProdutoEstoqueDTO produto;

    @JsonIgnore
    private FornecedorDTO fornecedor;

}
