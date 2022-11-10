package br.com.twobrothers.frontend.services;

import br.com.twobrothers.frontend.config.ModelMapperConfig;
import br.com.twobrothers.frontend.models.dto.FornecedorDTO;
import br.com.twobrothers.frontend.models.dto.filters.FiltroFornecedorDTO;
import br.com.twobrothers.frontend.models.entities.FornecedorEntity;
import br.com.twobrothers.frontend.repositories.FornecedorRepository;
import br.com.twobrothers.frontend.repositories.UsuarioRepository;
import br.com.twobrothers.frontend.repositories.services.FornecedorCrudService;
import br.com.twobrothers.frontend.repositories.services.exceptions.InvalidRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static br.com.twobrothers.frontend.utils.StringConstants.URI_ORDENS;

@Slf4j
@Service
public class FornecedorService {

    @Autowired
    FornecedorCrudService crudService;

    @Autowired
    UsuarioRepository usuario;

    @Autowired
    FornecedorRepository fornecedorRepository;

    @Autowired
    ModelMapperConfig modelMapper;

    public String encaminhaParaCriacaoDoCrudService(FornecedorDTO fornecedor) {
        try {
            crudService.criaNovo(fornecedor);
            return null;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String encaminhaParaUpdateDoCrudService(FornecedorDTO fornecedor) {
        try {
            crudService.atualizaPorId(fornecedor);
            return null;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public List<FornecedorEntity> filtroFornecedores(
            Pageable pageable,
            String descricao,
            String dataInicio,
            String dataFim,
            String mes,
            String ano,
            String cpfCnpj,
            String telefone) throws InvalidRequestException {
        if (descricao != null) return crudService.buscaPorNomeCompleto(pageable, descricao);
        else if (dataInicio != null && dataFim != null) return crudService.buscaPorRangeDeData(pageable, dataInicio, dataFim);
        else if (mes != null && ano != null) return crudService.buscaPorPeriodo(pageable, Integer.parseInt(mes), Integer.parseInt(ano));
        else if (cpfCnpj != null) return crudService.buscaPorCpfCnpj(pageable, cpfCnpj);
        else if (telefone != null) return crudService.buscaPorTelefone(pageable, telefone);
        else return fornecedorRepository.findAll(pageable).toList();
    }

    public List<FornecedorEntity> filtroFornecedoresSemPaginacao(
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
        else return fornecedorRepository.findAll();
    }

    public List<Integer> calculaQuantidadePaginas(List<FornecedorEntity> fornecedores, Pageable pageable) {
        List<Integer> paginas = new ArrayList<>();
        Integer contadorPaginas = 0;
        int contador = 0;
        paginas.add(contadorPaginas);
        for (int i = 0; i < fornecedores.size(); i++) {

            if (contador == pageable.getPageSize()) {
                contadorPaginas++;
                paginas.add(contadorPaginas);
                contador = 0;
            }
            contador++;

        }
        return paginas;
    }

    public List<FornecedorEntity> buscaTodos() {
        return fornecedorRepository.findAll(Sort.by("nome"));
    }

    public String constroiUriFiltro(FiltroFornecedorDTO filtroFornecedorDTO) {

        URI_ORDENS = "fornecedores?";

        if (filtroFornecedorDTO.getDescricao() != null && !filtroFornecedorDTO.getDescricao().equals("")) {
            URI_ORDENS += "descricao=" + filtroFornecedorDTO.getDescricao();
        }

        if (filtroFornecedorDTO.getDataInicio() != null && !filtroFornecedorDTO.getDataInicio().equals("")) {
            URI_ORDENS += "inicio=" + filtroFornecedorDTO.getDataInicio();
        }

        if (filtroFornecedorDTO.getDataFim() != null && !filtroFornecedorDTO.getDataFim().equals("")) {
            URI_ORDENS += "&fim=" + filtroFornecedorDTO.getDataFim();
        }

        if (filtroFornecedorDTO.getPeriodoMes() != null && !filtroFornecedorDTO.getPeriodoMes().equals("")) {
            URI_ORDENS += "mes=" + filtroFornecedorDTO.getPeriodoMes();
        }

        if (filtroFornecedorDTO.getPeriodoAno() != null && !filtroFornecedorDTO.getPeriodoAno().equals("")) {
            URI_ORDENS += "&ano=" + filtroFornecedorDTO.getPeriodoAno();
        }

        if (filtroFornecedorDTO.getCpfCnpj() != null && !filtroFornecedorDTO.getCpfCnpj().equals("")) {
            URI_ORDENS += "cpfCnpj=" + filtroFornecedorDTO.getCpfCnpj();
        }

        if (filtroFornecedorDTO.getTelefone() != null && !filtroFornecedorDTO.getTelefone().equals("")) {
            URI_ORDENS += "telefone=" + filtroFornecedorDTO.getTelefone();
        }

        return URI_ORDENS;
    }

}
