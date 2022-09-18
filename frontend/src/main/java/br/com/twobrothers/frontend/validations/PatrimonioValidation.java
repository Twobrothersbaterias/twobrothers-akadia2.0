package br.com.twobrothers.frontend.validations;

import br.com.twobrothers.frontend.models.dto.PatrimonioDTO;
import br.com.twobrothers.frontend.repositories.services.exceptions.InvalidRequestException;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.ArrayList;
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
public class PatrimonioValidation {

    public void validaCorpoDaRequisicao(PatrimonioDTO patrimonio) {
        validaSePossuiAtributosNulos(patrimonio);
        if (patrimonio.getDataAgendamento() != null) validaAtributoDataAgendamento(patrimonio.getDataAgendamento());
        if (patrimonio.getDataPagamento() != null) validaAtributoDataAgendamento(patrimonio.getDataPagamento());
        log.warn("[VALIDAÇÃO - PATRIMONIO] Validação do objeto patrimonio finalizada com sucesso");
    }

    public void validaSePossuiAtributosNulos(PatrimonioDTO patrimonio) {

        log.info("[VALIDAÇÃO - PATRIMONIO] Inicializando validação de atributos obrigatórios nulos...");
        List<String> atributosNulos = new ArrayList<>();

        if (patrimonio.getNome() == null) atributosNulos.add("nome");
        if (patrimonio.getTipoPatrimonio() == null) atributosNulos.add("tipoPatrimonio");
        if (patrimonio.getStatusPatrimonio() == null) atributosNulos.add("statusPatrimonio");
        if (patrimonio.getValor() == null) atributosNulos.add("valor");
        if (patrimonio.getUsuarioResponsavel() == null) atributosNulos.add("idUsuarioResponsavel");

        if (!atributosNulos.isEmpty())
            throw new InvalidRequestException("Validação do patrimonio falhou. A inserção de um ou mais atributos " +
                    "obrigatórios é necessária no corpo da requisição: " + atributosNulos);

        log.warn("Validação de atributos nulos OK");

    }

    public void validaAtributoDataAgendamento(String data) {

        log.info("[VALIDAÇÃO - PATRIMONIO] Inicializando validação do atributo dataAgendamento...");

        LocalDate hoje = LocalDate.now();

        LocalDate dataAgendada = LocalDate.parse(data);

        if (dataAgendada.isBefore(hoje)) {
            log.error("[ERROR] Não é possível realizar um agendamento para uma data no passado");
            throw new InvalidRequestException("Não é possível realizar um agendamento para uma data no passado");
        }

        log.warn("Validação do atributo dataAgendamento OK");
    }

    public void validaAtributoDataPagamento(String data) {
        log.info("[VALIDAÇÃO - PATRIMONIO] Inicializando validação do atributo dataPagamento...");

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
