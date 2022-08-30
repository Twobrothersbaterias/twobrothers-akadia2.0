package br.com.twobrothers.msdespesas.models.dto;

import br.com.twobrothers.msdespesas.models.enums.StatusDespesaEnum;
import br.com.twobrothers.msdespesas.models.enums.TipoDespesaEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
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
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString @EqualsAndHashCode
public class DespesaDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime dataCadastro;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private String dataPagamento;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private String dataAgendamento;
    private String descricao;
    private Double valor;
    private StatusDespesaEnum statusDespesa;
    private TipoDespesaEnum tipoDespesa;

    private Long idUsuarioResponsavel;

}
