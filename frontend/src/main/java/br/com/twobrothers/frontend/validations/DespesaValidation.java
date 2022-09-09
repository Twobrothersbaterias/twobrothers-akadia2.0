package br.com.twobrothers.frontend.validations;

import br.com.twobrothers.frontend.models.dto.DespesaDTO;
import br.com.twobrothers.frontend.models.enums.StatusDespesaEnum;
import br.com.twobrothers.frontend.repositories.services.exceptions.InvalidRequestException;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static br.com.twobrothers.frontend.utils.RegexPatterns.DATE_REGEX;

/**
 * @author Gabriel Lagrota
 * @version 1.0
 * @email gabriellagrota23@gmail.com
 * @phone (11)97981-5415
 * @github https://github.com/LagrotaGabriel
 * @since 30-08-22
 */
@Slf4j
public class DespesaValidation {

    public void validaCorpoDaRequisicao(DespesaDTO despesa) {
        validaSePossuiAtributosNulos(despesa);
        if (despesa.getDataAgendamento() != null) validaAtributoDataAgendamento(despesa.getDataAgendamento());
        if (despesa.getStatusDespesa().equals(StatusDespesaEnum.PAGO))
            validaAtributoDataPagamento(despesa.getDataPagamento());
        log.warn("[VALIDAÇÃO - DESPESA] Validação do objeto despesa finalizada com sucesso");
    }

    public void validaSePossuiAtributosNulos(DespesaDTO despesa) {

        log.info("[VALIDAÇÃO - DESPESA] Inicializando validação de atributos obrigatórios nulos...");
        List<String> atributosNulos = new ArrayList<>();

        if (despesa.getDescricao() == null) atributosNulos.add("descricao");
        if (despesa.getTipoDespesa() == null) atributosNulos.add("tipoDespesa");
        if (despesa.getStatusDespesa() == null) atributosNulos.add("statusDespesa");
        if (despesa.getValor() == null) atributosNulos.add("valor");

        if (!atributosNulos.isEmpty())
            throw new InvalidRequestException("Validação da despesa falhou. A inserção de um ou mais atributos " +
                    "obrigatórios é necessária no corpo da requisição: " + atributosNulos);

        log.warn("Validação de atributos nulos OK");
    }

    public void validaAtributoDataAgendamento(String data) {

        log.info("[VALIDAÇÃO - DESPESA] Inicializando validação do atributo dataAgendamento...");

        LocalDate hoje = LocalDate.now();

        if (!data.matches(DATE_REGEX)) {
            log.error("[ERROR] Validação da despesa falhou. Motivo: o padrão da data de agendamento é inválido");
            throw new InvalidRequestException("Validação da despesa falhou. Motivo: o padrão da data de agendamento é inválido");
        }

        String dataDeAgendamento = data.replace("/", "-").split("-")[2] + "-"
                + data.replace("/", "-").split("-")[1] + "-"
                + data.replace("/", "-").split("-")[0];

        LocalDate dataAgendada = LocalDate.parse(dataDeAgendamento);
        if (dataAgendada.isBefore(hoje)) {
            log.error("[ERROR] Não é possível realizar um agendamento para uma data no passado");
            throw new InvalidRequestException("Não é possível realizar um agendamento para uma data no passado");
        }

        log.warn("Validação do atributo dataAgendamento OK");

    }

    public void validaAtributoDataPagamento(String data) {
        log.info("[VALIDAÇÃO - DESPESA] Inicializando validação do atributo dataPagamento...");
        if (!data.matches(DATE_REGEX))
            throw new InvalidRequestException("Validação da despesa falhou. Motivo: o padrão da data de pagamento é inválido");
        log.warn("Validação do atributo dataPagamento OK");
    }

}
