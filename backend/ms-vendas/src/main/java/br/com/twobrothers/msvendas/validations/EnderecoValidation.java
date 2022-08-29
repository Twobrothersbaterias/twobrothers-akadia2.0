package br.com.twobrothers.msvendas.validations;

import br.com.twobrothers.msvendas.exceptions.InvalidRequestException;
import br.com.twobrothers.msvendas.models.dto.EnderecoDTO;

import static br.com.twobrothers.msvendas.utils.RegexPatterns.POSTAL_CODE_REGEX_PATTERN;
import static br.com.twobrothers.msvendas.utils.RegexPatterns.STREET_NUMBER_REGEX_PATTERN;

public class EnderecoValidation {

    public void validaCorpoRequisicao(EnderecoDTO endereco) {
        validaSeNaoNulo(endereco);
        validaLogradouro(endereco.getLogradouro());
        validaNumero(endereco.getNumero());
        validaBairro(endereco.getBairro());
        validaCep(endereco.getCep());
        validaComplemento(endereco.getComplemento());
    }

    public void validaSeNaoNulo(EnderecoDTO endereco) {
        if (endereco.getLogradouro() == null
                && endereco.getNumero() == null)
            throw new InvalidRequestException("Requisição inválida. Um ou mais campos obrigatórios são nulos");
    }

    public void validaLogradouro(String logradouro) {
        if (logradouro.length() > 60)
            throw new InvalidRequestException("Requisição inválida. O campo logradouro deve ter o tamanho menor do que 60 caracteres");
    }

    public void validaNumero(Integer numero) {
        if (!numero.toString().matches(STREET_NUMBER_REGEX_PATTERN))
            throw new InvalidRequestException("Requisição inválida. O campo número deve possuir de 1 a 5 caracteres.");
    }

    public void validaBairro(String bairro) {
        if (bairro.length() > 60)
            throw new InvalidRequestException("Requisição inválida. O campo bairro deve ter o tamanho menor do que 60 caracteres");
    }

    public void validaCep(String cep) {
        if (!cep.matches(POSTAL_CODE_REGEX_PATTERN))
            throw new InvalidRequestException("Requisição inválida. O campo cep foi enviado com padrão incorreto");
    }

    public void validaComplemento(String complemento) {
        if (complemento.length() > 60)
            throw new InvalidRequestException("Requisição inválida. O campo complemento deve ter o tamanho menor do que 60 caracteres");
    }

}
