package br.com.twobrothers.frontend.models.dto;

import br.com.twobrothers.frontend.models.enums.EstadoEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

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
public class EnderecoDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime dataCadastro;

    private String logradouro;
    private Integer numero;
    private String bairro;
    private String cidade;
    private String cep;
    private String complemento;
    private EstadoEnum estado;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private ClienteDTO cliente;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private FornecedorDTO fornecedor;

}
