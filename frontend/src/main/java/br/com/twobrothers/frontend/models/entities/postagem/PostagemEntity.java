package br.com.twobrothers.frontend.models.entities.postagem;

import br.com.twobrothers.frontend.models.entities.UsuarioEntity;
import br.com.twobrothers.frontend.models.enums.FonteEnum;
import lombok.*;

import javax.persistence.*;

/**
 * @author Gabriel Lagrota
 * @email gabriellagrota23@gmail.com
 * @phone (11)97981-5415
 * @github https://github.com/LagrotaGabriel
 * @version 1.0
 * @since 30-08-22
 */
@Builder
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Table(name = "tb_postagem")
public class PostagemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String dataCadastro;

    private String titulo;

    @Column(length = 1510)
    private String conteudo;

    @Enumerated(EnumType.STRING)
    private FonteEnum fonteTitulo;

    @Enumerated(EnumType.STRING)
    private FonteEnum fonteConteudo;

    private String corTitulo;
    private String corConteudo;

    @ManyToOne(targetEntity = CategoriaEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id")
    private CategoriaEntity categoria;

    @ManyToOne(targetEntity = SubCategoriaEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_categoria_id")
    private SubCategoriaEntity subCategoria;

    @ManyToOne(targetEntity = UsuarioEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private UsuarioEntity usuarioResponsavel;

}
