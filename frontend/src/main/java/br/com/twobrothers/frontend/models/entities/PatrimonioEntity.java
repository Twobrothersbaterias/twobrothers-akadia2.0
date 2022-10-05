package br.com.twobrothers.frontend.models.entities;

import br.com.twobrothers.frontend.models.enums.StatusPatrimonioEnum;
import br.com.twobrothers.frontend.models.enums.TipoPatrimonioEnum;
import lombok.*;

import javax.persistence.*;

/**
 * @author Gabriel Lagrota
 * @email gabriellagrota23@gmail.com
 * @phone (11)97981-5415
 * @github https://github.com/LagrotaGabriel
 * @version 1.0
 * @since 30-08-22
 */
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
    private String dataCadastro;
    @Enumerated(EnumType.STRING)
    private TipoPatrimonioEnum tipoPatrimonio;
    @Enumerated(EnumType.STRING)
    private StatusPatrimonioEnum statusPatrimonio;
    private String dataEntrada;
    private Double valor;
    private String usuarioResponsavel;

}
