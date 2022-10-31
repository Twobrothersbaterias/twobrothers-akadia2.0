package br.com.twobrothers.frontend.validations;

import br.com.twobrothers.frontend.models.dto.PrecoFornecedorDTO;
import br.com.twobrothers.frontend.repositories.services.exceptions.InvalidRequestException;
import lombok.extern.slf4j.Slf4j;

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
public class PrecoFornecedorValidation {

    public void validaCorpoRequisicao(PrecoFornecedorDTO preco) {
        validaSePossuiAtributosNulos(preco);
        log.warn("[VALIDAÇÃO - PRECO] Validação do objeto PrecoFornecedorDTO finalizada com sucesso");
    }

    public void validaSePossuiAtributosNulos(PrecoFornecedorDTO preco) {

        log.info("[VALIDAÇÃO - PRECO] Inicializando validação de atributos obrigatórios nulos...");
        List<String> atributosNulos = new ArrayList<>();

        if (preco.getValor() == null) atributosNulos.add("preco");

        if (!atributosNulos.isEmpty())
            throw new InvalidRequestException("Validação do preço falhou. A inserção de um ou mais atributos " +
                    "obrigatórios é necessária no corpo da requisição: " + atributosNulos);

        log.warn("Validação de atributos nulos OK");
    }

}
