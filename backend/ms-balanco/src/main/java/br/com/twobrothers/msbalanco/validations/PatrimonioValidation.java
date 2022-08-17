package br.com.twobrothers.msbalanco.validations;

import br.com.twobrothers.msbalanco.models.dto.PatrimonioDTO;
import br.com.twobrothers.msbalanco.services.exceptions.InvalidRequestException;

public class PatrimonioValidation {

    //TODO FINALIZAR CLASSE

    public boolean validaCorpoDaRequisicao(PatrimonioDTO patrimonio) {
        validaSePossuiAtributosNulos(patrimonio);

        return true;
    }

    public boolean validaSePossuiAtributosNulos(PatrimonioDTO patrimonio) {
        if (patrimonio.getValorPatrimonio() != null) return true;
        throw new InvalidRequestException("Validação da despesa falhou. Motivo: um ou mais atributos recebido(s) " +
                "na requisição são nulos");
    }
}
