package br.com.twobrothers.frontend.validations;

import br.com.twobrothers.frontend.models.dto.FornecedorDTO;
import br.com.twobrothers.frontend.models.enums.ValidationType;
import br.com.twobrothers.frontend.repositories.ClienteRepository;
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

    public void validaCorpoRequisicao(FornecedorDTO fornecedor, FornecedorRepository repository, ValidationType type) {
        validaSePossuiAtributosNulos(fornecedor, type);
        validaAtributoNome(fornecedor.getNome());
        if (fornecedor.getCpfCnpj() != null && type.equals(ValidationType.CREATE)) {
            validaSeCpfCnpjJaExiste(fornecedor.getCpfCnpj(), repository);
        }
        if (fornecedor.getEmail() != null && type.equals(ValidationType.CREATE)) {
            validaSeEmailJaExiste(fornecedor.getEmail(), repository);
        }
        log.warn("[VALIDAÇÃO - FORNECEDOR] Validação do objeto fornecedor finalizada com sucesso");
    }

    public void validaSePossuiAtributosNulos(FornecedorDTO fornecedor, ValidationType type) {

        log.info("[VALIDAÇÃO - FORNECEDOR] Inicializando validação de atributos obrigatórios nulos...");
        List<String> atributosNulos = new ArrayList<>();

        if (fornecedor.getNome() == null) atributosNulos.add("nome");
        if (type.equals(ValidationType.CREATE) && fornecedor.getUsuarioResponsavel() == null) atributosNulos.add("usuarioResponsavel");

        if (!atributosNulos.isEmpty())
            throw new InvalidRequestException("Validação do fornecedor falhou. A inserção de um ou mais atributos " +
                    "obrigatórios é necessária no corpo da requisição: " + atributosNulos);

        log.warn("Validação de atributos nulos OK");

    }

    public void validaSeCpfCnpjJaExiste(String cpfCnpj, FornecedorRepository repository) {
        log.info("[VALIDAÇÃO - FORNECEDOR] Inicializando validação de existência do campo CPF/CNPJ...");
        if (repository.buscaPorCpfCnpj(cpfCnpj).isPresent())
            throw new InvalidRequestException("O Cpf ou Cnpj digitado já existe");
        log.warn("Validação de duplicidade de CPF/CNPJ OK");
    }
    public void validaSeEmailJaExiste(String email, FornecedorRepository repository) {
        log.info("[VALIDAÇÃO - FORNECEDOR] Inicializando validação de existência do campo EMAIL...");
        if (repository.buscaPorEmail(email).isPresent())
            throw new InvalidRequestException("O email digitado já existe");
        log.warn("Validação de duplicidade de e-mail OK");
    }

    public void validaAtributoNome(String nome) {
        log.info("[VALIDAÇÃO - FORNECEDOR] Inicializando validação do atributo nome...");
        if (nome.length() > 60)
            throw new InvalidRequestException("Requisição inválida. O nome do fornecedor deve possuir menos de 60 caracteres.");
        log.warn("Validação do atributo nome OK");
    }

    public void validaRangeData(String inicio, String fim) {

        LocalDate dataInicio = LocalDate.parse(inicio);
        LocalDate dataFim = LocalDate.parse(fim);

        if (dataInicio.isAfter(dataFim))
            throw new InvalidRequestException("O conteúdo do campo data início não pode ser anterior ao campo data fim");

    }

}
