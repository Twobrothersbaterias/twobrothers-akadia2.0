package br.com.twobrothers.frontend.validations;

import br.com.twobrothers.frontend.models.dto.PagamentoDTO;
import br.com.twobrothers.frontend.repositories.services.exceptions.InvalidRequestException;
import br.com.twobrothers.frontend.utils.ConversorDeDados;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.List;

import static br.com.twobrothers.frontend.utils.RegexPatterns.DATE_REGEX;

/**
 * @author Gabriel Lagrota
 * @email gabriellagrota23@gmail.com
 * @phone (11)97981-5415
 * @github https://github.com/LagrotaGabriel
 * @version 1.0
 * @since 30-08-22
 */
@Slf4j
public class PagamentoValidation {

    //TODO ARRUMAR VALIDATIONS

    public void validaCorpoRequisicaoEmMassa(List<PagamentoDTO> pagamentos) {
        for (PagamentoDTO pagamentoDTO : pagamentos) {
            validaCorpoRequisicao(pagamentoDTO);
        }
        log.warn("[VALIDAÇÃO - PAGAMENTO] Validação da listas de pagamento finalizada com sucesso");
    }

    public void validaCorpoRequisicao(PagamentoDTO pagamentoDTO) {
        validaSePossuiAtributosNulos(pagamentoDTO);
//        if (pagamentoDTO.getDataPagamento() != null && !pagamentoDTO.getDataPagamento().equals("Em aberto"))
//            validaAtributoDataPagamento(pagamentoDTO.getDataPagamento());
//        if (pagamentoDTO.getDataAgendamento() != null && !pagamentoDTO.getDataAgendamento().equals("Sem agendamento"))
//            validaAtributoDataAgendamento(pagamentoDTO.getDataAgendamento());
        log.warn("[VALIDAÇÃO - PAGAMENTO] Validação do objeto pagamento finalizada com sucesso");
    }

    public void validaSePossuiAtributosNulos(PagamentoDTO pagamentoDTO) {
        log.info("[VALIDAÇÃO - PAGAMENTO] Inicializando validação de atributos obrigatórios nulos...");

        if (pagamentoDTO.getDataAgendamento() != null && !pagamentoDTO.getDataAgendamento().equals("Sem agendamento")) {

//            if (pagamentoDTO.getDataPagamento() != null && !pagamentoDTO.getDataPagamento().isEmpty())
//                throw new InvalidRequestException("Não é possível cadastrar a data de pagamento em um agendamento");

            if (pagamentoDTO.getFormaPagamento() != null && !pagamentoDTO.getDataPagamento().isEmpty())
                throw new InvalidRequestException("Não é possível cadastrar a forma de pagamento em um agendamento");
        } else {
//            if (pagamentoDTO.getDataAgendamento() != null && !pagamentoDTO.getDataAgendamento().isEmpty() && !pagamentoDTO.getDataAgendamento().equals("Sem agendamento"))
//                throw new InvalidRequestException("Não é possível cadastrar uma data de agendamento em um pagamento que já foi realizado");

            if (pagamentoDTO.getFormaPagamento() == null && !pagamentoDTO.getDataPagamento().isEmpty())
                throw new InvalidRequestException("A forma de pagamento não pode ser nula");
        }

        if (pagamentoDTO.getValor() == null)
            throw new InvalidRequestException("O campo valor não pode ser nulo");


        log.warn("Validação de atributos nulos OK");
    }

    public void validaAtributoDataPagamento(String dataPagamento) {
        log.info("[VALIDAÇÃO - PAGAMENTO] Inicializando validação do atributo dataPagamento...");

        LocalDate hoje = LocalDate.now();

        if (dataPagamento.matches(DATE_REGEX))
            throw new InvalidRequestException("Validação do pagamento falhou. Motivo: o padrão da data de pagamento é inválido");

        String dataDoPagamento = dataPagamento.replace("/", "-").split("-")[2] + "-"
                + dataPagamento.replace("/", "-").split("-")[1] + "-"
                + dataPagamento.replace("/", "-").split("-")[0];

        LocalDate dataAgendada = LocalDate.parse(dataDoPagamento);

        if (dataAgendada.isAfter(hoje))
            throw new InvalidRequestException("Não é possível realizar um pagamento para uma data no futuro");

        log.warn("Validação do atributo dataPagamento OK");
    }

    public void validaAtributoDataAgendamento(String dataAgendamento) {
        log.info("[VALIDAÇÃO - PAGAMENTO] Inicializando validação do atributo dataAgendamento...");

        LocalDate hoje = LocalDate.now();

        if (!dataAgendamento.matches(DATE_REGEX))
            throw new InvalidRequestException("Validação do pagamento falhou. Motivo: o padrão da data de agendamento é inválido");

        String dataDeAgendamento = dataAgendamento.replace("/", "-").split("-")[2] + "-"
                + dataAgendamento.replace("/", "-").split("-")[1] + "-"
                + dataAgendamento.replace("/", "-").split("-")[0];

        LocalDate dataAgendada = LocalDate.parse(dataDeAgendamento);

        if (dataAgendada.isBefore(hoje))
            throw new InvalidRequestException("Não é possível realizar um agendamento para uma data no passado");

        log.warn("Validação do atributo dataAgendamento OK");
    }

}
