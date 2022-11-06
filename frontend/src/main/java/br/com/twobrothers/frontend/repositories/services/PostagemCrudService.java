package br.com.twobrothers.frontend.repositories.services;

import br.com.twobrothers.frontend.models.dto.postagem.PostagemDTO;
import br.com.twobrothers.frontend.models.entities.postagem.CategoriaEntity;
import br.com.twobrothers.frontend.models.entities.postagem.PostagemEntity;
import br.com.twobrothers.frontend.models.entities.postagem.SubCategoriaEntity;
import br.com.twobrothers.frontend.repositories.CategoriaRepository;
import br.com.twobrothers.frontend.repositories.PostagemRepository;
import br.com.twobrothers.frontend.repositories.SubCategoriaRepository;
import br.com.twobrothers.frontend.repositories.UsuarioRepository;
import br.com.twobrothers.frontend.utils.UsuarioUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static br.com.twobrothers.frontend.utils.StringConstants.BARRA_DE_LOG;

@Slf4j
@Service
public class PostagemCrudService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    CategoriaRepository categoriaRepository;

    @Autowired
    SubCategoriaRepository subCategoriaRepository;

    @Autowired
    PostagemRepository postagemRepository;

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
                                .anexo(postagem.getAnexo())
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
                                .anexo(postagem.getAnexo())
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
                            .anexo(postagem.getAnexo())
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

    public List<PostagemEntity> buscaPorRangeDeData(Pageable pageable, String dataInicio, String dataFim) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de postagem por range de data...");
        return postagemRepository.buscaPorRangeDeDataCadastroPaginado(pageable, dataInicio, dataFim);
    }

    public List<PostagemEntity> buscaPorPeriodo(Pageable pageable, Integer mes, Integer ano) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de postagem por período...");
        LocalDate dataInicio = LocalDate.of(ano, mes, 1);
        LocalDate dataFim = LocalDate.of(ano, mes, LocalDate.now().withMonth(mes).withYear(ano).with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth());
        return postagemRepository.buscaPorPeriodoPaginado(pageable, dataInicio.toString(), dataFim.toString());
    }

    public List<PostagemEntity> buscaHoje(Pageable pageable) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de todos os postagems cadastrados hoje...");
        LocalDate hoje = LocalDate.now();
        return postagemRepository.buscaHojePaginado(pageable, hoje.toString());
    }

    public List<PostagemEntity> buscaPorTituloPaginado(Pageable pageable, String titulo) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de postagem por titulo...");
        return postagemRepository.buscaPorTituloPaginado(pageable, titulo);
    }

    public List<PostagemEntity> buscaPorCategoria(Pageable pageable, String categoria) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de postagem por categoria...");
        return postagemRepository.buscaPorCategoriaPaginado(pageable, categoria);
    }

    public List<PostagemEntity> buscaPorRangeDeDataSemPaginacao(String dataInicio, String dataFim) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de postagem por range de data...");
        List<PostagemEntity> postagens = postagemRepository.buscaPorRangeDeDataCadastroSemPaginacao(dataInicio, dataFim);
        System.err.println(postagens.size());
        return postagemRepository.buscaPorRangeDeDataCadastroSemPaginacao(dataInicio, dataFim);
    }

    public List<PostagemEntity> buscaPorPeriodoSemPaginacao(Integer mes, Integer ano) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de postagem por período...");
        LocalDate dataInicio = LocalDate.of(ano, mes, 1);
        LocalDate dataFim = LocalDate.of(ano, mes, LocalDate.now().withMonth(mes).withYear(ano).with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth());
        return postagemRepository.buscaPorPeriodoSemPaginacao(dataInicio.toString(), dataFim.toString());
    }

    public List<PostagemEntity> buscaHojeSemPaginacao() {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de todos os postagems cadastrados hoje...");
        LocalDate hoje = LocalDate.now();
        return postagemRepository.buscaHojeSemPaginacao(hoje.toString());
    }

    public List<PostagemEntity> buscaPorTituloSemPaginacao(String titulo) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de postagem por titulo...");
        return postagemRepository.buscaPorTituloSemPaginacao(titulo);
    }

    public List<PostagemEntity> buscaPorCategoriaSemPaginacao(String categoria) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de postagem por categoria...");
        return postagemRepository.buscaPorCategoriaSemPaginacao(categoria);
    }

    public void deletaPorId(Long id) {
        if (postagemRepository.findById(id).isPresent()) {

            PostagemEntity postagem = postagemRepository.findById(id).get();
            CategoriaEntity categoria = postagem.getCategoria();
            SubCategoriaEntity subCategoria = postagem.getSubCategoria();
            categoria.removePostagem(postagem);
            subCategoria.removePostagem(postagem);

            if (subCategoria.getPostagens().isEmpty()) {
                categoria.removeSubCategoria(subCategoria);
                categoriaRepository.save(categoria);
                subCategoriaRepository.deleteById(subCategoria.getId());
            }
            else {
                subCategoriaRepository.save(subCategoria);
            }

            categoriaRepository.save(categoria);
            postagemRepository.deleteById(id);
        }
    }

    public void atualizaPorId(PostagemDTO postagem) {

    }
}
