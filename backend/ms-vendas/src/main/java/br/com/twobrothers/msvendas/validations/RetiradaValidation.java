package br.com.twobrothers.msvendas.validations;

import br.com.twobrothers.msvendas.exceptions.InvalidRequestException;
import br.com.twobrothers.msvendas.models.dto.RetiradaDTO;

import java.time.LocalDate;

import static br.com.twobrothers.msvendas.utils.RegexPatterns.DATE_REGEX;

public class RetiradaValidation {

    public boolean validaCorpoRequisicao(RetiradaDTO retiradaDTO) {
        validaSePossuiAtributosNulos(retiradaDTO);
        return true;
    }

    public void validaSePossuiAtributosNulos(RetiradaDTO retiradaDTO) {
        if (retiradaDTO.getStatusRetirada() == null
                || retiradaDTO.getTecnicoEntrada() == null)
            throw new InvalidRequestException("Alguns atributos obrigatórios devem ser inseridos no corpo da requisição");
    }

    public void validaAtributoDataAgendamento(String data) {

        LocalDate hoje = LocalDate.now();

        if (!data.matches(DATE_REGEX))
            throw new InvalidRequestException("Validação da retirada falhou. Motivo: o padrão da data de agendamento é inválido");

        String dataDeAgendamento = data.replace("/", "-").split("-")[2] + "-"
                + data.replace("/", "-").split("-")[1] + "-"
                + data.replace("/", "-").split("-")[0];

        LocalDate dataAgendada = LocalDate.parse(dataDeAgendamento);

        if (dataAgendada.isBefore(hoje))
            throw new InvalidRequestException("Não é possível realizar um agendamento para uma data no passado");

    }

}
