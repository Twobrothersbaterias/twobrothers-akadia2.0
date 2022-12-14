package br.com.twobrothers.msdespesas.models.entities;

import br.com.twobrothers.msdespesas.models.enums.StatusDespesaEnum;
import br.com.twobrothers.msdespesas.models.enums.TipoDespesaEnum;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString @EqualsAndHashCode
@Table(name = "TB_DESPESA")
public class DespesaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataCadastro;
    private String dataPagamento;
    private String dataAgendamento;
    private String descricao;
    private Double valor;
    private StatusDespesaEnum statusDespesa;
    private TipoDespesaEnum tipoDespesa;
    //TODO private Usuario usuarioResponsavel;

}
