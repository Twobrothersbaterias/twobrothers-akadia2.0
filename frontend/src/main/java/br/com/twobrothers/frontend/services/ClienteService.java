package br.com.twobrothers.frontend.services;

import br.com.twobrothers.frontend.config.ModelMapperConfig;
import br.com.twobrothers.frontend.models.dto.ClienteDTO;
import br.com.twobrothers.frontend.models.dto.filters.FiltroClienteDTO;
import br.com.twobrothers.frontend.models.entities.ClienteEntity;
import br.com.twobrothers.frontend.repositories.ClienteRepository;
import br.com.twobrothers.frontend.repositories.UsuarioRepository;
import br.com.twobrothers.frontend.repositories.services.ClienteCrudService;
import br.com.twobrothers.frontend.repositories.services.exceptions.InvalidRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static br.com.twobrothers.frontend.utils.StringConstants.URI_CLIENTES;

@Slf4j
@Service
public class ClienteService {

    @Autowired
    ClienteCrudService crudService;

    @Autowired
    UsuarioRepository usuario;

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    ModelMapperConfig modelMapper;

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
            Integer mes,
            Integer ano,
            String cpfCnpj,
            String telefone) throws InvalidRequestException {
        if (descricao != null) return crudService.buscaPorNomeCompleto(pageable, descricao);
        else if (dataInicio != null && dataFim != null)
            return crudService.buscaPorRangeDeData(pageable, dataInicio, dataFim);
        else if (mes != null && ano != null) return crudService.buscaPorPeriodo(pageable, mes, ano);
        else if (cpfCnpj != null) return crudService.buscaPorCpfCnpj(pageable, cpfCnpj);
        else if (telefone != null) return crudService.buscaPorTelefone(pageable, telefone);
        else return crudService.buscaHoje(pageable);
    }

    public List<ClienteEntity> filtroClientesSemPaginacao(
            String descricao,
            String dataInicio,
            String dataFim,
            Integer mes,
            Integer ano,
            String cpfCnpj,
            String telefone) throws InvalidRequestException {
        if (descricao != null) return crudService.buscaPorNomeCompletoSemPaginacao(descricao);
        else if (dataInicio != null && dataFim != null)
            return crudService.buscaPorRangeDeDataSemPaginacao(dataInicio, dataFim);
        else if (mes != null && ano != null) return crudService.buscaPorPeriodoSemPaginacao(mes, ano);
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

        if (filtroClienteDTO.getDescricao() != null && !filtroClienteDTO.getDescricao().equals("")) {
            URI_CLIENTES += "descricao=" + filtroClienteDTO.getDescricao();
        }

        if (filtroClienteDTO.getDataInicio() != null && !filtroClienteDTO.getDataInicio().equals("")) {
            URI_CLIENTES += "inicio=" + filtroClienteDTO.getDataInicio();
        }

        if (filtroClienteDTO.getDataFim() != null && !filtroClienteDTO.getDataFim().equals("")) {
            URI_CLIENTES += "fim=" + filtroClienteDTO.getDataFim();
        }

        if (filtroClienteDTO.getPeriodoMes() != null && !filtroClienteDTO.getPeriodoMes().equals("")) {
            URI_CLIENTES += "mes=" + filtroClienteDTO.getPeriodoMes();
        }

        if (filtroClienteDTO.getPeriodoAno() != null && !filtroClienteDTO.getPeriodoAno().equals("")) {
            URI_CLIENTES += "ano=" + filtroClienteDTO.getPeriodoAno();
        }

        if (filtroClienteDTO.getCpfCnpj() != null && !filtroClienteDTO.getCpfCnpj().equals("")) {
            URI_CLIENTES += "cpfCnpj=" + filtroClienteDTO.getCpfCnpj();
        }

        if (filtroClienteDTO.getTelefone() != null && !filtroClienteDTO.getTelefone().equals("")) {
            URI_CLIENTES += "telefone=" + filtroClienteDTO.getTelefone();
        }

        return URI_CLIENTES;
    }

}
