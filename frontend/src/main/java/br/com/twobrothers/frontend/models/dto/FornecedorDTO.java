package br.com.twobrothers.frontend.models.dto;

import br.com.twobrothers.frontend.models.entities.UsuarioEntity;
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
public class FornecedorDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String dataCadastro;

    private String nome;
    private String cpfCnpj;
    private String telefone;
    private String email;
    private String dataNascimento;
    private UsuarioDTO usuarioResponsavel;
    private EnderecoDTO endereco;

    private List<PrecoFornecedorDTO> precosFornecedor = new ArrayList<>();
    private List<AbastecimentoDTO> abastecimentos = new ArrayList<>();

}
