package br.com.twobrothers.msvendas.validations;

import br.com.twobrothers.msvendas.exceptions.InvalidRequestException;
import br.com.twobrothers.msvendas.models.dto.PrecoFornecedorDTO;

public class PrecoFornecedorValidation {

    public boolean validaCorpoRequisicao(PrecoFornecedorDTO preco) {
        validaSePossuiAtributosNulos(preco);
        return true;
    }

    public boolean validaSePossuiAtributosNulos(PrecoFornecedorDTO preco) {
        if (preco.getValor() != null && preco.getIdUsuarioResponsavel() != null) return true;
        throw new InvalidRequestException("Requisição inválida. Um ou mais atributos obrigatórios são nulos.");
    }

}
