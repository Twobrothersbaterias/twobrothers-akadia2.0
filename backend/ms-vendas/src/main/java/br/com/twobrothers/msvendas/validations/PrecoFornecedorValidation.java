package br.com.twobrothers.msvendas.validations;

import br.com.twobrothers.msvendas.exceptions.InvalidRequestException;
import br.com.twobrothers.msvendas.models.dto.PrecoFornecedorDTO;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class PrecoFornecedorValidation {

    public void validaCorpoRequisicao(PrecoFornecedorDTO preco) {
        validaSePossuiAtributosNulos(preco);
        log.warn("[VALIDAÇÃO - PRECO] Validação do objeto PrecoFornecedorDTO finalizada com sucesso");
    }

    public void validaSePossuiAtributosNulos(PrecoFornecedorDTO preco) {

        log.info("[VALIDAÇÃO - PRECO] Inicializando validação de atributos obrigatórios nulos...");
        List<String> atributosNulos = new ArrayList<>();

        if (preco.getValor() == null) atributosNulos.add("preco");
        if (preco.getIdUsuarioResponsavel() == null) atributosNulos.add("idUsuarioResponsavel");

        if (!atributosNulos.isEmpty())
            throw new InvalidRequestException("Validação do preço falhou. A inserção de um ou mais atributos " +
                    "obrigatórios é necessária no corpo da requisição: " + atributosNulos);

        log.warn("Validação de atributos nulos OK");
    }

}
