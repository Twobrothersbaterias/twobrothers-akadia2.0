package br.com.twobrothers.frontend.services;

import br.com.twobrothers.frontend.config.ModelMapperConfig;
import br.com.twobrothers.frontend.models.dto.PatrimonioDTO;
import br.com.twobrothers.frontend.models.dto.filters.FiltroPatrimonioDTO;
import br.com.twobrothers.frontend.models.entities.PatrimonioEntity;
import br.com.twobrothers.frontend.models.entities.ProdutoEstoqueEntity;
import br.com.twobrothers.frontend.models.enums.StatusPatrimonioEnum;
import br.com.twobrothers.frontend.models.enums.TipoPatrimonioEnum;
import br.com.twobrothers.frontend.repositories.PatrimonioRepository;
import br.com.twobrothers.frontend.repositories.UsuarioRepository;
import br.com.twobrothers.frontend.repositories.services.PatrimonioCrudService;
import br.com.twobrothers.frontend.repositories.services.exceptions.InvalidRequestException;
import br.com.twobrothers.frontend.utils.ConversorDeDados;
import br.com.twobrothers.frontend.utils.UsuarioUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static br.com.twobrothers.frontend.utils.ConversorDeDados.converteValorDoubleParaValorMonetario;
import static br.com.twobrothers.frontend.utils.StringConstants.TIPO_FILTRO;

@Slf4j
@Service
public class PatrimonioService {

    @Autowired
    PatrimonioCrudService crudService;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    PatrimonioRepository patrimonioRepository;

    @Autowired
    ModelMapperConfig modelMapper;

