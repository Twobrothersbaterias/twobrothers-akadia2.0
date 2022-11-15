package br.com.twobrothers.frontend.services;

import br.com.twobrothers.frontend.models.dto.UsuarioDTO;
import br.com.twobrothers.frontend.models.dto.filters.FiltroUsuarioDTO;
import br.com.twobrothers.frontend.models.entities.DespesaEntity;
import br.com.twobrothers.frontend.models.entities.UsuarioEntity;
import br.com.twobrothers.frontend.repositories.UsuarioRepository;
import br.com.twobrothers.frontend.repositories.services.DespesaCrudService;
import br.com.twobrothers.frontend.repositories.services.UsuarioCrudService;
import br.com.twobrothers.frontend.repositories.services.exceptions.InvalidRequestException;
import br.com.twobrothers.frontend.utils.UsuarioUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static br.com.twobrothers.frontend.utils.StringConstants.TIPO_FILTRO;
import static br.com.twobrothers.frontend.utils.StringConstants.URI_POSTAGEM;

@Slf4j
@Service
public class UsuarioService {

    @Autowired
    UsuarioCrudService crudService;

    @Autowired
    DespesaCrudService despesaCrudService;

    @Autowired
    UsuarioRepository usuarioRepository;

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
            String mes,
            String ano,
            String username) throws InvalidRequestException {
        if (descricao != null) return crudService.buscaPorNomePaginado(pageable, descricao);
        else if (dataInicio != null && dataFim != null)
            return crudService.buscaPorRangeDeDataPaginado(pageable, dataInicio, dataFim);
        else if (mes != null && ano != null) return crudService.buscaPorPeriodoPaginado(pageable, Integer.parseInt(mes), Integer.parseInt(ano));
        else if (username != null) return crudService.buscaPorUsernamePaginado(pageable, username);
        else return crudService.buscaTodosPaginado(pageable);
    }

    public List<UsuarioEntity> filtroUsuariosSemPaginacao(
            String descricao,
            String dataInicio,
            String dataFim,
            String mes,
            String ano,
            String username) throws InvalidRequestException {
        if (descricao != null) return crudService.buscaPorNomeSemPaginacao(descricao);
        else if (dataInicio != null && dataFim != null)
            return crudService.buscaPorRangeDeDataSemPaginacao(dataInicio, dataFim);
        else if (mes != null && ano != null) return crudService.buscaPorPeriodoSemPaginacao(Integer.parseInt(mes), Integer.parseInt(ano));
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

    public ModelMap modelMapBuilder(ModelMap modelMap, Pageable pageable, HttpServletRequest req,
                                    String descricao, String inicio, String fim, String mes, String ano, String usuario) {

        log.info("[STARTING] Iniciando construção do modelMap...");
        HashMap<String, Object> atributos = new HashMap<>();

        log.info("[PROGRESS] Verificando se existem itens em atraso ou que vencem hoje...");
        List<DespesaEntity> despesasAtrasadas = despesaCrudService.buscaDespesasAtrasadas();
        List<DespesaEntity> despesasHoje = despesaCrudService.buscaDespesasComVencimentoParaHoje();

        log.info("[PROGRESS] Iniciando setagem da lista de objetos encontrados com o filtro atual...");
        List<UsuarioEntity> usuariosSemPaginacao = filtroUsuariosSemPaginacao(
                descricao,
                inicio,
                fim,
                mes,
                ano,
                usuario);

        List<UsuarioEntity> usuariosPaginados = filtroUsuarios(
                pageable,
                descricao,
                inicio,
                fim,
                mes,
                ano,
                usuario);

        log.info("[PROGRESS] Setando valores dos filtros...");
        atributos.put("despesasAtrasadas", despesasAtrasadas);
        atributos.put("despesasHoje", despesasHoje);
        atributos.put("descricao", descricao);
        atributos.put("dataInicio", inicio);
        atributos.put("dataFim", fim);
        atributos.put("mes", mes);
        atributos.put("ano", ano);
        atributos.put("usuario", usuario);
        atributos.put("usuarios", usuariosPaginados);

        log.info("[PROGRESS] Inicializando setagem de tipo de filtro...");
        atributos.put(TIPO_FILTRO, "hoje");
        if (inicio != null && fim != null) atributos.replace(TIPO_FILTRO, "data");
        if (mes != null && ano != null) atributos.replace(TIPO_FILTRO, "periodo");
        if (descricao != null) atributos.replace(TIPO_FILTRO, "descricao");
        if (usuario != null) atributos.replace(TIPO_FILTRO, "usuario");

        log.info("[PROGRESS] Setando atributos da página...");
        atributos.put("pagina", pageable.getPageNumber());
        atributos.put("paginas", calculaQuantidadePaginas(usuariosSemPaginacao, pageable));
        atributos.put("totalItens", usuariosSemPaginacao.size());

        String baseUrl = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath();
        String completeUrl = baseUrl + "/colaboradores?" + req.getQueryString();

        atributos.put("privilegio", UsuarioUtils.loggedUser(usuarioRepository).getPrivilegio().getDesc());
        atributos.put("username", UsuarioUtils.loggedUser(usuarioRepository).getNome());
        atributos.put("baseUrl", baseUrl);
        atributos.put("queryString", req.getQueryString());
        atributos.put("completeUrl", completeUrl);

        modelMap.addAllAttributes(atributos);

        log.info("[SUCCESS] ModelMap construído com sucesso. Retornando para o controller...");
        return modelMap;
    }

}
