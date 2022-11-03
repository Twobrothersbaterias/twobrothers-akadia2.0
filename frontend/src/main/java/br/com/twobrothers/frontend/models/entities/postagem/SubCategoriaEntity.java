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
@Table(name = "tb_subcategoria")
public class SubCategoriaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String dataCadastro;

    private String nome;

    @ManyToOne(targetEntity = UsuarioEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private UsuarioEntity usuarioResponsavel;

    @ManyToOne(targetEntity = CategoriaEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id")
    private CategoriaEntity categoria;

    @OneToMany(targetEntity = PostagemEntity.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PostagemEntity> postagens = new ArrayList<>();

    public void addPostagem(PostagemEntity postagem) {
        postagem.setSubCategoria(this);
        this.postagens.add(postagem);
    }

    public void removePostagem(PostagemEntity postagem) {
        postagem.setSubCategoria(null);
        this.postagens.remove(postagem);
    }

}
