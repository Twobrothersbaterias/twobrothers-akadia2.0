package br.com.twobrothers.frontend.models.entities;

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
@Table(name = "tb_preco_fornecedor")
public class PrecoFornecedorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String dataCadastro;
    private Double valor;
    private String observacao;
    private String usuarioResponsavel;

    @ManyToOne(targetEntity = ProdutoEstoqueEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id")
    private ProdutoEstoqueEntity produto;

    @ManyToOne(targetEntity = FornecedorEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "fornecedor_id")
    private FornecedorEntity fornecedor;

}
