package br.com.twobrothers.frontend.services;

import br.com.twobrothers.frontend.config.ModelMapperConfig;
import br.com.twobrothers.frontend.models.dto.filters.FiltroPostagemDTO;
import br.com.twobrothers.frontend.models.dto.postagem.PostagemDTO;
import br.com.twobrothers.frontend.models.entities.postagem.PostagemEntity;
import br.com.twobrothers.frontend.repositories.UsuarioRepository;
import br.com.twobrothers.frontend.repositories.services.PostagemCrudService;
import br.com.twobrothers.frontend.repositories.services.exceptions.InvalidRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static br.com.twobrothers.frontend.utils.StringConstants.URI_POSTAGEM;

@Slf4j
@Service
public class PostagemService {

    @Autowired
    PostagemCrudService crudService;

    @Autowired
    UsuarioRepository usuario;

    @Autowired
    ModelMapperConfig modelMapper;

    public String encaminhaParaCriacaoDoCrudService(PostagemDTO postagem) {
        try {
            crudService.criaNovo(postagem);
            return null;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String encaminhaParaUpdateDoCrudService(PostagemDTO postagem) {
        try {
            crudService.atualizaPorId(postagem);
            return null;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public List<PostagemEntity> filtroPostagens(
            Pageable pageable,
            String titulo,
            String dataInicio,
            String dataFim,
            Integer mes,
            Integer ano,
            String categoria) throws InvalidRequestException {
        if (titulo != null) return crudService.buscaPorTituloPaginado(pageable, titulo);
        else if (dataInicio != null && dataFim != null)
            return crudService.buscaPorRangeDeData(pageable, dataInicio, dataFim);
        else if (mes != null && ano != null) return crudService.buscaPorPeriodo(pageable, mes, ano);
        else if (categoria != null) return crudService.buscaPorCategoria(pageable, categoria);
        else return crudService.buscaHoje(pageable);
    }

    public List<PostagemEntity> filtroPostagensSemPaginacao(
            String titulo,
            String dataInicio,
            String dataFim,
            Integer mes,
            Integer ano,
            String categoria) throws InvalidRequestException {
        if (titulo != null) return crudService.buscaPorTituloSemPaginacao(titulo);
        else if (dataInicio != null && dataFim != null)
            return crudService.buscaPorRangeDeDataSemPaginacao(dataInicio, dataFim);
        else if (mes != null && ano != null) return crudService.buscaPorPeriodoSemPaginacao(mes, ano);
        else if (categoria != null) return crudService.buscaPorCategoriaSemPaginacao(categoria);
        else return crudService.buscaHojeSemPaginacao();
    }

    public List<Integer> calculaQuantidadePaginas(List<PostagemEntity> postagens, Pageable pageable) {
        List<Integer> paginas = new ArrayList<>();
        Integer contadorPaginas = 0;
        Integer contador = 0;
        paginas.add(contadorPaginas);
        for (int i = 0; i < postagens.size(); i++) {

            if (contador == pageable.getPageSize()) {
                contadorPaginas++;
                paginas.add(contadorPaginas);
                contador = 0;
            }
            contador++;

        }
        return paginas;
    }

    public String constroiUriFiltro(FiltroPostagemDTO filtroPostagemDTO) {

        URI_POSTAGEM = "?";

        if (filtroPostagemDTO.getTitulo() != null && !filtroPostagemDTO.getTitulo().equals("")) {
            URI_POSTAGEM += "titulo=" + filtroPostagemDTO.getTitulo();
        }

        if (filtroPostagemDTO.getDataInicio() != null && !filtroPostagemDTO.getDataInicio().equals("")) {
            URI_POSTAGEM += "inicio=" + filtroPostagemDTO.getDataInicio();
        }

        if (filtroPostagemDTO.getDataFim() != null && !filtroPostagemDTO.getDataFim().equals("")) {
            URI_POSTAGEM += "&fim=" + filtroPostagemDTO.getDataFim();
        }

        if (filtroPostagemDTO.getPeriodoMes() != null && !filtroPostagemDTO.getPeriodoMes().equals("")) {
            URI_POSTAGEM += "mes=" + filtroPostagemDTO.getPeriodoMes();
        }

        if (filtroPostagemDTO.getPeriodoAno() != null && !filtroPostagemDTO.getPeriodoAno().equals("")) {
            URI_POSTAGEM += "&ano=" + filtroPostagemDTO.getPeriodoAno();
        }

        if (filtroPostagemDTO.getCategoria() != null && !filtroPostagemDTO.getCategoria().equals("")) {
            URI_POSTAGEM += "categoria=" + filtroPostagemDTO.getCategoria();
        }

        return URI_POSTAGEM;
    }

}
