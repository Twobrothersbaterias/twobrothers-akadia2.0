package br.com.twobrothers.frontend.repositories.services;

import br.com.twobrothers.frontend.config.ModelMapperConfig;
import br.com.twobrothers.frontend.models.dto.FornecedorDTO;
import br.com.twobrothers.frontend.models.entities.EnderecoEntity;
import br.com.twobrothers.frontend.models.entities.FornecedorEntity;
import br.com.twobrothers.frontend.models.enums.ValidationType;
import br.com.twobrothers.frontend.repositories.FornecedorRepository;
import br.com.twobrothers.frontend.repositories.services.exceptions.InvalidRequestException;
import br.com.twobrothers.frontend.repositories.services.exceptions.ObjectNotFoundException;
import br.com.twobrothers.frontend.validations.EnderecoValidation;
import br.com.twobrothers.frontend.validations.FornecedorValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static br.com.twobrothers.frontend.utils.StringConstants.*;

/**
 * @author Gabriel Lagrota
 * @email gabriellagrota23@gmail.com
 * @phone (11)97981-5415
 * @github https://github.com/LagrotaGabriel
 * @version 1.0
 * @since 30-08-22
 */
@Slf4j
@Service
public class FornecedorCrudService {

    @Autowired
    FornecedorRepository repository;

    @Autowired
    ModelMapperConfig modelMapper;

    FornecedorValidation validation = new FornecedorValidation();
    EnderecoValidation enderecoValidation = new EnderecoValidation();

    public FornecedorDTO criaNovo(FornecedorDTO fornecedor) {

        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de criação");

        log.info("[PROGRESS] Setando a data de cadastro no fornecedor: {}", LocalDateTime.now());
        fornecedor.setDataCadastro(LocalDateTime.now());

        validation.validaCorpoRequisicao(fornecedor, repository, ValidationType.CREATE);

        if (fornecedor.getEndereco() != null) {
            fornecedor.getEndereco().setDataCadastro(LocalDateTime.now());
            enderecoValidation.validaCorpoRequisicao(fornecedor.getEndereco());
        }

        log.info("[PROGRESS] Salvando o fornecedor na base de dados...");
        FornecedorEntity fornecedorEntity = repository.save(modelMapper.mapper().map(fornecedor, FornecedorEntity.class));

        log.info("[SUCCESS] Requisição finalizada com sucesso");
        return modelMapper.mapper().map(fornecedorEntity, FornecedorDTO.class);

    }

    public List<FornecedorDTO> buscaTodos() {
        if (!repository.findAll().isEmpty()) return repository.findAll().stream()
                .map(x -> modelMapper.mapper().map(x, FornecedorDTO.class)).collect(Collectors.toList());
        throw new ObjectNotFoundException("Não existe nenhum fornecedor salvo na base de dados.");
    }

    public List<FornecedorDTO> buscaPorPaginacao(Pageable paginacao) {
        if (!repository.findAll(paginacao).isEmpty()) return repository.findAll(paginacao)
                .getContent().stream().map(x -> modelMapper.mapper().map(x, FornecedorDTO.class)).collect(Collectors.toList());
        throw new ObjectNotFoundException("Não existe nenhum fornecedor cadastrado na página indicada");
    }

    public List<FornecedorDTO> buscaPorRangeDeDataCadastro(String dataInicio, String dataFim) {

        List<FornecedorEntity> fornecedores = repository.buscaPorRangeDeDataCadastro(
                (LocalDate.parse(dataInicio)).atTime(0, 0),
                (LocalDate.parse(dataFim)).atTime(23, 59, 59, 999999999));

        if (!fornecedores.isEmpty())
            return fornecedores.stream().map(x -> modelMapper.mapper().map(x, FornecedorDTO.class)).collect(Collectors.toList());
        throw new ObjectNotFoundException("Não existe nenhum fornecedor cadastrado no range de datas indicado");

    }

    public FornecedorDTO buscaPorId(Long id) {
        if (repository.findById(id).isPresent()) {
            return modelMapper.mapper().map(repository.findById(id).get(), FornecedorDTO.class);
        }
        throw new ObjectNotFoundException("Não existe nenhum fornecedor cadastrado no banco de dados com o id " + id);
    }

    public FornecedorDTO buscaPorCpfCnpj(String cpfCnpj) {
        if (repository.buscaPorCpfCnpj(cpfCnpj).isPresent())
            return modelMapper.mapper().map(repository.buscaPorCpfCnpj(cpfCnpj).get(), FornecedorDTO.class);
        throw new ObjectNotFoundException("Nenhum fornecedor foi encontrado através do atributo cpfCnpj enviado.");
    }

