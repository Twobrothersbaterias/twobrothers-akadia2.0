package br.com.twobrothers.msbalanco.models.entities;

import br.com.twobrothers.msbalanco.models.enums.StatusPatrimonioEnum;
import br.com.twobrothers.msbalanco.models.enums.TipoPatrimonioEnum;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Table(name = "TB_PATRIMONIO")
public class PatrimonioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private LocalDateTime dataCadastro;

    @Enumerated(EnumType.STRING)
    private TipoPatrimonioEnum tipoPatrimonio;

    @Enumerated(EnumType.STRING)
    private StatusPatrimonioEnum statusPatrimonio;
    private String dataAgendamento;
    private Double valor;
    private Long idUsuarioResponsavel;

}
