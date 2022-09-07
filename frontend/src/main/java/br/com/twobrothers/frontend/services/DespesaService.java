package br.com.twobrothers.frontend.services;

import br.com.twobrothers.frontend.models.dto.DespesaDTO;
import br.com.twobrothers.frontend.proxys.DespesaServiceProxy;
import br.com.twobrothers.frontend.utils.ConversorDeDatas;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DespesaService {

    @Autowired
    DespesaServiceProxy proxy;

    public void tratamentoDeNovaDespesa(DespesaDTO despesaDTO) {

        //TODO SETAR ID DO USUÁRIO QUE CRIOU A DESPESA

        if (despesaDTO.getDataPagamento() != null)
            despesaDTO.setDataPagamento(ConversorDeDatas.converteDataUsParaDataBr(despesaDTO.getDataPagamento()));

        if (despesaDTO.getDataAgendamento() != null)
            despesaDTO.setDataAgendamento(ConversorDeDatas.converteDataUsParaDataBr(despesaDTO.getDataAgendamento()));

        proxy.novaDespesa(despesaDTO).getBody();
    }

}
