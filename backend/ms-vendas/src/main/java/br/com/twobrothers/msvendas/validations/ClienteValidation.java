package br.com.twobrothers.msvendas.validations;

import br.com.twobrothers.msvendas.exceptions.InvalidRequestException;
import br.com.twobrothers.msvendas.models.dto.ClienteDTO;
import br.com.twobrothers.msvendas.models.enums.ValidationType;
import br.com.twobrothers.msvendas.repositories.ClienteRepository;

import static br.com.twobrothers.msvendas.utils.RegexPatterns.*;

public class ClienteValidation {

    EnderecoValidation enderecoValidation = new EnderecoValidation();

    public boolean validaCorpoRequisicao(ClienteDTO cliente, ClienteRepository repository, ValidationType type) {
        if (cliente.getDataNascimento() != null) validaAtributoDataNascimento(cliente.getDataNascimento());
        if (cliente.getNomeCompleto() != null) validaAtributoNomeCompleto(cliente.getNomeCompleto());
        if (cliente.getCpfCnpj() != null) validaAtributoCpfCnpj(cliente.getCpfCnpj(), repository, type);
        if (cliente.getEmail() != null) validaAtributoEmail(cliente.getEmail(), repository, type);
        if (cliente.getTelefone() != null) validaAtributoTelefone(cliente.getTelefone());
        if (cliente.getIdUsuarioResponsavel() == null)
            throw new InvalidRequestException("O id do usuário responsável pela " +
                    "requisição não pode ser nulo");
        return true;
    }

    public boolean validaAtributoDataNascimento(String dataNascimento) { //TODO Testar REGEX
        if (dataNascimento.matches(DATE_REGEX)) return true;
        throw new InvalidRequestException("Validação do cliente falhou. O padrão da data de nascimento enviada é inválido.");
    }

    public boolean validaAtributoNomeCompleto(String nomeCompleto) {
        if (nomeCompleto.length() <= 70) return true;
        throw new InvalidRequestException("Validação do cliente falhou. O nome completo deve conter menos de 70 caracteres");
    }

    public boolean validaAtributoCpfCnpj(String cpfCnpj, ClienteRepository repository, ValidationType type) { //TODO Testar REGEX

        cpfCnpj = cpfCnpj.replace(".", "").replace("-", "");

        if (cpfCnpj.length() == 11 && cpfCnpj.matches(CPF_REGEX_PATTERN_REPLACED) ||
                cpfCnpj.length() == 14 && cpfCnpj.matches(CNPJ_REGEX_PATTERN_REPLACED)) {
            if (type.equals(ValidationType.CREATE) && repository.buscaPorCpfCnpj(cpfCnpj).isEmpty() || type.equals(ValidationType.UPDATE)) {
                return true;
            } else {
                throw new InvalidRequestException("O cpf/cnpj enviado já existe em um cadastro de nossa base de dados.");
            }
        } else {
            throw new InvalidRequestException("Validação do cpf/cnpj falhou. O valor enviado é inválido.");
        }
    }

    public boolean validaAtributoEmail(String email, ClienteRepository repository, ValidationType type) {
        if (email.matches(EMAIL_REGEX_PATTERN)) {
            if (type.equals(ValidationType.CREATE) && repository.buscaPorEmail(email).isEmpty() || type.equals(ValidationType.UPDATE)) {
                return true;
            } else {
                throw new InvalidRequestException("O e-mail enviado já existe em um cadastro de nossa base de dados.");
            }
        } else {
            throw new InvalidRequestException("Validação do e-mail falhou. O valor enviado é inválido.");
        }
    }

    public boolean validaAtributoTelefone(String telefone) {
        if (telefone.replace("(", "").replace(")", "").replace("-", "")
                .matches(PHONE_REGEX_PATTERN_REPLACED)) return true; //TODO Testar REGEX
        throw new InvalidRequestException("Validação do telefone falhou. O valor enviado é inválido.");
    }
}
