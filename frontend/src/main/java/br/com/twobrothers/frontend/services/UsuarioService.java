package br.com.twobrothers.frontend.services;

import br.com.twobrothers.frontend.config.ModelMapperConfig;
import br.com.twobrothers.frontend.models.dto.UsuarioDTO;
import br.com.twobrothers.frontend.models.dto.filters.FiltroUsuarioDTO;
import br.com.twobrothers.frontend.models.entities.UsuarioEntity;
import br.com.twobrothers.frontend.repositories.UsuarioRepository;
import br.com.twobrothers.frontend.repositories.services.UsuarioCrudService;
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
public class UsuarioService {

    @Autowired
    UsuarioCrudService crudService;

    @Autowired
    UsuarioRepository repository;

    @Autowired
    ModelMapperConfig modelMapper;

    public String encaminhaParaCriacaoDoCrudService(UsuarioDTO usuario) {
        try {
            crudService.criaNovo(usuario);
            return null;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String encaminhaParaUpdateDoCrudService(UsuarioDTO usuario) {
        try {
            crudService.atualizaPorId(usuario);
            return null;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public List<UsuarioEntity> filtroUsuarios(
            Pageable pageable,
            String descricao,
            String dataInicio,
            String dataFim,
            Integer mes,
            Integer ano,
            String username) throws InvalidRequestException {
        if (descricao != null) return crudService.buscaPorNomePaginado(pageable, descricao);
        else if (dataInicio != null && dataFim != null)
            return crudService.buscaPorRangeDeDataPaginado(pageable, dataInicio, dataFim);
        else if (mes != null && ano != null) return crudService.buscaPorPeriodoPaginado(pageable, mes, ano);
        else if (username != null) return crudService.buscaPorUsernamePaginado(pageable, username);
        else return crudService.buscaTodosPaginado(pageable);
    }

    public List<UsuarioEntity> filtroUsuariosSemPaginacao(
            String descricao,
            String dataInicio,
            String dataFim,
            Integer mes,
            Integer ano,
            String username) throws InvalidRequestException {
        if (descricao != null) return crudService.buscaPorNomeSemPaginacao(descricao);
        else if (dataInicio != null && dataFim != null)
            return crudService.buscaPorRangeDeDataSemPaginacao(dataInicio, dataFim);
        else if (mes != null && ano != null) return crudService.buscaPorPeriodoSemPaginacao(mes, ano);
        else if (username != null) return crudService.buscaPorUsernameSemPaginacao(username);
        else return crudService.buscaTodosSemPaginacao();
    }

    public List<Integer> calculaQuantidadePaginas(List<UsuarioEntity> usuarios, Pageable pageable) {
        List<Integer> paginas = new ArrayList<>();
        Integer contadorPaginas = 0;
        int contador = 0;
        paginas.add(contadorPaginas);
        for (int i = 0; i < usuarios.size(); i++) {

            if (contador == pageable.getPageSize()) {
                contadorPaginas++;
                paginas.add(contadorPaginas);
                contador = 0;
            }
            contador++;

        }
        return paginas;
    }

    public String constroiUriFiltro(FiltroUsuarioDTO filtroUsuarioDTO) {

        URI_POSTAGEM = "colaboradores?";

        if (filtroUsuarioDTO.getDescricao() != null && !filtroUsuarioDTO.getDescricao().equals("")) {
            URI_POSTAGEM += "descricao=" + filtroUsuarioDTO.getDescricao();
        }

        if (filtroUsuarioDTO.getDataInicio() != null && !filtroUsuarioDTO.getDataInicio().equals("")) {
            URI_POSTAGEM += "inicio=" + filtroUsuarioDTO.getDataInicio();
        }

        if (filtroUsuarioDTO.getDataFim() != null && !filtroUsuarioDTO.getDataFim().equals("")) {
            URI_POSTAGEM += "&fim=" + filtroUsuarioDTO.getDataFim();
        }

        if (filtroUsuarioDTO.getPeriodoMes() != null && !filtroUsuarioDTO.getPeriodoMes().equals("")) {
            URI_POSTAGEM += "mes=" + filtroUsuarioDTO.getPeriodoMes();
        }

        if (filtroUsuarioDTO.getPeriodoAno() != null && !filtroUsuarioDTO.getPeriodoAno().equals("")) {
            URI_POSTAGEM += "&ano=" + filtroUsuarioDTO.getPeriodoAno();
        }

        if (filtroUsuarioDTO.getUsername() != null && !filtroUsuarioDTO.getUsername().equals("")) {
            URI_POSTAGEM += "usuario=" + filtroUsuarioDTO.getUsername();
        }

        return URI_POSTAGEM;
    }

}