    public String encaminhaParaCriacaoDoCrudService(PatrimonioDTO patrimonio) {
        try {
            crudService.criaNovo(patrimonio);
            return null;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String encaminhaParaUpdateDoCrudService(PatrimonioDTO patrimonio) {
        try {
            crudService.atualizaPorId(patrimonio);
            return null;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public List<PatrimonioEntity> filtroPatrimonios(Pageable pageable,
                                                    String descricao,
                                                    String tipo,
                                                    String mes,
                                                    String ano) throws InvalidRequestException {
        if (descricao != null) return crudService.buscaPorDescricao(pageable, descricao);
        else if (tipo != null) return crudService.buscaPorTipo(pageable, TipoPatrimonioEnum.valueOf(tipo));
        else if (mes != null && ano != null) return crudService.buscaPorPeriodo(pageable, Integer.parseInt(mes), Integer.parseInt(ano));
        else return crudService.buscaEsteMes(pageable);
    }

    public List<PatrimonioEntity> filtroPatrimoniosSemPaginacao(
            String descricao,
            String tipo,
            String mes,
            String ano) throws InvalidRequestException {
        if (descricao != null) return crudService.buscaPorDescricaoSemPaginacao(descricao);
        else if (tipo != null) return crudService.buscaPorTipoSemPaginacao(TipoPatrimonioEnum.valueOf(tipo));
        else if (mes != null && ano != null) return crudService.buscaPorPeriodoSemPaginacao(Integer.parseInt(mes), Integer.parseInt(ano));
        else return crudService.buscaEsteMesSemPaginacao();
    }

    public Double calculaValorBruto(List<PatrimonioEntity> patrimonios) {

        Double valor = 0.0;
        if (patrimonios != null && !patrimonios.isEmpty()) {
            for (PatrimonioEntity patrimonio : patrimonios) {
                if (patrimonio.getStatusPatrimonio() == StatusPatrimonioEnum.PAGO
                && patrimonio.getTipoPatrimonio() != TipoPatrimonioEnum.PASSIVO) valor += patrimonio.getValor();
            }
        }
        return valor;
    }

    public Double calculaValorPendente(List<PatrimonioEntity> patrimonios) {

        Double valor = 0.0;
        if (patrimonios != null && !patrimonios.isEmpty()) {
            for (PatrimonioEntity patrimonio : patrimonios) {
                if (patrimonio.getStatusPatrimonio() == StatusPatrimonioEnum.PENDENTE) valor += patrimonio.getValor();
            }
        }
        return valor;

    }

    public Double calculaValorPassivos(List<PatrimonioEntity> patrimonios) {
        Double valor = 0.0;
        if (patrimonios != null && !patrimonios.isEmpty()) {
            for (PatrimonioEntity patrimonio : patrimonios) {
                if (patrimonio.getTipoPatrimonio() == TipoPatrimonioEnum.PASSIVO
                && patrimonio.getStatusPatrimonio() == StatusPatrimonioEnum.PAGO) valor += patrimonio.getValor();
            }
        }
        return valor;

    }

    public Double calculaValorCaixa(List<PatrimonioEntity> patrimonios) {
        return (calculaValorBruto(patrimonios)) - calculaValorPassivos(patrimonios);
    }

    public List<Integer> calculaQuantidadePaginas(List<PatrimonioEntity> patrimonios, Pageable pageable) {
        List<Integer> paginas = new ArrayList<>();
        Integer contadorPaginas = 0;
        Integer contador = 0;
        paginas.add(contadorPaginas);
        for (int i = 0; i < patrimonios.size(); i++) {

            if (contador == pageable.getPageSize()) {
                contadorPaginas++;
                paginas.add(contadorPaginas);
                contador = 0;
            }
            contador++;

        }
        return paginas;
    }

    public String constroiUriFiltro(FiltroPatrimonioDTO filtroPatrimonioDTO) {

        String uri = "patrimonios?";

        if (filtroPatrimonioDTO.getDescricao() != null && !filtroPatrimonioDTO.getDescricao().equals("")) {
            if (uri.equals("patrimonios?")) uri += "descricao=" + filtroPatrimonioDTO.getDescricao();
            else uri += "&descricao=" + filtroPatrimonioDTO.getDescricao();
        }

        if (filtroPatrimonioDTO.getDataInicio() != null && !filtroPatrimonioDTO.getDataInicio().equals("")) {
            if (uri.equals("patrimonios?")) uri += "inicio=" + filtroPatrimonioDTO.getDataInicio();
            else uri += "&inicio=" + filtroPatrimonioDTO.getDataInicio();
        }

        if (filtroPatrimonioDTO.getDataFim() != null && !filtroPatrimonioDTO.getDataFim().equals("")) {
            if (uri.equals("patrimonios?")) uri += "fim=" + filtroPatrimonioDTO.getDataFim();
            else uri += "&fim=" + filtroPatrimonioDTO.getDataFim();
        }

        if (filtroPatrimonioDTO.getPeriodoMes() != null && !filtroPatrimonioDTO.getPeriodoMes().equals("")) {
            if (uri.equals("patrimonios?")) uri += "mes=" + filtroPatrimonioDTO.getPeriodoMes();
            else uri += "&mes=" + filtroPatrimonioDTO.getPeriodoMes();
        }

        if (filtroPatrimonioDTO.getPeriodoAno() != null && !filtroPatrimonioDTO.getPeriodoAno().equals("")) {
            if (uri.equals("patrimonios?")) uri += "ano=" + filtroPatrimonioDTO.getPeriodoAno();
            else uri += "&ano=" + filtroPatrimonioDTO.getPeriodoAno();
        }

        if (filtroPatrimonioDTO.getTipo() != null && !filtroPatrimonioDTO.getTipo().equals("")) {
            if (uri.equals("patrimonios?")) uri += "tipo=" + filtroPatrimonioDTO.getTipo();
            else uri += "&tipo=" + filtroPatrimonioDTO.getTipo();
        }

        return uri;
    }

    public void cargaDePatrimonio() {

        LocalDate dataAnterior = LocalDate.now().minusMonths(1);
        int mesAnterior = LocalDate.now().minusMonths(1).getMonthValue();
        LocalDate dataInicioMesAnterior = LocalDate.of(dataAnterior.getYear(), mesAnterior, 1);
        LocalDate dataFimMesAnterior = LocalDate.of(dataAnterior.getYear(), mesAnterior, LocalDate.now().withMonth(mesAnterior).withYear(dataAnterior.getYear()).with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth());

        List<PatrimonioDTO> patrimonios =
                patrimonioRepository.buscaPorPeriodoSemPaginacao(
                        dataInicioMesAnterior.toString(),
                        dataFimMesAnterior.toString())
                        .stream().map(x -> modelMapper.mapper().map(x, PatrimonioDTO.class)).collect(Collectors.toList());

        if (patrimonios.isEmpty())
            throw new InvalidRequestException("Não existe nenhum item no mês anterior para realizar a carga");

        List<PatrimonioEntity> patrimoniosAtualizados = new ArrayList<>();
        for (PatrimonioDTO patrimonio: patrimonios) {
            patrimonio.setId(null);
            patrimonio.setStatusPatrimonio(StatusPatrimonioEnum.PENDENTE);
            patrimonio.setDataCadastro(LocalDate.now().toString());
            patrimoniosAtualizados.add(modelMapper.mapper().map(patrimonio, PatrimonioEntity.class));
        }

        patrimonioRepository.saveAll(patrimoniosAtualizados);

    }

    public ModelMap modelMapBuilder(ModelMap modelMap, Pageable pageable, HttpServletRequest req,
                                    String descricao, String mes, String ano, String tipo) {

        log.info("[STARTING] Iniciando construção do modelMap...");
        HashMap<String, Object> atributos = new HashMap<>();

        log.info("[PROGRESS] Setando lista de itens encontrados...");
        List<PatrimonioEntity> patrimoniosSemPaginacao = filtroPatrimoniosSemPaginacao(
                descricao,
                tipo,
                mes,
                ano);

        List<PatrimonioEntity> patrimoniosPaginados = filtroPatrimonios(
                pageable,
                descricao,
                tipo,
                mes,
                ano);

        log.info("[PROGRESS] Setando valores dos informativos...");
        atributos.put("bruto", ConversorDeDados.converteValorDoubleParaValorMonetario(calculaValorBruto(patrimoniosSemPaginacao)));
        atributos.put("pendente", ConversorDeDados.converteValorDoubleParaValorMonetario(calculaValorPendente(patrimoniosSemPaginacao)));
        atributos.put("passivos", ConversorDeDados.converteValorDoubleParaValorMonetario(calculaValorPassivos(patrimoniosSemPaginacao)));
        atributos.put("caixa", ConversorDeDados.converteValorDoubleParaValorMonetario(calculaValorCaixa(patrimoniosSemPaginacao)));

        log.info("[PROGRESS] Setando valores dos filtros...");
        atributos.put("descricao", descricao);
        atributos.put("tipo", tipo);
        atributos.put("mes", mes);
        atributos.put("ano", ano);
        atributos.put("patrimonios", patrimoniosPaginados);

        log.info("[PROGRESS] Inicializando setagem de tipo de filtro...");
        atributos.put(TIPO_FILTRO, "mesAtual");
        if (mes != null && ano != null) atributos.replace(TIPO_FILTRO, "periodo");
        if (descricao != null) atributos.replace(TIPO_FILTRO, "descricao");
        if (tipo != null) atributos.replace(TIPO_FILTRO, "tipo");

        log.info("[PROGRESS] Setando atributos da página...");
        atributos.put("pagina", pageable.getPageNumber());
        atributos.put("paginas", calculaQuantidadePaginas(patrimoniosSemPaginacao, pageable));
        atributos.put("totalItens", patrimoniosSemPaginacao.size());

        String baseUrl = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath();
        String completeUrl = baseUrl + "/patrimonios?" + req.getQueryString();

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
