package br.com.twobrothers.msvendas.validations;

import br.com.twobrothers.msvendas.exceptions.InvalidRequestException;
import br.com.twobrothers.msvendas.models.dto.RetiradaDTO;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static br.com.twobrothers.msvendas.utils.RegexPatterns.DATE_REGEX;

@Slf4j
public class RetiradaValidation {

    public void validaCorpoRequisicao(RetiradaDTO retiradaDTO) {
        validaSePossuiAtributosNulos(retiradaDTO);
        if (retiradaDTO.getDataAgendamento() != null) validaAtributoDataAgendamento(retiradaDTO.getDataAgendamento());
        log.warn("[VALIDAÇÃO - RETIRADA] Validação do objeto retirada finalizada com sucesso");
    }

    public void validaSePossuiAtributosNulos(RetiradaDTO retiradaDTO) {

        log.info("[VALIDAÇÃO - RETIRADA] Inicializando validação de atributos obrigatórios nulos...");
        List<String> atributosNulos = new ArrayList<>();

        if (retiradaDTO.getStatusRetirada() == null) atributosNulos.add("statusRetirada");
        if (retiradaDTO.getTecnicoEntrada() == null) atributosNulos.add("tecnicoEntrada");

        if (!atributosNulos.isEmpty())
            throw new InvalidRequestException("Validação do abastecimento falhou. A inserção de um ou mais atributos " +
                    "obrigatórios é necessária no corpo da requisição: " + atributosNulos);

        log.warn("Validação de atributos nulos OK");

    }

    public void validaAtributoDataAgendamento(String data) {
        log.info("[VALIDAÇÃO - RETIRADA] Inicializando validação do atributo dataAgendamento...");

        LocalDate hoje = LocalDate.now();

        if (!data.matches(DATE_REGEX))
            throw new InvalidRequestException("Validação da retirada falhou. Motivo: o padrão da data de agendamento é inválido");

        String dataDeAgendamento = data.replace("/", "-").split("-")[2] + "-"
                + data.replace("/", "-").split("-")[1] + "-"
                + data.replace("/", "-").split("-")[0];

        LocalDate dataAgendada = LocalDate.parse(dataDeAgendamento);

        if (dataAgendada.isBefore(hoje))
            throw new InvalidRequestException("Não é possível realizar um agendamento para uma data no passado");

        log.warn("Validação do atributo dataAgendamento OK");
    }

}
