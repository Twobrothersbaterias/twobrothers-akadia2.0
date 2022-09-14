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
        if (despesa.getStatusDespesa().equals(StatusDespesaEnum.PENDENTE)) validaAtributoDataAgendamento(despesa.getDataAgendamento());
        if (despesa.getStatusDespesa().equals(StatusDespesaEnum.PAGO)) validaAtributoDataPagamento(despesa.getDataPagamento());
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

        LocalDate dataAgendada = LocalDate.parse(data);

        if (dataAgendada.isBefore(hoje)) {
            log.error("[ERROR] Não é possível realizar um agendamento para uma data no passado");
            throw new InvalidRequestException("Não é possível realizar um agendamento para uma data no passado");
        }

        log.warn("Validação do atributo dataAgendamento OK");
    }

    public void validaAtributoDataPagamento(String data) {
        log.info("[VALIDAÇÃO - DESPESA] Inicializando validação do atributo dataPagamento...");

        LocalDate hoje = LocalDate.now();
        LocalDate dataPagamento = LocalDate.parse(data);

        if (dataPagamento.isAfter(hoje)) {
            log.error("[ERROR] Não é possível realizar um pagamento para uma data no futuro");
            throw new InvalidRequestException("Não é possível realizar um pagamento para uma data no futuro");
        }

        log.warn("Validação do atributo pagamento OK");
    }

    public void validaRangeData(String inicio, String fim) {

        LocalDate dataInicio = LocalDate.parse(inicio);
        LocalDate dataFim = LocalDate.parse(fim);

        if (dataInicio.isAfter(dataFim))
            throw new InvalidRequestException("O conteúdo do campo data início não pode ser anterior ao campo data fim");

    }

}
