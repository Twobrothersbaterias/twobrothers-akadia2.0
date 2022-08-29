package br.com.twobrothers.msdespesas.validations;

import br.com.twobrothers.msdespesas.models.dto.DespesaDTO;
import br.com.twobrothers.msdespesas.models.enums.StatusDespesaEnum;
import br.com.twobrothers.msdespesas.services.exceptions.InvalidRequestException;

import java.time.LocalDate;

import static br.com.twobrothers.msdespesas.utils.RegexPatterns.DATE_REGEX;

public class DespesaValidation {

    public boolean validaCorpoDaRequisicao(DespesaDTO despesa) {
        validaSePossuiAtributosNulos(despesa);
        if (despesa.getDataAgendamento() != null) {
            validaAtributoDataAgendamento(despesa.getDataAgendamento());
        }
        if (despesa.getStatusDespesa().equals(StatusDespesaEnum.PAGO)) {
            validaAtributoDataPagamento(despesa.getDataPagamento());
        }
        return true;
    }

    public boolean validaSePossuiAtributosNulos(DespesaDTO despesa) {
        if (despesa.getDescricao() != null &&
                despesa.getTipoDespesa() != null &&
                despesa.getStatusDespesa() != null &&
                despesa.getValor() != null) return true;
        throw new InvalidRequestException("Validação da despesa falhou. Motivo: um ou mais atributos recebido(s) " +
                "na requisição são nulos");
    }

    public boolean validaAtributoDataAgendamento(String data) {

        LocalDate hoje = LocalDate.now();

        if (data.matches(DATE_REGEX)) {

            String dataDeAgendamento = data.replace("/", "-").split("-")[2] + "-"
                    + data.replace("/", "-").split("-")[1] + "-"
                    + data.replace("/", "-").split("-")[0];

            LocalDate dataAgendada = LocalDate.parse(dataDeAgendamento);
            if (dataAgendada.isBefore(hoje))
                throw new InvalidRequestException("Não é possível realizar um agendamento para uma data no passado");

            return true;
        }
        throw new InvalidRequestException("Validação da despesa falhou. Motivo: o padrão da data de agendamento é inválido");
    }

    public boolean validaAtributoDataPagamento(String data) {
        if (data.matches(DATE_REGEX)) return true;
        throw new InvalidRequestException("Validação da despesa falhou. Motivo: o padrão da data de pagamento é inválido");
    }

}
