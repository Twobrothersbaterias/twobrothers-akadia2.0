package br.com.twobrothers.msvendas.validations;

import br.com.twobrothers.msvendas.exceptions.InvalidRequestException;
import br.com.twobrothers.msvendas.models.dto.AbastecimentoDTO;

import static br.com.twobrothers.msvendas.utils.RegexPatterns.QUANTIDADE_REGEX;

public class AbastecimentoValidation {

    public boolean validaCorpoRequisicao(AbastecimentoDTO abastecimento) {
        validaSePossuiAtributosNulos(abastecimento);
        validaAtributoQuantidade(abastecimento.getQuantidade());
        validaAtributoCustoTotal(abastecimento.getCustoTotal());
        if (abastecimento.getObservacao() != null) validaAtributoObservacao(abastecimento.getObservacao());

        return true;
    }

    public boolean validaSePossuiAtributosNulos(AbastecimentoDTO abastecimento) {
        if (abastecimento.getQuantidade() != null &&
                abastecimento.getCustoTotal() != null &&
                abastecimento.getIdUsuarioResponsavel() != null &&
                abastecimento.getFormaPagamento() != null) return true;
        throw new InvalidRequestException("Validação do abastecimento falhou. Motivo: um ou mais atributos recebido(s) " +
                "na requisição são nulos");
    }

    public boolean validaAtributoQuantidade(Integer quantidade) {
        if (quantidade.toString().matches(QUANTIDADE_REGEX)) return true;
        throw new InvalidRequestException("Validação do abastecimento falhou. Motivo: A quantidade deve estar entre 1 e 999");
    }

    public boolean validaAtributoCustoTotal(Double custoTotal) {
        if (custoTotal > 0.0 && custoTotal < 999999.0) return true;
        throw new InvalidRequestException("Validação do abastecimento falhou. Motivo: O custo total deve deve estar entre R$ 1,00 e R$ 999.999,00");
    }

    public boolean validaAtributoObservacao(String observacao) {
        if (observacao.length() < 100) return true;
        throw new InvalidRequestException("Validação do abastecimento falhou. Motivo: O campo observação deve possuir " +
                "até 100 caracteres. Quantidade atual: " + observacao.length());
    }

}
