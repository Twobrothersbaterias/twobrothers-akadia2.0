package br.com.twobrothers.msvendas.services;

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

        log.info("[PROGRESS] Verificando se objeto FornecedorDTO possui objeto do tipo EnderecoDTO acoplado...");

        if (fornecedor.getEndereco() != null) {

            log.warn("[INFO] Objeto EnderecoDTO acoplado detectado");

            log.info("[PROGRESS] Validando corpo do objeto FornecedorDTO passado via JSON...");
            if (validation.validaCorpoRequisicao(fornecedor, repository, ValidationType.CREATE)) {

                log.info("[PROGRESS] Verificando se o endereço já existe na base de dados: {}", fornecedor.getEndereco());

                Optional<EnderecoEntity> enderecoEntity = enderecoRepository.buscaPorAtributos(
                        fornecedor.getEndereco().getLogradouro(),
                        fornecedor.getEndereco().getBairro(),
                        fornecedor.getEndereco().getNumero()
                );

                EnderecoDTO enderecoDTO;

                if (enderecoEntity.isPresent()) {
                    log.warn("[INFO] O endereço passado já existe");
                    enderecoDTO = modelMapper.mapper().map(enderecoEntity.get(), EnderecoDTO.class);
                } else {
                    log.warn("[INFO] O endereço passado não existe");
                    enderecoDTO = fornecedor.getEndereco();

                    log.info("[PROGRESS] Validando corpo do objeto EnderecoDTO passado via JSON...");
                    if (enderecoValidation.validaCorpoRequisicao(enderecoDTO, enderecoRepository, ValidationType.CREATE)) {
                        log.info("[PROGRESS] Setando a data de cadastro no endereço: {}", LocalDateTime.now());
                        enderecoDTO.setDataCadastro(LocalDateTime.now());
                    } else {
                        log.error(VALIDACAO_DO_ENDERECO_FALHOU_LOG);
                        throw new InvalidRequestException(VALIDACAO_DO_ENDERECO_FALHOU);
                    }
                }

                enderecoDTO.addFornecedor(fornecedor);

                log.info("[PROGRESS] Salvando o fornecedor na base de dados...");
                enderecoRepository.save(modelMapper.mapper().map(enderecoDTO, EnderecoEntity.class));
                log.info("[SUCCESS] Requisição finalizada com sucesso");
                return fornecedor;

            }

        } else {

            log.warn("[INFO] Objeto EnderecoDTO acoplado não detectado");

            log.info("[PROGRESS] Validando corpo do objeto EnderecoDTO passado via JSON...");
            if (validation.validaCorpoRequisicao(fornecedor, repository, ValidationType.CREATE)) {
                log.info("[PROGRESS] Salvando o fornecedor na base de dados...");
                log.info("[SUCCESS] Requisição finalizada com sucesso");
                return modelMapper.mapper().map(repository.save(modelMapper.mapper().map(fornecedor, FornecedorEntity.class)), FornecedorDTO.class);
            }

        }

        log.info(VALIDACAO_DO_FORNECEDOR_FALHOU_LOG);
        throw new InvalidRequestException(VALIDACAO_DO_FORNECEDOR_FALHOU);

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
            fornecedorAtualizado.setTelefone(fornecedor.getTelefone());
            fornecedorAtualizado.setEmail(fornecedor.getEmail());
            fornecedorAtualizado.setCpfCnpj(fornecedor.getCpfCnpj());

            log.info("[PROGRESS] Verificando se algum endereço foi enviado via JSON...");
            if (fornecedor.getEndereco() != null) {

                if (!enderecoValidation.validaCorpoRequisicao(fornecedor.getEndereco(), enderecoRepository, ValidationType.UPDATE)) {
                    log.error(VALIDACAO_DO_ENDERECO_FALHOU_LOG);
                    throw new InvalidRequestException(VALIDACAO_DO_ENDERECO_FALHOU);
                }

                log.info("[PROGRESS] Criando as variáveis do endereço: enderecoOptional e enderecoEncontrado...");
                Optional<EnderecoEntity> enderecoOptional = enderecoRepository.buscaPorAtributos(
                        fornecedor.getEndereco().getLogradouro(),
                        fornecedor.getEndereco().getBairro(),
                        fornecedor.getEndereco().getNumero()
                );
                EnderecoEntity enderecoEncontrado;

                log.info("[PROGRESS] Criando o objeto enderecoAtualizado para setagem dos atributos...");
                EnderecoEntity enderecoAtualizado;

                log.info("[PROGRESS] Verificando se o endereço recebido via JSON existe na base de dados...");
                if (enderecoOptional.isPresent()) {

                    enderecoEncontrado = enderecoOptional.get();
                    log.info("[INFO] Endereço encontrado: {}, {}", enderecoEncontrado.getLogradouro(), enderecoEncontrado.getNumero());

                    log.info("[PROGRESS] Verificando se o endereço passado na requisição é diferente do antigo endereço do fornecedor...");
                    if (fornecedorEncontrado.getEndereco() != enderecoEncontrado) {

                        log.warn("[INFO] O endereço recebido é diferente.");
                        log.info("[PROGRESS] Removendo o fornecedor a ser atualizado do endereço antigo...");
                        EnderecoEntity enderecoAntigo = fornecedorEncontrado.getEndereco();
                        enderecoAntigo.removeFornecedor(fornecedorEncontrado);

                        log.info("[PROGRESS] Salvando o endereço antigo sem o fornecedor...");
                        enderecoRepository.save(enderecoAntigo);

                        log.info("[PROGRESS] Setando o valor do endereço atualizado com o valor do endereço encontrado...");
                        enderecoAtualizado = modelMapper.mapper().map(enderecoEncontrado, EnderecoEntity.class);

                    } else {
                        log.warn("[INFO] O endereço recebido na requisição é igual ao endereço antigo do fornecedor.");
                        log.info("[PROGRESS] Setando a variável enderecoAtualizado com o valor do endereco encontrado...");
                        enderecoAtualizado = enderecoEncontrado;

                        log.info("[PROGRESS] Removendo o fornecedor do endereço antigo...");
                        EnderecoEntity enderecoAntigo = fornecedorEncontrado.getEndereco();
                        enderecoAntigo.removeFornecedor(fornecedorEncontrado);

                    }

                } else {

                    log.warn("[INFO] Endereço não encontrado.");

                    log.info("[PROGRESS] Removendo o fornecedor do endereço antigo...");
                    EnderecoEntity enderecoAntigo = fornecedorEncontrado.getEndereco();
                    enderecoAntigo.removeFornecedor(fornecedorEncontrado);

                    log.info("[PROGRESS] Salvando o endereço antigo sem o fornecedor...");
                    enderecoRepository.save(enderecoAntigo);

                    log.info("[PROGRESS] Setando o valor da variável enderecoAtualizado com o valor passado via JSON...");
                    enderecoAtualizado = modelMapper.mapper().map(fornecedor.getEndereco(), EnderecoEntity.class);
                    enderecoAtualizado.setDataCadastro(LocalDateTime.now());

                }

                log.info("[PROGRESS] Adicionando o endereço ao fornecedor e o fornecedor ao endereço...");
                enderecoAtualizado.addFornecedor(fornecedorAtualizado);

                log.info("[PROGRESS] Salvando o novo endereço com o fornecedor atualizado dentro...");
                enderecoRepository.save(enderecoAtualizado);

            }
            else {

                log.warn("[INFO] Nenhum endereço foi recebido na requisição");

                log.info("[INFO] Verifica se o fornecedor antigo possuia um endereço cadastrado");
                if (fornecedorEncontrado.getEndereco() != null) {

                    EnderecoEntity enderecoEncontrado = new EnderecoEntity();

                    Optional<EnderecoEntity> enderecoOptional = enderecoRepository.buscaPorAtributos(
                            fornecedorEncontrado.getEndereco().getLogradouro(),
                            fornecedorEncontrado.getEndereco().getBairro(),
                            fornecedorEncontrado.getEndereco().getNumero()
                    );

                    if (enderecoOptional.isPresent()) {
                        enderecoEncontrado = enderecoOptional.get();
                    }

                    enderecoEncontrado.getFornecedores().remove(fornecedorEncontrado);
                    enderecoRepository.save(enderecoEncontrado);
                }

                log.info("[PROGRESS] Salvando o fornecedor na base de dados...");
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

            log.info("[PROGRESS] Buscando pelo endereço do fornecedor na base de dados...");
            Optional<EnderecoEntity> optionalAddress = enderecoRepository.buscaPorAtributos(
                    fornecedorOptional.get().getEndereco().getLogradouro(),
                    fornecedorOptional.get().getEndereco().getBairro(),
                    fornecedorOptional.get().getEndereco().getNumero()
            );

            if (optionalAddress.isPresent()) {
                log.warn("[INFO] Endereço encontrado: {}, {}", optionalAddress.get().getLogradouro(), optionalAddress.get().getNumero());
                log.info("[PROGRESS] Removendo o fornecedor da lista de fornecedors do endereço...");
                optionalAddress.get().getFornecedores().remove(fornecedorOptional.get());
                log.info("[PROGRESS] Salvando o endereço sem o fornecedor na lista..");
                enderecoRepository.save(optionalAddress.get());
            }

            log.info("[PROGRESS] Removendo o fornecedor da base de dados...");
            repository.deleteById(id);

            log.warn(REQUISICAO_FINALIZADA_COM_SUCESSO);
            return true;

        }
        log.error("[FAILURE] Fornecedor com o id {} não encontrado", id);
        throw new ObjectNotFoundException(FORNECEDOR_NAO_ENCONTRADO);

    }

}
