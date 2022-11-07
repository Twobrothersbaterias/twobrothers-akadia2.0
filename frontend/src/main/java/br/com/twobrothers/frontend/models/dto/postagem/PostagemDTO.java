package br.com.twobrothers.frontend.models.dto.postagem;

import br.com.twobrothers.frontend.models.entities.UsuarioEntity;
import br.com.twobrothers.frontend.models.entities.postagem.CategoriaEntity;
import br.com.twobrothers.frontend.models.entities.postagem.SubCategoriaEntity;
import br.com.twobrothers.frontend.models.enums.FonteEnum;
import lombok.*;

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
public class PostagemDTO {
    private Long id;
    private String dataCadastro;
    private String titulo;
    private String conteudo;
    private FonteEnum fonteTitulo;
    private FonteEnum fonteConteudo;
    private String corTitulo;
    private String corConteudo;
    private CategoriaEntity categoria;
    private SubCategoriaEntity subCategoria;
    private UsuarioEntity usuarioResponsavel;
}