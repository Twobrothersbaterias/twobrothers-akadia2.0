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
@Table(name = "tb_preco_fornecedor")
public class PrecoFornecedorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataCadastro;
    private Double valor;
    private String observacao;
    private Long idUsuarioResponsavel;

    @ManyToOne(targetEntity = ProdutoEstoqueEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id")
    private ProdutoEstoqueEntity produto;

    @ManyToOne(targetEntity = FornecedorEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "fornecedor_id")
    private FornecedorEntity fornecedor;

}
