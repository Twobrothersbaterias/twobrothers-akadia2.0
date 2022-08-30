package br.com.twobrothers.msvendas.models.entities;

import br.com.twobrothers.msvendas.models.enums.LojaEnum;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
@Table(name = "tb_ordem")
@SequenceGenerator(allocationSize = 1, sequenceName = "sq_ordem", name = "ordem")
public class OrdemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "sq_ordem")
    private Long id;

    private LocalDateTime dataCadastro;
    private String veiculo;
    private Boolean emiteNfe;
    private Long idUsuarioResponsavel;

    @Enumerated(EnumType.STRING)
    private LojaEnum loja;

    @ManyToOne(targetEntity = ClienteEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private ClienteEntity cliente;

    @OneToOne(targetEntity = RetiradaEntity.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private RetiradaEntity retirada;

    @OneToMany(targetEntity = PagamentoEntity.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PagamentoEntity> pagamentos = new ArrayList<>();

    @OneToMany(targetEntity = EntradaOrdemEntity.class, fetch = FetchType.LAZY, mappedBy = "ordem", cascade = CascadeType.ALL)
    private List<EntradaOrdemEntity> entradas = new ArrayList<>();

    public void addEntrada(EntradaOrdemEntity entrada) {
        entrada.setOrdem(this);
        this.entradas.add(entrada);
    }

}