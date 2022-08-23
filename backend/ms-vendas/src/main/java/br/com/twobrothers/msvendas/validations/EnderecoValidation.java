package br.com.twobrothers.msvendas.validations;

import br.com.twobrothers.msvendas.exceptions.InvalidRequestException;
import br.com.twobrothers.msvendas.models.dto.EnderecoDTO;
import br.com.twobrothers.msvendas.models.enums.ValidationType;
import br.com.twobrothers.msvendas.repositories.EnderecoRepository;

import static br.com.twobrothers.msvendas.utils.RegexPatterns.POSTAL_CODE_REGEX_PATTERN;
import static br.com.twobrothers.msvendas.utils.RegexPatterns.STREET_NUMBER_REGEX_PATTERN;

public class EnderecoValidation {

    public boolean validaCorpoRequisicao(EnderecoDTO endereco, EnderecoRepository repository, ValidationType validationType) {
        validaSeNaoNulo(endereco);
        if (validationType.equals(ValidationType.CREATE)) validaSeJaExiste(endereco, repository);
        validaLogradouro(endereco.getLogradouro());
        validaNumero(endereco.getNumero());
        validaBairro(endereco.getBairro());
        validaCep(endereco.getCep());
        validaComplemento(endereco.getComplemento());
        return true;
    }

    public boolean validaSeNaoNulo(EnderecoDTO endereco) {
        if (endereco.getLogradouro() != null
                && endereco.getNumero() != null) return true;
        throw new InvalidRequestException("Requisição inválida. Um ou mais campos obrigatórios são nulos");
    }

    public boolean validaSeJaExiste(EnderecoDTO endereco, EnderecoRepository repository) {
        if (repository.buscaPorAtributos(endereco.getLogradouro(), endereco.getBairro(), endereco.getNumero()).isEmpty()) return true;
        throw new InvalidRequestException("Requisição inválida. O endereço informado já existe");
    }

    public boolean validaLogradouro(String logradouro) {
        if (logradouro.length() <= 60) return true;
        throw new InvalidRequestException("Requisição inválida. O campo logradouro deve ter o tamanho menor do que 60 caracteres");
    }

    public boolean validaNumero(Integer numero) {
        if (numero.toString().matches(STREET_NUMBER_REGEX_PATTERN)) return true;
        throw new InvalidRequestException("Requisição inválida. O campo número deve possuir de 1 a 5 caracteres.");
    }

    public boolean validaBairro(String bairro) {
        if (bairro.length() <= 60) return true;
        throw new InvalidRequestException("Requisição inválida. O campo bairro deve ter o tamanho menor do que 60 caracteres");
    }

    public boolean validaCep(String cep) {
        if (cep.matches(POSTAL_CODE_REGEX_PATTERN)) return true;
        throw new InvalidRequestException("Requisição inválida. O campo cep foi enviado com padrão incorreto");
    }

    public boolean validaComplemento(String complemento) {
        if (complemento.length() <= 60) return true;
        throw new InvalidRequestException("Requisição inválida. O campo complemento deve ter o tamanho menor do que 60 caracteres");
    }

}
