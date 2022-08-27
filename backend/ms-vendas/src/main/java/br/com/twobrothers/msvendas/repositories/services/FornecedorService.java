package br.com.twobrothers.msvendas.repositories.services;

import br.com.twobrothers.msvendas.config.ModelMapperConfig;
import br.com.twobrothers.msvendas.exceptions.InvalidRequestException;
import br.com.twobrothers.msvendas.exceptions.ObjectNotFoundException;
import br.com.twobrothers.msvendas.models.dto.EnderecoDTO;
import br.com.twobrothers.msvendas.models.dto.FornecedorDTO;
import br.com.twobrothers.msvendas.models.entities.EnderecoEntity;
import br.com.twobrothers.msvendas.models.entities.FornecedorEntity;
import br.com.twobrothers.msvendas.models.enums.ValidationType;
import br.com.twobrothers.msvendas.repositories.EnderecoRepository;
import br.com.twobrothers.msvendas.repositories.FornecedorRepository;
import br.com.twobrothers.msvendas.validations.EnderecoValidation;
import br.com.twobrothers.msvendas.validations.FornecedorValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static br.com.twobrothers.msvendas.utils.StringConstants.*;

@Slf4j
@Service
public class FornecedorService {

    @Autowired
    FornecedorRepository repository;

    @Autowired
    EnderecoRepository enderecoRepository;

    @Autowired
    ModelMapperConfig modelMapper;

    FornecedorValidation validation = new FornecedorValidation();
    EnderecoValidation enderecoValidation = new EnderecoValidation();

    public FornecedorDTO criaNovo(FornecedorDTO fornecedor) {

        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de criação");

        log.info("[PROGRESS] Setando a data de cadastro no fornecedor: {}", LocalDateTime.now());
        fornecedor.setDataCadastro(LocalDateTime.now());

        if (!validation.validaCorpoRequisicao(fornecedor, repository, ValidationType.CREATE)) {
            log.info(VALIDACAO_DO_FORNECEDOR_FALHOU_LOG);
            throw new InvalidRequestException(VALIDACAO_DO_FORNECEDOR_FALHOU);
        }

        log.info("[PROGRESS] Verificando se objeto FornecedorDTO possui objeto do tipo EnderecoDTO acoplado...");
        if (fornecedor.getEndereco() != null) {

            log.warn("[INFO] Objeto endereço acoplado detectado no corpo da requisição");

            if (!enderecoValidation.validaCorpoRequisicao(fornecedor.getEndereco()))
                throw new InvalidRequestException("Falha na validação do corpo do endereço");

            EnderecoDTO enderecoDTO = fornecedor.getEndereco();

            log.info("[PROGRESS] Setando data de cadastro no objeto endereço recebido na requisição...");
            enderecoDTO.setDataCadastro(LocalDateTime.now());

            enderecoDTO.addFornecedor(fornecedor);

            log.info("[PROGRESS] Salvando o endereço na base de dados persistindo o fornecedor em cascata...");
            enderecoRepository.save(modelMapper.mapper().map(enderecoDTO, EnderecoEntity.class));
            log.info("[SUCCESS] Requisição finalizada com sucesso");
            return fornecedor;

        }
        else{

            log.warn("[INFO] Objeto endereço não detectado no corpo da requisição");

            log.info("[PROGRESS] Salvando o fornecedor na base de dados...");
            log.info("[SUCCESS] Requisição finalizada com sucesso");
            return modelMapper.mapper().map(repository
                    .save(modelMapper.mapper().map(fornecedor, FornecedorEntity.class)), FornecedorDTO.class);

        }

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

        log.info("[PROGRESS] Verificando se um fornecedor com o id {} já existe na base de dados...", id);
        if (validation.validaCorpoRequisicao(fornecedor, repository, ValidationType.UPDATE)
                && fornecedorOptional.isPresent()) {

            fornecedorEncontrado = fornecedorOptional.get();
            log.warn("[INFO] Fornecedor encontrado: {}", fornecedorEncontrado.getNome());

            log.info("[PROGRESS] Atualizando o fornecedor encontrado com os valores recebidos no corpo da requisição...");
            fornecedorAtualizado = fornecedorEncontrado;
            fornecedorAtualizado.setNome(fornecedor.getNome());
            fornecedorAtualizado.setCpfCnpj(fornecedor.getCpfCnpj());
            fornecedorAtualizado.setTelefone(fornecedor.getTelefone());
            fornecedorAtualizado.setEmail(fornecedor.getEmail());
            if (fornecedor.getEndereco() != null) {
                fornecedorAtualizado.setEndereco(modelMapper.mapper().map(fornecedor.getEndereco(), EnderecoEntity.class));
            }
            else {
                fornecedorAtualizado.setEndereco(null);
            }

            log.info("[PROGRESS] Verificando se algum endereço foi enviado via JSON...");
            if (fornecedor.getEndereco() != null) {

                if (!enderecoValidation.validaCorpoRequisicao(fornecedor.getEndereco())) {
                    log.error(VALIDACAO_DO_ENDERECO_FALHOU_LOG);
                    throw new InvalidRequestException(VALIDACAO_DO_ENDERECO_FALHOU);
                }

                log.info("[PROGRESS] Adicionando fornecedor ao endereço e endereço ao fornecedor");
                EnderecoEntity enderecoEntity = modelMapper.mapper().map(fornecedor.getEndereco(), EnderecoEntity.class);
                enderecoEntity.addFornecedor(fornecedorAtualizado);

                log.info("[PROGRESS] Iniciando persistência em cascata [enderecoEntity] -> [fornecedorEntity]...");
                enderecoRepository.save(enderecoEntity);

            }
            else {

                log.warn("[INFO] Endereço não encontrado no corpo da requisição.");

                log.info("[PROGRESS] Iniciando persistência do fornecedor na base de dados...");
                repository.save(fornecedorAtualizado);

            }

            log.warn(REQUISICAO_FINALIZADA_COM_SUCESSO);
            return modelMapper.mapper().map(fornecedorAtualizado, FornecedorDTO.class);


        }

        log.info(FORNECEDOR_NAO_ENCONTRADO_LOG);
        throw new InvalidRequestException(FORNECEDOR_NAO_ENCONTRADO);
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
