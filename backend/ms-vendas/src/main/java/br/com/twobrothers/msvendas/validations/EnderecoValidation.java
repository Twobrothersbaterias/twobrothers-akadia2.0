package br.com.twobrothers.msvendas.validations;

import br.com.twobrothers.msvendas.exceptions.InvalidRequestException;
import br.com.twobrothers.msvendas.models.dto.EnderecoDTO;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

import static br.com.twobrothers.msvendas.utils.RegexPatterns.POSTAL_CODE_REGEX_PATTERN;
import static br.com.twobrothers.msvendas.utils.RegexPatterns.STREET_NUMBER_REGEX_PATTERN;

@Slf4j
public class EnderecoValidation {

    public void validaCorpoRequisicao(EnderecoDTO endereco) {
        validaSeNaoNulo(endereco);
        validaLogradouro(endereco.getLogradouro());
        validaNumero(endereco.getNumero());
        if (endereco.getBairro() != null) validaBairro(endereco.getBairro());
        if (endereco.getCep() != null) validaCep(endereco.getCep());
        if (endereco.getComplemento() != null) validaComplemento(endereco.getComplemento());
        log.warn("[VALIDAÇÃO - ENDEREÇO] Validação do objeto abastecimento finalizada com sucesso");
    }

    public void validaSeNaoNulo(EnderecoDTO endereco) {

        log.info("[VALIDAÇÃO - ENDEREÇO] Inicializando validação de atributos obrigatórios nulos...");
        List<String> atributosNulos = new ArrayList<>();

        if (endereco.getLogradouro() == null) atributosNulos.add("logradouro");
        if (endereco.getNumero() == null) atributosNulos.add("numero");

        if (!atributosNulos.isEmpty())
            throw new InvalidRequestException("Validação do endereço falhou. A inserção de um ou mais atributos " +
                    "obrigatórios é necessária no corpo da requisição: " + atributosNulos);

        log.warn("Validação de atributos nulos OK");
    }

    public void validaLogradouro(String logradouro) {
        log.info("[VALIDAÇÃO - ENDEREÇO] Inicializando validação do atributo logradouro...");
        if (logradouro.length() > 60)
            throw new InvalidRequestException("Requisição inválida. O campo logradouro deve ter o tamanho menor do que 60 caracteres");
        log.warn("Validação do atributo logradouro OK");
    }

    public void validaNumero(Integer numero) {
        log.info("[VALIDAÇÃO - ENDEREÇO] Inicializando validação do atributo numero...");
        if (!numero.toString().matches(STREET_NUMBER_REGEX_PATTERN))
            throw new InvalidRequestException("Requisição inválida. O campo número deve possuir de 1 a 5 caracteres.");
        log.warn("Validação do atributo numero OK");
    }

    public void validaBairro(String bairro) {
        log.info("[VALIDAÇÃO - ENDEREÇO] Inicializando validação do atributo bairro...");
        if (bairro.length() > 60)
            throw new InvalidRequestException("Requisição inválida. O campo bairro deve ter o tamanho menor do que 60 caracteres");
        log.warn("Validação do atributo bairro OK");
    }

    public void validaCep(String cep) {
        log.info("[VALIDAÇÃO - ENDEREÇO] Inicializando validação do atributo cep...");
        if (!cep.matches(POSTAL_CODE_REGEX_PATTERN))
            throw new InvalidRequestException("Requisição inválida. O campo cep foi enviado com padrão incorreto");
        log.warn("Validação do atributo cep OK");
    }

    public void validaComplemento(String complemento) {
        log.info("[VALIDAÇÃO - ENDEREÇO] Inicializando validação do atributo complemento...");
        if (complemento.length() > 60)
            throw new InvalidRequestException("Requisição inválida. O campo complemento deve ter o tamanho menor do que 60 caracteres");
        log.warn("Validação do atributo complemento OK");
    }

}
