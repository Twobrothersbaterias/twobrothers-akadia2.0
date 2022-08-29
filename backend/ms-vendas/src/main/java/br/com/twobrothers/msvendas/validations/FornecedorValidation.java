package br.com.twobrothers.msvendas.validations;

import br.com.twobrothers.msvendas.exceptions.InvalidRequestException;
import br.com.twobrothers.msvendas.models.dto.FornecedorDTO;
import br.com.twobrothers.msvendas.models.enums.ValidationType;
import br.com.twobrothers.msvendas.repositories.FornecedorRepository;

import static br.com.twobrothers.msvendas.utils.RegexPatterns.*;

public class FornecedorValidation {

    public boolean validaCorpoRequisicao(FornecedorDTO fornecedor, FornecedorRepository repository, ValidationType validationType) {
        validaSePossuiAtributosNulos(fornecedor);
        validaAtributoNome(fornecedor.getNome());
        validaAtributoTelefone(fornecedor.getTelefone());
        if (fornecedor.getCpfCnpj() != null) validaAtributoCpfCnpj(fornecedor.getCpfCnpj(), repository, validationType);
        if (fornecedor.getEmail() != null) validaAtributoEmail(fornecedor.getEmail(), repository, validationType);
        return true;
    }

    public boolean validaSePossuiAtributosNulos(FornecedorDTO fornecedor) {
        if (fornecedor.getNome() != null
                && fornecedor.getIdUsuarioResponsavel() != null
                && fornecedor.getTelefone() != null)
            return true;
        throw new InvalidRequestException("Requisição inválida. Um ou mais atributos obrigatórios são nulos.");
    }

    public boolean validaAtributoNome(String nome) {
        if (nome.length() <= 60) return true;
        throw new InvalidRequestException("Requisição inválida. O nome do fornecedor deve possuir menos de 60 caracteres.");
    }

    public boolean validaAtributoCpfCnpj(String cpfCnpj, FornecedorRepository repository, ValidationType type) {

        if (cpfCnpj.length() == 14 && cpfCnpj.matches(CPF_REGEX_PATTERN)
                || cpfCnpj.length() == 18 && cpfCnpj.matches(CNPJ_REGEX_PATTERN)) {

            if (type.equals(ValidationType.CREATE) && repository.buscaPorCpfCnpj(cpfCnpj).isEmpty() || type.equals(ValidationType.UPDATE)) {
                return true;
            } else {
                throw new InvalidRequestException("O cpf/cnpj enviado já existe em um cadastro de nossa base de dados.");
            }
        } else {
            throw new InvalidRequestException("Validação do cpf/cnpj falhou. O valor enviado é inválido.");
        }
    }

    public boolean validaAtributoEmail(String email, FornecedorRepository repository, ValidationType type) {
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
        if (telefone.matches(PHONE_REGEX_PATTERN)) return true;
        throw new InvalidRequestException("Validação do telefone falhou. O valor enviado é inválido.");
    }

}
