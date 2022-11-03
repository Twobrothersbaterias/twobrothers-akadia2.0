package br.com.twobrothers.frontend.repositories.services;

import br.com.twobrothers.frontend.models.dto.postagem.PostagemDTO;
import br.com.twobrothers.frontend.models.entities.postagem.CategoriaEntity;
import br.com.twobrothers.frontend.models.entities.postagem.PostagemEntity;
import br.com.twobrothers.frontend.models.entities.postagem.SubCategoriaEntity;
import br.com.twobrothers.frontend.repositories.CategoriaRepository;
import br.com.twobrothers.frontend.repositories.SubCategoriaRepository;
import br.com.twobrothers.frontend.repositories.UsuarioRepository;
import br.com.twobrothers.frontend.utils.UsuarioUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

@Slf4j
@Service
public class PostagemCrudService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    CategoriaRepository categoriaRepository;

    @Autowired
    SubCategoriaRepository subCategoriaRepository;

    private static final String CRIACAO_POSTAGEM = "[PROGRESS] Iniciando processo de criação do objeto postagem...";
    private static final String ACLOPANDO_POSTAGEM_SUBCATEGORIA = "[PROGRESS] Acoplando objeto postagem no objeto subcategoria...";

    public void criaNovo(PostagemDTO postagem) {

        log.warn("[STARTING] Iniciando método de criação de nova postagem");

        CategoriaEntity categoriaEntity;
        SubCategoriaEntity subCategoriaEntity;

        log.info("[PROGRESS] Verificando se a categoria passada na requisição existe na base de dados: {}", postagem.getCategoria().getNome());
        Optional<CategoriaEntity> categoriaOptional = categoriaRepository.findByNome(postagem.getCategoria().getNome());

        // CATEGORIA JÁ EXISTENTE
        if (categoriaOptional.isPresent()) {

            log.warn("[INFO] A categoria informada foi encontrada");
            categoriaEntity = categoriaOptional.get();

            log.info("[PROGRESS] Verificando se a subcategoria passada na requisição existe na base de dados: {}", postagem.getSubCategoria().getNome());
            Optional<SubCategoriaEntity> subCategoriaOptional =
                    subCategoriaRepository.buscaPorNomeNaCategoriaInformada(postagem.getSubCategoria().getNome(), categoriaEntity.getNome());

            // SUBCATEGORIA JÁ EXISTENTE
            if (subCategoriaOptional.isPresent()) {
                log.warn("[INFO] A subcategoria informada foi encontrada");
                subCategoriaEntity = subCategoriaOptional.get();

                log.info(CRIACAO_POSTAGEM);
                PostagemEntity postagemEntity =
                        PostagemEntity.builder()
                                .corConteudo(postagem.getCorConteudo())
                                .corTitulo(postagem.getCorTitulo())
                                .fonteConteudo(postagem.getFonteConteudo())
                                .fonteTitulo(postagem.getFonteTitulo())
                                .subCategoria(null)
                                .conteudo(postagem.getConteudo())
                                .titulo(postagem.getTitulo())
                                .categoria(null)
                                .usuarioResponsavel(UsuarioUtils.loggedUser(usuarioRepository))
                                .dataCadastro(LocalDate.now().toString())
                                .build();

                log.info(ACLOPANDO_POSTAGEM_SUBCATEGORIA);
                subCategoriaEntity.addPostagem(postagemEntity);

                log.info("[PROGRESS] Acoplando objetos postagem no objeto categoria...");
                categoriaEntity.addPostagem(postagemEntity);

            }

            // SUBCATEGORIA NÃO EXISTENTE
            else {
                log.warn("[INFO] A subcategoria informada não foi encontrada");

                log.info("[PROGRESS] Iniciando processo de criação do objeto subcategoria...");
                SubCategoriaEntity subCategoria =
                        SubCategoriaEntity.builder()
                                .nome(postagem.getSubCategoria().getNome())
                                .dataCadastro(LocalDate.now().toString())
                                .categoria(null)
                                .postagens(new ArrayList<>())
                                .usuarioResponsavel(UsuarioUtils.loggedUser(usuarioRepository))
                                .build();

                log.info(CRIACAO_POSTAGEM);
                PostagemEntity postagemEntity =
                        PostagemEntity.builder()
                                .corConteudo(postagem.getCorConteudo())
                                .corTitulo(postagem.getCorTitulo())
                                .fonteConteudo(postagem.getFonteConteudo())
                                .fonteTitulo(postagem.getFonteTitulo())
                                .subCategoria(null)
                                .conteudo(postagem.getConteudo())
                                .titulo(postagem.getTitulo())
                                .categoria(null)
                                .usuarioResponsavel(UsuarioUtils.loggedUser(usuarioRepository))
                                .dataCadastro(LocalDate.now().toString())
                                .build();

                log.info(ACLOPANDO_POSTAGEM_SUBCATEGORIA);
                subCategoria.addPostagem(postagemEntity);

                log.info("[PROGRESS] Acoplando objetos postagem e subcategoria no objeto categoria...");
                categoriaEntity.addSubCategoria(subCategoria);
                categoriaEntity.addPostagem(postagemEntity);

            }

            log.info("[PROGRESS] Persistindo categoria com objetos acoplados...");
            categoriaRepository.save(categoriaEntity);

        }

        // CATEGORIA NÃO EXISTENTE
        else {
            log.warn("[INFO] A categoria informada não foi encontrada. Iniciando processo de criação...");

            log.info("[PROGRESS] Iniciando processo de criação do objeto categoria...");
            CategoriaEntity categoria =
                    CategoriaEntity.builder()
                            .nome(postagem.getCategoria().getNome())
                            .dataCadastro(LocalDate.now().toString())
                            .subCategorias(new ArrayList<>())
                            .postagens(new ArrayList<>())
                            .usuarioResponsavel(UsuarioUtils.loggedUser(usuarioRepository))
                            .build();

            log.info("[PROGRESS] Iniciando processo de criação do objeto subcategoria...");
            SubCategoriaEntity subCategoria =
                    SubCategoriaEntity.builder()
                            .nome(postagem.getSubCategoria().getNome())
                            .dataCadastro(LocalDate.now().toString())
                            .categoria(null)
                            .postagens(new ArrayList<>())
                            .usuarioResponsavel(UsuarioUtils.loggedUser(usuarioRepository))
                            .build();

            log.info(CRIACAO_POSTAGEM);
            PostagemEntity postagemEntity =
                    PostagemEntity.builder()
                            .corConteudo(postagem.getCorConteudo())
                            .corTitulo(postagem.getCorTitulo())
                            .fonteConteudo(postagem.getFonteConteudo())
                            .fonteTitulo(postagem.getFonteTitulo())
                            .subCategoria(null)
                            .conteudo(postagem.getConteudo())
                            .titulo(postagem.getTitulo())
                            .categoria(null)
                            .usuarioResponsavel(UsuarioUtils.loggedUser(usuarioRepository))
                            .dataCadastro(LocalDate.now().toString())
                            .build();

            log.info(ACLOPANDO_POSTAGEM_SUBCATEGORIA);
            subCategoria.addPostagem(postagemEntity);

            log.info("[PROGRESS] Acoplando objetos postagem e subcategoria no objeto categoria...");
            categoria.addSubCategoria(subCategoria);
            categoria.addPostagem(postagemEntity);

            log.info("[PROGRESS] Persistindo categoria com objetos acoplados...");
            categoriaRepository.save(categoria);
        }

    }

    public void atualizaPorId(PostagemDTO postagem) {

    }
}
