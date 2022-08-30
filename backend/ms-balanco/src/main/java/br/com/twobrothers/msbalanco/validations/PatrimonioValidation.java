package br.com.twobrothers.msbalanco.validations;

import br.com.twobrothers.msbalanco.models.dto.PatrimonioDTO;
import br.com.twobrothers.msbalanco.services.exceptions.InvalidRequestException;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static br.com.twobrothers.msbalanco.utils.RegexPatterns.DATE_REGEX;

@Slf4j
public class PatrimonioValidation {

    public void validaCorpoDaRequisicao(PatrimonioDTO patrimonio) {
        validaSePossuiAtributosNulos(patrimonio);
        if (patrimonio.getDataAgendamento() != null) validaAtributoDataAgendamento(patrimonio.getDataAgendamento());
    }

    public void validaSePossuiAtributosNulos(PatrimonioDTO patrimonio) {

        log.info("[VALIDAÇÃO - PATRIMONIO] Inicializando validação de atributos obrigatórios nulos...");
        List<String> atributosNulos = new ArrayList<>();

        if (patrimonio.getNome() == null) atributosNulos.add("nome");
        if (patrimonio.getTipoPatrimonio() == null) atributosNulos.add("tipoPatrimonio");
        if (patrimonio.getStatusPatrimonio() == null) atributosNulos.add("statusPatrimonio");
        if (patrimonio.getValor() == null) atributosNulos.add("valor");
        if (patrimonio.getIdUsuarioResponsavel() == null) atributosNulos.add("idUsuarioResponsavel");

        if (!atributosNulos.isEmpty())
            throw new InvalidRequestException("Validação do patrimonio falhou. A inserção de um ou mais atributos " +
                    "obrigatórios é necessária no corpo da requisição: " + atributosNulos);

        log.warn("Validação de atributos nulos OK");

    }

    public void validaAtributoDataAgendamento(String data) {

        log.info("[VALIDAÇÃO - PATRIMONIO] Inicializando validação do atributo dataAgendamento...");

        LocalDate hoje = LocalDate.now();

        if (!data.matches(DATE_REGEX))
            throw new InvalidRequestException
                    ("Validação do patrimoônio falhou. Motivo: o padrão da data de agendamento é inválido");

        String dataDeAgendamento = data.replace("/", "-").split("-")[2] + "-"
                + data.replace("/", "-").split("-")[1] + "-"
                + data.replace("/", "-").split("-")[0];

        LocalDate dataAgendada = LocalDate.parse(dataDeAgendamento);
        if (dataAgendada.isBefore(hoje))
            throw new InvalidRequestException("Não é possível realizar um agendamento para uma data no passado");

        log.warn("Validação do atributo dataAgendamento OK");
    }

}
