package br.com.twobrothers.frontend.services;

import br.com.twobrothers.frontend.models.dto.ClienteDTO;
import br.com.twobrothers.frontend.models.dto.filters.FiltroClienteDTO;
import br.com.twobrothers.frontend.models.entities.ClienteEntity;
import br.com.twobrothers.frontend.repositories.UsuarioRepository;
import br.com.twobrothers.frontend.repositories.services.ClienteCrudService;
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
public class ClienteService {

    @Autowired
    ClienteCrudService crudService;

    @Autowired
    UsuarioRepository usuarioRepository;

    public String encaminhaParaCriacaoDoCrudService(ClienteDTO cliente) {
        try {
            crudService.criaNovo(cliente);
            return null;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String encaminhaParaUpdateDoCrudService(ClienteDTO cliente) {
        try {
            crudService.atualizaPorId(cliente);
            return null;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public List<ClienteEntity> filtroClientes(
            Pageable pageable,
            String descricao,
            String dataInicio,
            String dataFim,
            String mes,
            String ano,
            String cpfCnpj,
            String telefone) throws InvalidRequestException {
        if (descricao != null) return crudService.buscaPorNomeCompleto(pageable, descricao);
        else if (dataInicio != null && dataFim != null)
            return crudService.buscaPorRangeDeData(pageable, dataInicio, dataFim);
        else if (mes != null && ano != null) return crudService.buscaPorPeriodo(pageable, Integer.parseInt(mes), Integer.parseInt(ano));
        else if (cpfCnpj != null) return crudService.buscaPorCpfCnpj(pageable, cpfCnpj);
        else if (telefone != null) return crudService.buscaPorTelefone(pageable, telefone);
        else return crudService.buscaHoje(pageable);
    }

    public List<ClienteEntity> filtroClientesSemPaginacao(
            String descricao,
            String dataInicio,
            String dataFim,
            String mes,
            String ano,
            String cpfCnpj,
            String telefone) throws InvalidRequestException {
        if (descricao != null) return crudService.buscaPorNomeCompletoSemPaginacao(descricao);
        else if (dataInicio != null && dataFim != null)
            return crudService.buscaPorRangeDeDataSemPaginacao(dataInicio, dataFim);
        else if (mes != null && ano != null) return crudService.buscaPorPeriodoSemPaginacao(Integer.parseInt(mes), Integer.parseInt(ano));
        else if (cpfCnpj != null) return crudService.buscaPorCpfCnpjSemPaginacao(cpfCnpj);
        else if (telefone != null) return crudService.buscaPorTelefoneSemPaginacao(telefone);
        else return crudService.buscaHojeSemPaginacao();
    }

    public List<Integer> calculaQuantidadePaginas(List<ClienteEntity> clientes, Pageable pageable) {
        List<Integer> paginas = new ArrayList<>();
        Integer contadorPaginas = 0;
        int contador = 0;
        paginas.add(contadorPaginas);
        for (int i = 0; i < clientes.size(); i++) {

            if (contador == pageable.getPageSize()) {
                contadorPaginas++;
                paginas.add(contadorPaginas);
                contador = 0;
            }
            contador++;

        }
        return paginas;
    }

    public String constroiUriFiltro(FiltroClienteDTO filtroClienteDTO) {

        URI_POSTAGEM = "clientes?";

        if (filtroClienteDTO.getDescricao() != null && !filtroClienteDTO.getDescricao().equals("")) {
            URI_POSTAGEM += "descricao=" + filtroClienteDTO.getDescricao();
        }

        if (filtroClienteDTO.getDataInicio() != null && !filtroClienteDTO.getDataInicio().equals("")) {
            URI_POSTAGEM += "inicio=" + filtroClienteDTO.getDataInicio();
        }

        if (filtroClienteDTO.getDataFim() != null && !filtroClienteDTO.getDataFim().equals("")) {
            URI_POSTAGEM += "&fim=" + filtroClienteDTO.getDataFim();
        }

        if (filtroClienteDTO.getPeriodoMes() != null && !filtroClienteDTO.getPeriodoMes().equals("")) {
            URI_POSTAGEM += "mes=" + filtroClienteDTO.getPeriodoMes();
        }

        if (filtroClienteDTO.getPeriodoAno() != null && !filtroClienteDTO.getPeriodoAno().equals("")) {
            URI_POSTAGEM += "&ano=" + filtroClienteDTO.getPeriodoAno();
        }

        if (filtroClienteDTO.getCpfCnpj() != null && !filtroClienteDTO.getCpfCnpj().equals("")) {
            URI_POSTAGEM += "cpfCnpj=" + filtroClienteDTO.getCpfCnpj();
        }

        if (filtroClienteDTO.getTelefone() != null && !filtroClienteDTO.getTelefone().equals("")) {
            URI_POSTAGEM += "telefone=" + filtroClienteDTO.getTelefone();
        }

        return URI_POSTAGEM;
    }

    public ModelMap modelMapperBuilder(ModelMap modelMap, Pageable pageable, HttpServletRequest req,
                                       String descricao, String inicio, String fim,
                                       String mes, String ano, String cpfCnpj, String telefone) {

        log.info("[STARTING] Iniciando método de construção de atributos passados por PathParam...");
        HashMap<String, Object> atributos = new HashMap<>();

        log.info("[PROGRESS] Iniciando setagem da lista de itens encontrados...");

        List<ClienteEntity> clientesSemPaginacao = filtroClientesSemPaginacao(
                descricao,
                inicio,
                fim,
                mes,
                ano,
                cpfCnpj,
                telefone);

        List<ClienteEntity> clientesPaginados = filtroClientes(
                pageable,
                descricao,
                inicio,
                fim,
                mes,
                ano,
                cpfCnpj,
                telefone);

        log.info("[PROGRESS] Inicializando setagem de atributos de busca...");
        atributos.put("privilegio", UsuarioUtils.loggedUser(usuarioRepository).getPrivilegio().getDesc());
        atributos.put("descricao", descricao);
        atributos.put("inicio", inicio);
        atributos.put("fim", fim);
        atributos.put("dataInicio", inicio);
        atributos.put("dataFim", fim);
        atributos.put("mes", mes);
        atributos.put("ano", ano);
        atributos.put("cpfCnpj", cpfCnpj);
        atributos.put("telefone", telefone);
        atributos.put("clientes", clientesPaginados);

        log.info("[PROGRESS] Inicializando setagem de tipo de filtro...");
        atributos.put(TIPO_FILTRO, "hoje");
        if (inicio != null && fim != null) atributos.replace(TIPO_FILTRO, "data");
        if (mes != null && ano != null) atributos.replace(TIPO_FILTRO, "periodo");
        if (descricao != null) atributos.replace(TIPO_FILTRO, "descricao");
        if (cpfCnpj != null) atributos.replace(TIPO_FILTRO, "cpfCnpj");
        if (telefone != null) atributos.replace(TIPO_FILTRO, "telefone");

        log.info("[PROGRESS] Inicializando setagem de atributos da página...");
        atributos.put("pagina", pageable.getPageNumber());
        atributos.put("paginas", calculaQuantidadePaginas(clientesSemPaginacao, pageable));
        atributos.put("totalItens", clientesSemPaginacao.size());

        String baseUrl = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath();
        String completeUrl = baseUrl + "/clientes?" + req.getQueryString();
        modelMap.addAttribute("username", UsuarioUtils.loggedUser(usuarioRepository).getNome());
        modelMap.addAttribute("baseUrl", baseUrl);
        modelMap.addAttribute("queryString", req.getQueryString());
        modelMap.addAttribute("completeUrl", completeUrl);

        log.info("[SUCCESS] ModelMap construído com sucesso. Retornando para o controller...");
        modelMap.addAllAttributes(atributos);
        return modelMap;
    }

}
