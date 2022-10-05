package br.com.twobrothers.frontend.services;

import br.com.twobrothers.frontend.config.ModelMapperConfig;
import br.com.twobrothers.frontend.models.dto.PatrimonioDTO;
import br.com.twobrothers.frontend.models.dto.filters.FiltroPatrimonioDTO;
import br.com.twobrothers.frontend.models.entities.PatrimonioEntity;
import br.com.twobrothers.frontend.models.enums.StatusPatrimonioEnum;
import br.com.twobrothers.frontend.models.enums.TipoPatrimonioEnum;
import br.com.twobrothers.frontend.repositories.PatrimonioRepository;
import br.com.twobrothers.frontend.repositories.UsuarioRepository;
import br.com.twobrothers.frontend.repositories.services.PatrimonioCrudService;
import br.com.twobrothers.frontend.repositories.services.exceptions.InvalidRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static br.com.twobrothers.frontend.utils.StringConstants.BARRA_DE_LOG;

@Slf4j
@Service
public class PatrimonioService {

    @Autowired
    PatrimonioCrudService crudService;

    @Autowired
    UsuarioRepository usuario;

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
                                                    TipoPatrimonioEnum tipo,
                                                    Integer mes,
                                                    Integer ano) throws InvalidRequestException {
        if (descricao != null) return crudService.buscaPorDescricao(pageable, descricao);
        else if (tipo != null) return crudService.buscaPorTipo(pageable, tipo);
        else if (mes != null && ano != null) return crudService.buscaPorPeriodo(pageable, mes, ano);
        else return crudService.buscaEsteMes(pageable);
    }

    public List<PatrimonioEntity> filtroPatrimoniosSemPaginacao(
            String descricao,
            TipoPatrimonioEnum tipo,
            Integer mes,
            Integer ano) throws InvalidRequestException {
        if (descricao != null) return crudService.buscaPorDescricaoSemPaginacao(descricao);
        else if (tipo != null) return crudService.buscaPorTipoSemPaginacao(tipo);
        else if (mes != null && ano != null) return crudService.buscaPorPeriodoSemPaginacao(mes, ano);
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

        List<PatrimonioEntity> patrimoniosAtualizados = new ArrayList<>();
        for (PatrimonioDTO patrimonio: patrimonios) {
            patrimonio.setId(null);
            patrimonio.setStatusPatrimonio(StatusPatrimonioEnum.PENDENTE);
            patrimonio.setDataCadastro(LocalDate.now().toString());
            patrimoniosAtualizados.add(modelMapper.mapper().map(patrimonio, PatrimonioEntity.class));
        }

        patrimonioRepository.saveAll(patrimoniosAtualizados);

    }

}
