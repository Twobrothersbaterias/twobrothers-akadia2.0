package br.com.twobrothers.msvendas.models.entities;

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
@Table(name = "tb_fornecedor")
public class FornecedorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataCadastro;
    private String nome;
    private String cpfCnpj;
    private String telefone;
    private String email;
    private Long idUsuarioResponsavel;

    @OneToOne(targetEntity = EnderecoEntity.class, cascade = CascadeType.ALL)
    private EnderecoEntity endereco;

    @OneToMany(targetEntity = PrecoFornecedorEntity.class, mappedBy = "fornecedor", cascade = CascadeType.ALL)
    private List<PrecoFornecedorEntity> precosFornecedor = new ArrayList<>();

    @OneToMany(targetEntity = AbastecimentoEntity.class, mappedBy = "fornecedor", cascade = CascadeType.ALL)
    private List<AbastecimentoEntity> abastecimentos = new ArrayList<>();

    public void addPrecoFornecedor(PrecoFornecedorEntity preco) {
        preco.setFornecedor(this);
        this.precosFornecedor.add(preco);
    }

    public void removePrecoFornecedor(PrecoFornecedorEntity preco) {
        preco.setFornecedor(null);
        this.precosFornecedor.remove(preco);
    }

    public void addAbastecimento(AbastecimentoEntity abastecimento) {
        abastecimento.setFornecedor(this);
        this.abastecimentos.add(abastecimento);
    }

    public void removeAbastecimento(AbastecimentoEntity abastecimento) {
        abastecimento.setFornecedor(null);
        this.abastecimentos.remove(abastecimento);
    }
    
}
