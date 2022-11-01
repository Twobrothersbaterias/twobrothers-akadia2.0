package br.com.twobrothers.frontend.validations;

import br.com.twobrothers.frontend.models.dto.EntradaOrdemDTO;
import br.com.twobrothers.frontend.models.dto.OrdemDTO;
import br.com.twobrothers.frontend.repositories.services.exceptions.InvalidRequestException;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Gabriel Lagrota
 * @email gabriellagrota23@gmail.com
 * @phone (11)97981-5415
 * @github https://github.com/LagrotaGabriel
 * @version 1.0
 * @since 30-08-22
 */
@Slf4j
public class OrdemValidation {

    EntradaOrdemValidation entradaOrdemValidation = new EntradaOrdemValidation();

    public void validaCorpoRequisicao(OrdemDTO ordem) {
        validaSePossuiAtributosNulos(ordem);
        validaAtributosEntradaOrdem(ordem);
        log.warn("[VALIDAÇÃO - ORDEM] Validação do objeto abastecimento finalizada com sucesso");
    }

    public void validaSePossuiAtributosNulos(OrdemDTO ordem) {
        log.info("[VALIDAÇÃO - ORDEM] Inicializando validação de atributos obrigatórios nulos...");
        List<String> atributosNulos = new ArrayList<>();

        if (ordem.getTipoNfe() == null) atributosNulos.add("emiteNfe");
        if (ordem.getLoja() == null) atributosNulos.add("loja");
        if (ordem.getRetirada() == null) atributosNulos.add("retirada");
        if (ordem.getEntradas() == null || ordem.getEntradas().isEmpty()) atributosNulos.add("entradas");

        if (!atributosNulos.isEmpty())
            throw new InvalidRequestException("Validação do abastecimento falhou. A inserção de um ou mais atributos " +
                    "obrigatórios é necessária no corpo da requisição: " + atributosNulos);

        log.warn("Validação de atributos nulos OK");
    }

    public void validaAtributosEntradaOrdem(OrdemDTO ordem) {
        log.info("[VALIDAÇÃO - ORDEM] Inicializando validação das entradas da ordem...");
        for (EntradaOrdemDTO entradaOrdem : ordem.getEntradas()) {
            entradaOrdemValidation.validaCorpoRequisicao(entradaOrdem);
        }
        log.warn("Validação das entradas da ordem OK");
    }

    public void validaRangeData(String inicio, String fim) {

        LocalDate dataInicio = LocalDate.parse(inicio);
        LocalDate dataFim = LocalDate.parse(fim);

        if (dataInicio.isAfter(dataFim))
            throw new InvalidRequestException("O conteúdo do campo data início não pode ser anterior ao campo data fim");

    }

}
