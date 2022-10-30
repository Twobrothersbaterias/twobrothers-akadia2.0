package br.com.twobrothers.frontend.models.dto;

import br.com.twobrothers.frontend.models.enums.StatusRetiradaEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class RetiradaDTO {

    private Long id;

    private String dataRetirada;

    private String observacao;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private String dataAgendamento;

    private UsuarioDTO tecnicoEntrada;

    private StatusRetiradaEnum statusRetirada;

    @JsonIgnore
    private OrdemDTO ordem;

    public void addOrdem(OrdemDTO ordem) {
        ordem.setRetirada(this);
        this.setOrdem(ordem);
    }

}
