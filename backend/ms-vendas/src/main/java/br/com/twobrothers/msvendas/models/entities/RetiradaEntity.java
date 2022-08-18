package br.com.twobrothers.msvendas.models.entities;

import br.com.twobrothers.msvendas.models.enums.StatusRetiradaEnum;
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
@Table(name = "TB_RETIRADA")
public class RetiradaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataRetirada;
    private String observacao;
    private String dataAgendamento;
    private String tecnicoEntrada;
    private String tecnicoSaida;

    @Enumerated(EnumType.STRING)
    private StatusRetiradaEnum statusRetirada;

    //TODO Ponto de atenção
    @OneToOne(targetEntity = OrdemEntity.class, fetch = FetchType.LAZY)
    private OrdemEntity ordem;

}
