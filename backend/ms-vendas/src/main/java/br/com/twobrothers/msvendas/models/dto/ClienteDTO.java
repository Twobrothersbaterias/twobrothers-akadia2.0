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
public class ClienteDTO {

    private Long id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime dataCadastro;

    private String nomeCompleto;
    private String cpfCnpj;
    private String email;
    private String telefone;
    private Long idUsuarioResponsavel;
    private EnderecoDTO endereco;
    private List<OrdemDTO> ordens = new ArrayList<>();

}
