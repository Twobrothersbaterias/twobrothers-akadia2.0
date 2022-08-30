package br.com.twobrothers.msvendas.validations;

import br.com.twobrothers.msvendas.exceptions.InvalidRequestException;
import br.com.twobrothers.msvendas.models.dto.PagamentoDTO;

import java.time.LocalDate;
import java.util.List;

import static br.com.twobrothers.msvendas.utils.RegexPatterns.DATE_REGEX;

public class PagamentoValidation {

    public void validaCorpoRequisicaoEmMassa(List<PagamentoDTO> pagamentos) {
        for (PagamentoDTO pagamentoDTO: pagamentos) {
            validaCorpoRequisicao(pagamentoDTO);
        }
    }

    public void validaCorpoRequisicao(PagamentoDTO pagamentoDTO) {
        validaSePossuiAtributosNulos(pagamentoDTO);
        if (pagamentoDTO.getDataPagamento() != null) validaAtributoDataPagamento(pagamentoDTO.getDataPagamento());
        if (pagamentoDTO.getDataAgendamento() != null )validaAtributoDataAgendamento(pagamentoDTO.getDataAgendamento());
    }

    public void validaSePossuiAtributosNulos(PagamentoDTO pagamentoDTO) {

        if (pagamentoDTO.getDataAgendamento() != null) {

            if (pagamentoDTO.getDataPagamento() != null)
                throw new InvalidRequestException("Não é possível cadastrar a data de pagamento em um agendamento");

            if (pagamentoDTO.getFormaPagamento() != null)
                throw new InvalidRequestException("Não é possível cadastrar a forma de pagamento em um agendamento");
        }
        else {
            if (pagamentoDTO.getDataAgendamento() != null)
                throw new InvalidRequestException("Não é possível cadastrar uma data de agendamento em um pagamento que já foi realizado");

            if (pagamentoDTO.getFormaPagamento() == null)
                throw  new InvalidRequestException("A forma de pagamento não pode ser nula");
        }

        if (pagamentoDTO.getValor() == null)
            throw new InvalidRequestException("O campo valor não pode ser nulo");

    }

    public void validaAtributoDataPagamento(String dataPagamento) {

        LocalDate hoje = LocalDate.now();

        if (!dataPagamento.matches(DATE_REGEX))
            throw new InvalidRequestException("Validação do pagamento falhou. Motivo: o padrão da data de pagamento é inválido");

        String dataDoPagamento = dataPagamento.replace("/", "-").split("-")[2] + "-"
                + dataPagamento.replace("/", "-").split("-")[1] + "-"
                + dataPagamento.replace("/", "-").split("-")[0];

        LocalDate dataAgendada = LocalDate.parse(dataDoPagamento);

        if (dataAgendada.isAfter(hoje))
            throw new InvalidRequestException("Não é possível realizar um pagamento para uma data no futuro");

    }

    public void validaAtributoDataAgendamento(String dataAgendamento) {

        LocalDate hoje = LocalDate.now();

        if (!dataAgendamento.matches(DATE_REGEX))
            throw new InvalidRequestException("Validação do pagamento falhou. Motivo: o padrão da data de agendamento é inválido");

        String dataDeAgendamento = dataAgendamento.replace("/", "-").split("-")[2] + "-"
                + dataAgendamento.replace("/", "-").split("-")[1] + "-"
                + dataAgendamento.replace("/", "-").split("-")[0];

        LocalDate dataAgendada = LocalDate.parse(dataDeAgendamento);

        if (dataAgendada.isBefore(hoje))
            throw new InvalidRequestException("Não é possível realizar um agendamento para uma data no passado");

    }

}