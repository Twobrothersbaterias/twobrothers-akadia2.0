package br.com.twobrothers.msvendas.validations;

import br.com.twobrothers.msvendas.exceptions.InvalidRequestException;
import br.com.twobrothers.msvendas.models.dto.OrdemDTO;
import br.com.twobrothers.msvendas.models.enums.ValidationType;
import br.com.twobrothers.msvendas.repositories.OrdemRepository;

public class OrdemValidation {

    public boolean validaCorpoRequisicao(OrdemDTO ordem, OrdemRepository repository, ValidationType validationType) {
        validaSePossuiAtributosNulos(ordem);
        return true;
    }

    public boolean validaSePossuiAtributosNulos(OrdemDTO ordem) {
        if (ordem.getEmiteNfe() != null
                && ordem.getFormaPagamento() != null
                && ordem.getLoja() != null
                && ordem.getRetirada() != null
                && !ordem.getEntradas().isEmpty()) return true;
        throw new InvalidRequestException("Requisição inválida. Um ou mais atributos obrigatórios são nulos.");
    }


}
