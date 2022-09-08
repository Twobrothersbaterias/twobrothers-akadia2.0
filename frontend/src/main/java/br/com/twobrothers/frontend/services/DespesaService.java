package br.com.twobrothers.frontend.services;

import br.com.twobrothers.frontend.models.dto.DespesaDTO;
import br.com.twobrothers.frontend.proxys.DespesaServiceProxy;
import br.com.twobrothers.frontend.repositories.UsuarioRepository;
import br.com.twobrothers.frontend.utils.ConversorDeDatas;
import br.com.twobrothers.frontend.utils.UsuarioUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class DespesaService {

    @Autowired
    DespesaServiceProxy proxy;

    @Autowired
    UsuarioRepository usuario;

    public void tratamentoDeNovaDespesa(DespesaDTO despesaDTO) {

        despesaDTO.setIdUsuarioResponsavel(UsuarioUtils.loggedUser(usuario).getId());

        if (despesaDTO.getDataPagamento() != null)
            despesaDTO.setDataPagamento(ConversorDeDatas.converteDataUsParaDataBr(despesaDTO.getDataPagamento()));

        if (despesaDTO.getDataAgendamento() != null)
            despesaDTO.setDataAgendamento(ConversorDeDatas.converteDataUsParaDataBr(despesaDTO.getDataAgendamento()));

        proxy.novaDespesa(despesaDTO).getBody();
    }

    public List<DespesaDTO> filtrandoDespesas(Pageable pageable) {

        List<DespesaDTO> despesaDTOList = proxy.buscaDespesasPorPaginacao(pageable).getBody();

        return despesaDTOList;
    }

}