    public FornecedorDTO buscaPorEmail(String email) {
        if (repository.buscaPorEmail(email).isPresent())
            return modelMapper.mapper().map(repository.buscaPorEmail(email).get(), FornecedorDTO.class);
        throw new ObjectNotFoundException("Nenhum fornecedor foi encontrado através do atributo email enviado.");
    }

    public List<FornecedorDTO> buscaPorTelefone(String telefone) {
        if (!repository.buscaPorTelefone(telefone).isEmpty())
            return repository.buscaPorTelefone(telefone).stream().map(x -> modelMapper.mapper().map(x, FornecedorDTO.class)).collect(Collectors.toList());
        throw new ObjectNotFoundException("Nenhum fornecedor foi encontrado através do atributo telefone enviado.");
    }

    public List<FornecedorDTO> buscaPorNome(String nome) {
        if (!repository.buscaPorNome(nome).isEmpty())
            return repository.buscaPorNome(nome).stream().map(x -> modelMapper.mapper().map(x, FornecedorDTO.class)).collect(Collectors.toList());
        throw new ObjectNotFoundException("Nenhum fornecedor foi encontrado através do atributo nome enviado.");
    }

    public FornecedorDTO atualizaPorId(Long id, FornecedorDTO fornecedor) {

        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Inicializando método de atualização de fornecedor...");

        log.info("[PROGRESS] Criando as variáveis do fornecedor: fornecedorOptional e fornecedorEncontrado...");
        Optional<FornecedorEntity> fornecedorOptional = repository.findById(id);
        FornecedorEntity fornecedorEncontrado;

        log.info("[PROGRESS] Criando o objeto fornecedorAtualizado setagem dos atributos...");
        FornecedorEntity fornecedorAtualizado;

        log.info("[PROGRESS] Iniciando a validação do objeto fornecedor recebido via requisição...");
        validation.validaCorpoRequisicao(fornecedor, repository, ValidationType.UPDATE);

        log.info("[PROGRESS] Verificando se um fornecedor com o id {} já existe na base de dados...", id);
        if (fornecedorOptional.isEmpty()) {
            log.info(FORNECEDOR_NAO_ENCONTRADO_LOG);
            throw new InvalidRequestException(FORNECEDOR_NAO_ENCONTRADO);
        }

        fornecedorEncontrado = fornecedorOptional.get();
        log.warn("[INFO] Fornecedor encontrado: {}", fornecedorEncontrado.getNome());

        log.info("[PROGRESS] Atualizando o fornecedor encontrado com os valores recebidos no corpo da requisição...");
        fornecedorAtualizado = fornecedorEncontrado;
        fornecedorAtualizado.setNome(fornecedor.getNome());
        fornecedorAtualizado.setCpfCnpj(fornecedor.getCpfCnpj());
        fornecedorAtualizado.setTelefone(fornecedor.getTelefone());
        fornecedorAtualizado.setEmail(fornecedor.getEmail());

        if (fornecedor.getEndereco() != null) {
            enderecoValidation.validaCorpoRequisicao(fornecedor.getEndereco());

            fornecedor.getEndereco().setDataCadastro(LocalDateTime.now());
            fornecedorAtualizado.setEndereco(modelMapper.mapper().map(fornecedor.getEndereco(), EnderecoEntity.class));
        } else {
            fornecedorAtualizado.setEndereco(null);
        }

        log.info("[PROGRESS] Iniciando persistência do fornecedor na base de dados...");
        repository.save(fornecedorAtualizado);

        log.warn(REQUISICAO_FINALIZADA_COM_SUCESSO);
        return modelMapper.mapper().map(fornecedorAtualizado, FornecedorDTO.class);

    }

    public Boolean deletaPorId(Long id) {

        log.info(BARRA_DE_LOG);
        log.info("[INICIANDO] Inicializando método deletaPorId...");

        log.info("[PROGRESS] Buscando por um fornecedor com o id {}...", id);
        Optional<FornecedorEntity> fornecedorOptional = repository.findById(id);

        if (fornecedorOptional.isPresent()) {

            log.warn("[INFO] Fornecedor encontrado.");

            log.info("[PROGRESS] Removendo o fornecedor da base de dados...");
            repository.deleteById(id);

            log.warn(REQUISICAO_FINALIZADA_COM_SUCESSO);
            return true;

        }
        log.error("[FAILURE] Fornecedor com o id {} não encontrado", id);
        throw new ObjectNotFoundException(FORNECEDOR_NAO_ENCONTRADO);

    }

}
