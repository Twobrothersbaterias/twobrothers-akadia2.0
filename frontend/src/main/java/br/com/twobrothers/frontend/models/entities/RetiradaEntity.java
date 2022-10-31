package br.com.twobrothers.frontend.models.entities;

import br.com.twobrothers.frontend.models.enums.StatusRetiradaEnum;
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
@Table(name = "tb_retirada")
public class RetiradaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String dataRetirada;
    private String observacao;
    private String dataAgendamento;

    @Enumerated(EnumType.STRING)
    private StatusRetiradaEnum statusRetirada;

    @ManyToOne(targetEntity = UsuarioEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private UsuarioEntity tecnicoEntrada;

    @OneToOne(targetEntity = OrdemEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "ordem_id")
    private OrdemEntity ordem;

}
