package br.com.twobrothers.frontend.validations;

import br.com.twobrothers.frontend.models.dto.ClienteDTO;
import br.com.twobrothers.frontend.models.enums.ValidationType;
import br.com.twobrothers.frontend.repositories.ClienteRepository;
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
public class ClienteValidation {

    public void validaCorpoRequisicao(ClienteDTO cliente, ClienteRepository repository, ValidationType type) {
        validaSePossuiAtributosNulos(cliente);
        validaAtributoNomeCompleto(cliente.getNomeCompleto());
        if (cliente.getDataNascimento() != null) validaAtributoDataNascimento(cliente.getDataNascimento());
        if (cliente.getCpfCnpj() != null) validaAtributoCpfCnpj(cliente.getCpfCnpj(), repository, type);
        if (cliente.getEmail() != null) validaAtributoEmail(cliente.getEmail(), repository, type);
        if (cliente.getTelefone() != null) validaAtributoTelefone(cliente.getTelefone());
        log.warn("[VALIDAÇÃO - CLIENTE] Validação do objeto cliente finalizada com sucesso");
    }

    public void validaSePossuiAtributosNulos(ClienteDTO cliente) {

        log.info("[VALIDAÇÃO - CLIENTE] Inicializando validação de atributos obrigatórios nulos...");
        List<String> atributosNulos = new ArrayList<>();

        if (cliente.getNomeCompleto() == null) atributosNulos.add("nomeCompleto");
        if (cliente.getIdUsuarioResponsavel() == null) atributosNulos.add("idUsuarioResponsavel");

        if (!atributosNulos.isEmpty())
            throw new InvalidRequestException("Validação do cliente falhou. A inserção de um ou mais atributos " +
                    "obrigatórios é necessária no corpo da requisição: " + atributosNulos);

        log.warn("Validação de atributos nulos OK");
    }

    public void validaAtributoDataNascimento(String dataNascimento) {
        log.info("[VALIDAÇÃO - CLIENTE] Inicializando validação do atributo dataNascimento...");
        if (!dataNascimento.matches(DATE_REGEX))
            throw new InvalidRequestException("Validação do cliente falhou. O padrão da data de nascimento enviada é inválido.");
        log.warn("Validação do atributo dataNascimento OK");
    }

    public void validaAtributoNomeCompleto(String nomeCompleto) {
        log.info("[VALIDAÇÃO - CLIENTE] Inicializando validação do atributo nomeCompleto...");
        if (nomeCompleto.length() > 70)
            throw new InvalidRequestException("Validação do cliente falhou. O nome completo deve conter menos de 70 caracteres");
        log.warn("Validação do atributo nomeCompleto OK");
    }

    public void validaAtributoCpfCnpj(String cpfCnpj, ClienteRepository repository, ValidationType type) {
        log.info("[VALIDAÇÃO - CLIENTE] Inicializando validação do atributo cpfCnpj...");
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

    public void validaAtributoEmail(String email, ClienteRepository repository, ValidationType type) {
        log.info("[VALIDAÇÃO - CLIENTE] Inicializando validação do atributo email...");
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
        log.info("[VALIDAÇÃO - CLIENTE] Inicializando validação do atributo telefone...");
        if (!telefone.matches(PHONE_REGEX_PATTERN))
            throw new InvalidRequestException("Validação do telefone falhou. O valor enviado é inválido.");
        log.info("Validação do atributo email OK");
    }

    public void validaRangeData(String inicio, String fim) {

        LocalDate dataInicio = LocalDate.parse(inicio);
        LocalDate dataFim = LocalDate.parse(fim);

        if (dataInicio.isAfter(dataFim))
            throw new InvalidRequestException("O conteúdo do campo data início não pode ser anterior ao campo data fim");

    }
}
