package br.com.twobrothers.frontend.validations;

import br.com.twobrothers.frontend.models.dto.FornecedorDTO;
import br.com.twobrothers.frontend.models.enums.ValidationType;
import br.com.twobrothers.frontend.repositories.FornecedorRepository;
import br.com.twobrothers.frontend.repositories.services.exceptions.InvalidRequestException;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static br.com.twobrothers.frontend.utils.RegexPatterns.*;

/**
 * @author Gabriel Lagrota
 * @email gabriellagrota23@gmail.com
 * @phone (11)97981-5415
 * @github https://github.com/LagrotaGabriel
 * @version 1.0
 * @since 30-08-22
 */
@Slf4j
public class FornecedorValidation {

    public void validaCorpoRequisicao(FornecedorDTO fornecedor, FornecedorRepository repository, ValidationType validationType) {
        validaSePossuiAtributosNulos(fornecedor);
        validaAtributoNome(fornecedor.getNome());
//        validaAtributoTelefone(fornecedor.getTelefone());
//        if (fornecedor.getCpfCnpj() != null) validaAtributoCpfCnpj(fornecedor.getCpfCnpj(), repository, validationType);
//        if (fornecedor.getEmail() != null) validaAtributoEmail(fornecedor.getEmail(), repository, validationType);
    }

    public void validaSePossuiAtributosNulos(FornecedorDTO fornecedor) {

        log.info("[VALIDAÇÃO - FORNECEDOR] Inicializando validação de atributos obrigatórios nulos...");
        List<String> atributosNulos = new ArrayList<>();

        if (fornecedor.getNome() == null) atributosNulos.add("nome");
        if (fornecedor.getTelefone() == null) atributosNulos.add("telefone");
        if (fornecedor.getUsuarioResponsavel() == null) atributosNulos.add("usuarioResponsavel");

        if (!atributosNulos.isEmpty())
            throw new InvalidRequestException("Validação do fornecedor falhou. A inserção de um ou mais atributos " +
                    "obrigatórios é necessária no corpo da requisição: " + atributosNulos);

        log.warn("Validação de atributos nulos OK");

    }

    public void validaAtributoNome(String nome) {
        log.info("[VALIDAÇÃO - FORNECEDOR] Inicializando validação do atributo nome...");
        if (nome.length() > 60)
            throw new InvalidRequestException("Requisição inválida. O nome do fornecedor deve possuir menos de 60 caracteres.");
        log.warn("Validação do atributo nome OK");
    }

    public void validaAtributoCpfCnpj(String cpfCnpj, FornecedorRepository repository, ValidationType type) {
        log.info("[VALIDAÇÃO - FORNECEDOR] Inicializando validação do atributo cpfCnpj...");
        if (cpfCnpj.length() == 14 && cpfCnpj.matches(CPF_REGEX_PATTERN)
                || cpfCnpj.length() == 18 && cpfCnpj.matches(CNPJ_REGEX_PATTERN)) {

            if (type.equals(ValidationType.CREATE) && repository.buscaPorCpfCnpj(cpfCnpj).isEmpty() || type.equals(ValidationType.UPDATE)) {
                log.info("Validação do atributo cpfCnpj OK");
            } else {
                throw new InvalidRequestException("O cpf/cnpj enviado já existe em um cadastro de nossa base de dados.");
            }
        } else {
            throw new InvalidRequestException("Validação do cpf/cnpj falhou. O valor enviado é inválido.");
        }
    }

    public void validaAtributoEmail(String email, FornecedorRepository repository, ValidationType type) {
        log.info("[VALIDAÇÃO - FORNECEDOR] Inicializando validação do atributo email...");
        if (email.matches(EMAIL_REGEX_PATTERN)) {
            if (type.equals(ValidationType.CREATE) && repository.buscaPorEmail(email).isEmpty() || type.equals(ValidationType.UPDATE)) {
                log.info("Validação do atributo email OK");
            } else {
                throw new InvalidRequestException("O e-mail enviado já existe em um cadastro de nossa base de dados.");
            }
        } else {
            throw new InvalidRequestException("Validação do e-mail falhou. O valor enviado é inválido.");
        }
    }

    public void validaAtributoTelefone(String telefone) {
        log.info("[VALIDAÇÃO - FORNECEDOR] Inicializando validação do atributo nome...");
        if (!telefone.matches(PHONE_REGEX_PATTERN))
            throw new InvalidRequestException("Validação do telefone falhou. O valor enviado é inválido.");
        log.warn("Validação do atributo nome OK");
    }

    public void validaRangeData(String inicio, String fim) {

        LocalDate dataInicio = LocalDate.parse(inicio);
        LocalDate dataFim = LocalDate.parse(fim);

        if (dataInicio.isAfter(dataFim))
            throw new InvalidRequestException("O conteúdo do campo data início não pode ser anterior ao campo data fim");

    }

}
