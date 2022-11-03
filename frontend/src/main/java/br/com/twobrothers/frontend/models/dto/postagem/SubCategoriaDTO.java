package br.com.twobrothers.frontend.models.dto.postagem;

import br.com.twobrothers.frontend.models.entities.UsuarioEntity;
import br.com.twobrothers.frontend.models.entities.postagem.CategoriaEntity;
import br.com.twobrothers.frontend.models.entities.postagem.PostagemEntity;
import lombok.*;

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
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class SubCategoriaDTO {
    private Long id;
    private String dataCadastro;
    private String nome;
    private UsuarioEntity usuarioResponsavel;
    private CategoriaEntity categoria;
    private List<PostagemEntity> postagens = new ArrayList<>();
}
