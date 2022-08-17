package br.com.twobrothers.msdespesas.validations;

import br.com.twobrothers.msdespesas.services.exceptions.InvalidRequestException;
import br.com.twobrothers.msdespesas.models.dto.DespesaDTO;
import br.com.twobrothers.msdespesas.models.enums.StatusDespesaEnum;

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
        if (data.matches(DATE_REGEX)) return true;
        throw new InvalidRequestException("Validação da despesa falhou. Motivo: o padrão da data de agendamento é inválido");
    }

    public boolean validaAtributoDataPagamento(String data) {
        if (data.matches(DATE_REGEX)) return true;
        throw new InvalidRequestException("Validação da despesa falhou. Motivo: o padrão da data de pagamento é inválido");
    }

}
