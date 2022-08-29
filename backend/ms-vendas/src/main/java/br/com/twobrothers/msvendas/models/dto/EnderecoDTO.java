package br.com.twobrothers.msvendas.models.dto;

import br.com.twobrothers.msvendas.models.enums.EstadoEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class EnderecoDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime dataCadastro;

    private String logradouro;
    private Integer numero;
    private String bairro;
    private String cep;
    private String complemento;
    private EstadoEnum estado;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private ClienteDTO cliente;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private FornecedorDTO fornecedor;

}
