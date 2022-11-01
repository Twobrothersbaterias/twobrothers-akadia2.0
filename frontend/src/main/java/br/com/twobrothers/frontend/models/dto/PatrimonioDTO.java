package br.com.twobrothers.frontend.models.dto;

import br.com.twobrothers.frontend.models.enums.StatusPatrimonioEnum;
import br.com.twobrothers.frontend.models.enums.TipoPatrimonioEnum;
import lombok.*;

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
public class PatrimonioDTO {

    private Long id;
    private String dataCadastro;
    private String nome;
    private TipoPatrimonioEnum tipoPatrimonio;
    private StatusPatrimonioEnum statusPatrimonio;
    private String dataEntrada;
    private Double valor;
    private UsuarioDTO usuarioResponsavel;

}
