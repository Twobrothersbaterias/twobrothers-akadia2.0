package br.com.twobrothers.frontend.validations;

import br.com.twobrothers.frontend.models.dto.EntradaOrdemDTO;
import br.com.twobrothers.frontend.repositories.services.exceptions.InvalidRequestException;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

import static br.com.twobrothers.frontend.utils.RegexPatterns.QUANTIDADE_REGEX;

/**
 * @author Gabriel Lagrota
 * @email gabriellagrota23@gmail.com
 * @phone (11)97981-5415
 * @github https://github.com/LagrotaGabriel
 * @version 1.0
 * @since 30-08-22
 */
@Slf4j
public class EntradaOrdemValidation {

    public void validaCorpoRequisicao(EntradaOrdemDTO entradaOrdemDTO) {
        validaSePossuiAtributosNulos(entradaOrdemDTO);
    }

    public void validaSePossuiAtributosNulos(EntradaOrdemDTO entradaOrdemDTO) {

        log.info("[VALIDAÇÃO - ENTRADA] Inicializando validação de atributos obrigatórios nulos...");
        List<String> atributosNulos = new ArrayList<>();

        if (entradaOrdemDTO.getTipoEntrada() == null) atributosNulos.add("tipoEntrada");
        if (entradaOrdemDTO.getTipoOrdem() == null) atributosNulos.add("tipoOrdem");
        if (entradaOrdemDTO.getQuantidade() == null) atributosNulos.add("quantidade");
        if (entradaOrdemDTO.getValor() == null) atributosNulos.add("valor");

        if (!atributosNulos.isEmpty())
            throw new InvalidRequestException("Validação da entrada falhou. A inserção de um ou mais atributos " +
                    "obrigatórios é necessária no corpo da requisição: " + atributosNulos);

        log.warn("Validação de atributos nulos OK");
    }

}
