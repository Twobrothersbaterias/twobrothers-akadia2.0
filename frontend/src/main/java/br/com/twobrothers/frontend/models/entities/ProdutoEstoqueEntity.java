package br.com.twobrothers.frontend.models.entities;

import br.com.twobrothers.frontend.models.enums.TipoProdutoEnum;
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
@Table(name = "tb_produto")
public class ProdutoEstoqueEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String dataCadastro;
    private String sigla;
    private String marcaBateria;
    private String especificacao;
    private Integer quantidadeMinima;
    private String usuarioResponsavel;
    private Integer quantidade;

    @Enumerated(EnumType.STRING)
    private TipoProdutoEnum tipoProduto;

    @OneToMany(targetEntity = AbastecimentoEntity.class, fetch = FetchType.LAZY, mappedBy = "produto", cascade = CascadeType.ALL)
    private List<AbastecimentoEntity> abastecimentos = new ArrayList<>();

    @OneToMany(targetEntity = PrecoFornecedorEntity.class, fetch = FetchType.LAZY, mappedBy = "produto", cascade = CascadeType.ALL)
    private List<PrecoFornecedorEntity> precosFornecedor = new ArrayList<>();

    @OneToMany(targetEntity = EntradaOrdemEntity.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<EntradaOrdemEntity> entradas = new ArrayList<>();

    public void addPrecoFornecedor(PrecoFornecedorEntity preco) {
        preco.setProduto(this);
        this.precosFornecedor.add(preco);
    }

    public void removePrecoFornecedor(PrecoFornecedorEntity preco) {
        preco.setProduto(null);
        this.precosFornecedor.remove(preco);
    }

    public void addAbastecimento(AbastecimentoEntity abastecimento) {
        abastecimento.setProduto(this);
        this.abastecimentos.add(abastecimento);
    }

    public void removeAbastecimentos(AbastecimentoEntity abastecimento) {
        abastecimento.setProduto(null);
        this.abastecimentos.remove(abastecimento);
    }

    public void addEntrada(EntradaOrdemEntity entrada) {
        entrada.setProduto(this);
        this.entradas.add(entrada);
    }
    public void removeEntrada(EntradaOrdemEntity entrada) {
        entrada.setProduto(null);
        this.entradas.remove(entrada);
    }

}
