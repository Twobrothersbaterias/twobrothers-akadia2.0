package br.com.twobrothers.msvendas.models.dto;

import br.com.twobrothers.msvendas.models.enums.EstadoEnum;
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
public class EnderecoDTO {

    private Long id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime dataCadastro;

    private String logradouro;
    private String bairro;
    private String cep;
    private String complemento;
    private EstadoEnum estado;
    private List<ClienteDTO> clientes = new ArrayList<>();
    private List<FornecedorDTO> fornecedores = new ArrayList<>();

}
