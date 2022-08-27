package br.com.twobrothers.msvendas.models.dto;

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
public class FornecedorDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime dataCadastro;

    private String nome;
    private String cpfCnpj;
    private String telefone;
    private String email;
    private Long idUsuarioResponsavel;
    private EnderecoDTO endereco;

    private List<PrecoFornecedorDTO> precosFornecedor = new ArrayList<>();
    private List<AbastecimentoDTO> abastecimentos = new ArrayList<>();

}
