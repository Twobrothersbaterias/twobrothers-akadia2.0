package br.com.twobrothers.frontend.models.entities;

import br.com.twobrothers.frontend.models.enums.LojaEnum;
import br.com.twobrothers.frontend.models.enums.NfeEnum;
import lombok.*;

import javax.persistence.*;
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

    private String dataCadastro;
    private String veiculo;

    @Enumerated(EnumType.STRING)
    private NfeEnum tipoNfe;

    private String usuarioResponsavel;

    @Enumerated(EnumType.STRING)
    private LojaEnum loja;

    @ManyToOne(targetEntity = ClienteEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private ClienteEntity cliente;

    @OneToOne(targetEntity = RetiradaEntity.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private RetiradaEntity retirada;

    @OneToMany(targetEntity = PagamentoEntity.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PagamentoEntity> pagamentos = new ArrayList<>();

    @Column(length = 510)
    private String pagamentosString;

    @OneToMany(targetEntity = EntradaOrdemEntity.class, fetch = FetchType.LAZY, mappedBy = "ordem", cascade = CascadeType.ALL)
    private List<EntradaOrdemEntity> entradas = new ArrayList<>();

    @Column(length = 510)
    private String entradasString;

    public void addEntrada(EntradaOrdemEntity entrada) {
        entrada.setOrdem(this);
        this.entradas.add(entrada);
    }

    public void removeEntrada(EntradaOrdemEntity entrada) {
        entrada.setOrdem(null);
        this.entradas.remove(entrada);
    }

}