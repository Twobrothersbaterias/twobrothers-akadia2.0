package br.com.twobrothers.msvendas.validations;

import br.com.twobrothers.msvendas.exceptions.InvalidRequestException;
import br.com.twobrothers.msvendas.models.dto.EntradaOrdemDTO;

import static br.com.twobrothers.msvendas.utils.RegexPatterns.QUANTIDADE_REGEX;

public class EntradaOrdemValidation {

    public boolean validaCorpoRequisicao(EntradaOrdemDTO entradaOrdemDTO) {
        validaSePossuiAtributosNulos(entradaOrdemDTO);
        validaAtributoQuantidade(entradaOrdemDTO.getQuantidade());
        validaAtributoValor(entradaOrdemDTO.getValor());
        return true;
    }

    public boolean validaSePossuiAtributosNulos(EntradaOrdemDTO entradaOrdemDTO) {
        if (entradaOrdemDTO.getTipoEntrada() != null
                && entradaOrdemDTO.getTipoOrdem() != null
                && entradaOrdemDTO.getQuantidade() != null
                && entradaOrdemDTO.getValor() != null) return true;
        throw new InvalidRequestException("Requisição inválida. Um ou mais atributos obrigatórios são nulos.");
    }

    public boolean validaAtributoQuantidade(Integer quantidade) {
        if (quantidade.toString().matches(QUANTIDADE_REGEX)) return true;
        throw new InvalidRequestException("Atributo quantidade passado na entradaOrdem inválido");
    }

    public boolean validaAtributoValor(Double valor) {
        if (valor > 0.0 && valor < 99999.0) return true;
        throw new InvalidRequestException("Atributo valor inválido. O valor deve estar entre 0.0 e 9999.0");
    }

}
