package br.com.twobrothers.frontend.models.entities;

import br.com.twobrothers.frontend.models.enums.StatusRetiradaEnum;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    private LocalDateTime dataRetirada;
    private String observacao;
    private String dataAgendamento;
    private String tecnicoEntrada;

    @Enumerated(EnumType.STRING)
    private StatusRetiradaEnum statusRetirada;

    @OneToOne(targetEntity = OrdemEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "ordem_id")
    private OrdemEntity ordem;

}
