package br.com.twobrothers.msvendas.validations;

import br.com.twobrothers.msvendas.exceptions.InvalidRequestException;
import br.com.twobrothers.msvendas.models.dto.EntradaOrdemDTO;
import br.com.twobrothers.msvendas.models.dto.OrdemDTO;

public class OrdemValidation {

    EntradaOrdemValidation entradaOrdemValidation = new EntradaOrdemValidation();

    public boolean validaCorpoRequisicao(OrdemDTO ordem) {
        validaSePossuiAtributosNulos(ordem);
        validaAtributosEntradaOrdem(ordem);
        return true;
    }

    public void validaSePossuiAtributosNulos(OrdemDTO ordem) {
        if (ordem.getEmiteNfe() == null
                || ordem.getLoja() == null
                || ordem.getRetirada() == null
                || ordem.getPagamentos() == null
                || ordem.getEntradas().isEmpty())
            throw new InvalidRequestException("Requisição inválida. Um ou mais atributos obrigatórios são nulos.");
    }

    public void validaAtributosEntradaOrdem(OrdemDTO ordem) {
        for (EntradaOrdemDTO entradaOrdem : ordem.getEntradas()) {
            entradaOrdemValidation.validaCorpoRequisicao(entradaOrdem);
        }
    }

}
