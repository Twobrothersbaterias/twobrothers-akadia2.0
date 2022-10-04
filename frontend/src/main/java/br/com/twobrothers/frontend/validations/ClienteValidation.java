package br.com.twobrothers.frontend.validations;

import br.com.twobrothers.frontend.models.dto.ClienteDTO;
import br.com.twobrothers.frontend.models.enums.ValidationType;
import br.com.twobrothers.frontend.repositories.ClienteRepository;
import br.com.twobrothers.frontend.repositories.services.exceptions.InvalidRequestException;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
        validaSePossuiAtributosNulos(cliente, type);
        validaAtributoNomeCompleto(cliente.getNomeCompleto());
        if (cliente.getCpfCnpj() != null && type.equals(ValidationType.CREATE)) {
            validaSeCpfCnpjJaExiste(cliente.getCpfCnpj(), repository);
        }
        if (cliente.getEmail() != null && type.equals(ValidationType.CREATE)) {
            validaSeEmailJaExiste(cliente.getEmail(), repository);
        }
        log.warn("[VALIDAÇÃO - CLIENTE] Validação do objeto cliente finalizada com sucesso");
    }
    public void validaSePossuiAtributosNulos(ClienteDTO cliente, ValidationType type) {

        log.info("[VALIDAÇÃO - CLIENTE] Inicializando validação de atributos obrigatórios nulos...");
        List<String> atributosNulos = new ArrayList<>();

        if (cliente.getNomeCompleto() == null) atributosNulos.add("nomeCompleto");
        if (type.equals(ValidationType.CREATE) && cliente.getUsuarioResponsavel() == null) atributosNulos.add("usuarioResponsavel");

        if (!atributosNulos.isEmpty())
            throw new InvalidRequestException("Validação do cliente falhou. A inserção de um ou mais atributos " +
                    "obrigatórios é necessária no corpo da requisição: " + atributosNulos);

        log.warn("Validação de atributos nulos OK");
    }

    public void validaSeCpfCnpjJaExiste(String cpfCnpj, ClienteRepository repository) {
        log.info("[VALIDAÇÃO - CLIENTE] Inicializando validação de existência do campo CPF/CNPJ...");
        if (repository.buscaPorCpfCnpj(cpfCnpj).isPresent())
            throw new InvalidRequestException("O Cpf ou Cnpj digitado já existe");
        log.warn("Validação de duplicidade de CPF/CNPJ OK");
    }
    public void validaSeEmailJaExiste(String email, ClienteRepository repository) {
        log.info("[VALIDAÇÃO - CLIENTE] Inicializando validação de existência do campo EMAIL...");
        if (repository.buscaPorEmail(email).isPresent())
            throw new InvalidRequestException("O email digitado já existe");
        log.warn("Validação de duplicidade de e-mail OK");
    }

    public void validaAtributoNomeCompleto(String nomeCompleto) {
        log.info("[VALIDAÇÃO - CLIENTE] Inicializando validação do atributo nomeCompleto...");
        if (nomeCompleto.length() > 70)
            throw new InvalidRequestException("Validação do cliente falhou. O nome completo deve conter menos de 70 caracteres");
        log.warn("Validação do atributo nomeCompleto OK");
    }

    public void validaRangeData(String inicio, String fim) {

        LocalDate dataInicio = LocalDate.parse(inicio);
        LocalDate dataFim = LocalDate.parse(fim);

        if (dataInicio.isAfter(dataFim))
            throw new InvalidRequestException("O conteúdo do campo data início não pode ser anterior ao campo data fim");

    }
}
