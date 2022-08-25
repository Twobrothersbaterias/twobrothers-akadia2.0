package br.com.twobrothers.msvendas.models.entities;

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
@Table(name = "tb_troca")
public class TrocaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataCadastro;
    private Double valorTroca;
    private Integer quantidade;

    @ManyToOne(targetEntity = OrdemEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "ordem_id")
    private OrdemEntity ordem;

    //TODO Ponto de atenção
    @OneToOne(targetEntity = AbastecimentoEntity.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "troca")
    private AbastecimentoEntity abastecimento;

}
