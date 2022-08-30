package br.com.twobrothers.msvendas.validations;

import br.com.twobrothers.msvendas.exceptions.InvalidRequestException;
import br.com.twobrothers.msvendas.models.dto.EntradaOrdemDTO;
import br.com.twobrothers.msvendas.models.dto.OrdemDTO;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

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

        if (ordem.getEmiteNfe() == null) atributosNulos.add("emiteNfe");
        if (ordem.getLoja() == null) atributosNulos.add("loja");
        if (ordem.getRetirada() == null) atributosNulos.add("retirada");
        if (ordem.getPagamentos() == null || ordem.getPagamentos().isEmpty()) atributosNulos.add("pagamentos");
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

}
