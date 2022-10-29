package br.com.twobrothers.frontend.validations;

import br.com.twobrothers.frontend.models.dto.ClienteDTO;
import br.com.twobrothers.frontend.models.dto.UsuarioDTO;
import br.com.twobrothers.frontend.models.enums.ValidationType;
import br.com.twobrothers.frontend.repositories.ClienteRepository;
import br.com.twobrothers.frontend.repositories.UsuarioRepository;
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
public class UsuarioValidation {

    public void validaCorpoRequisicao(UsuarioDTO usuario, UsuarioRepository repository, ValidationType type) {
        validaSePossuiAtributosNulos(usuario, type);
        if (usuario.getCpfCnpj() != null && type.equals(ValidationType.CREATE)) {
            validaSeCpfCnpjJaExiste(usuario.getCpfCnpj(), repository);
        }
        if (usuario.getEmail() != null && type.equals(ValidationType.CREATE)) {
            validaSeEmailJaExiste(usuario.getEmail(), repository);
        }
        if (usuario.getNomeUsuario() != null && type.equals(ValidationType.CREATE)) {
            validaSeUsernameJaExiste(usuario.getNomeUsuario(), repository);
        }
        log.warn("[VALIDAÇÃO - USUARIO] Validação do objeto usuario finalizada com sucesso");
    }
    public void validaSePossuiAtributosNulos(UsuarioDTO usuario, ValidationType type) {

        log.info("[VALIDAÇÃO - USUARIO] Inicializando validação de atributos obrigatórios nulos...");
        List<String> atributosNulos = new ArrayList<>();

        if (usuario.getNome() == null) atributosNulos.add("nomeCompleto");
        if (usuario.getNomeUsuario() == null) atributosNulos.add("nomeUsuario");
        if (usuario.getSenha() == null) atributosNulos.add("senha");
        if (type.equals(ValidationType.CREATE) && usuario.getUsuarioResponsavel() == null) atributosNulos.add("usuarioResponsavel");

        if (!atributosNulos.isEmpty())
            throw new InvalidRequestException("Validação do usuario falhou. A inserção de um ou mais atributos " +
                    "obrigatórios é necessária no corpo da requisição: " + atributosNulos);

        log.warn("Validação de atributos nulos OK");
    }
    public void validaSeCpfCnpjJaExiste(String cpfCnpj, UsuarioRepository repository) {
        log.info("[VALIDAÇÃO - USUARIO] Inicializando validação de existência do campo CPF/CNPJ...");
        if (repository.buscaPorCpfCnpj(cpfCnpj).isPresent())
            throw new InvalidRequestException("O Cpf ou Cnpj digitado já existe");
        log.warn("Validação de duplicidade de CPF/CNPJ OK");
    }
    public void validaSeEmailJaExiste(String email, UsuarioRepository repository) {
        log.info("[VALIDAÇÃO - USUARIO] Inicializando validação de existência do campo EMAIL...");
        if (repository.buscaPorEmail(email).isPresent())
            throw new InvalidRequestException("O email digitado já existe");
        log.warn("Validação de duplicidade de e-mail OK");
    }
    public void validaSeUsernameJaExiste(String username, UsuarioRepository repository) {
        log.info("[VALIDAÇÃO - USUARIO] Inicializando validação de existência do campo usuário...");
        if (repository.buscaPorEmail(username).isPresent())
            throw new InvalidRequestException("O username digitado já existe");
        log.warn("Validação de duplicidade de username OK");
    }

    public void validaRangeData(String inicio, String fim) {

        LocalDate dataInicio = LocalDate.parse(inicio);
        LocalDate dataFim = LocalDate.parse(fim);

        if (dataInicio.isAfter(dataFim))
            throw new InvalidRequestException("O conteúdo do campo data início não pode ser anterior ao campo data fim");

    }
}
