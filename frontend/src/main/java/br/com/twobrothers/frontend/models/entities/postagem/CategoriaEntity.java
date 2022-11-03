package br.com.twobrothers.frontend.models.entities.postagem;

import br.com.twobrothers.frontend.models.entities.UsuarioEntity;
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

@Builder
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Table(name = "tb_categoria")
public class CategoriaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String dataCadastro;

    @Column(unique = true)
    private String nome;

    @ManyToOne(targetEntity = UsuarioEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private UsuarioEntity usuarioResponsavel;

    @OneToMany(targetEntity = PostagemEntity.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PostagemEntity> postagens = new ArrayList<>();

    @OneToMany(targetEntity = SubCategoriaEntity.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<SubCategoriaEntity> subCategorias = new ArrayList<>();

    public void addPostagem(PostagemEntity postagem) {
        postagem.setCategoria(this);
        this.postagens.add(postagem);
    }

    public void removePostagem(PostagemEntity postagem) {
        postagem.setCategoria(null);
        this.postagens.remove(postagem);
    }

    public void addSubCategoria(SubCategoriaEntity subCategoria) {
        subCategoria.setCategoria(this);
        this.subCategorias.add(subCategoria);
    }

    public void removeSubCategoria(SubCategoriaEntity subCategoria) {
        subCategoria.setCategoria(null);
        this.subCategorias.remove(subCategoria);
    }

}
