package br.com.twobrothers.msvendas.repositories.services;

import br.com.twobrothers.msvendas.config.ModelMapperConfig;
import br.com.twobrothers.msvendas.exceptions.InvalidRequestException;
import br.com.twobrothers.msvendas.exceptions.ObjectNotFoundException;
import br.com.twobrothers.msvendas.models.dto.PrecoFornecedorDTO;
import br.com.twobrothers.msvendas.models.entities.FornecedorEntity;
import br.com.twobrothers.msvendas.models.entities.PrecoFornecedorEntity;
import br.com.twobrothers.msvendas.models.entities.ProdutoEstoqueEntity;
import br.com.twobrothers.msvendas.repositories.FornecedorRepository;
import br.com.twobrothers.msvendas.repositories.PrecoFornecedorRepository;
import br.com.twobrothers.msvendas.repositories.ProdutoEstoqueRepository;
import br.com.twobrothers.msvendas.validations.PrecoFornecedorValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static br.com.twobrothers.msvendas.utils.StringConstants.BARRA_DE_LOG;
import static br.com.twobrothers.msvendas.utils.StringConstants.REQUISICAO_FINALIZADA_COM_SUCESSO;

@Slf4j
@Service
public class PrecoFornecedorService {

    @Autowired
    PrecoFornecedorRepository repository;

    @Autowired
    ProdutoEstoqueRepository produtoEstoqueRepository;

    @Autowired
    FornecedorRepository fornecedorRepository;

    @Autowired
    ModelMapperConfig modelMapper;

    PrecoFornecedorValidation validation = new PrecoFornecedorValidation();

    public PrecoFornecedorDTO criaNovo(PrecoFornecedorDTO preco, Long idFornecedor, Long idProduto) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de criação...");

        log.info("[PROGRESS] Validando objeto do tipo PrecoFornecedorDTO enviado via JSON...");
        if (validation.validaCorpoRequisicao(preco)) {

            log.info("[PROGRESS] Verificando se produto com o id {} existe na base de dados...", idProduto);
            if (!produtoEstoqueRepository.existsById(idProduto)) {
                throw new InvalidRequestException("Não existe nenhum produto com o id " + idProduto);
            }

            log.info("[PROGRESS] Verificando se produto com o id {} existe na base de dados...", idFornecedor);
            if (!fornecedorRepository.existsById(idFornecedor)) {
                throw new InvalidRequestException("Não existe nenhum fornecedor com o id " + idFornecedor);
            }

            log.info("[PROGRESS] Setando data de cadastro no preço: {}", LocalDateTime.now());
            preco.setDataCadastro(LocalDateTime.now());

            log.info("[PROGRESS] Persistindo o preço no banco de dados SEM o produto e SEM o fornecedor...");
            PrecoFornecedorEntity precoFornecedorEntity =
                    repository.save(modelMapper.mapper().map(preco, PrecoFornecedorEntity.class));

            ProdutoEstoqueEntity produtoEstoque = produtoEstoqueRepository.findById(idProduto).get();

            log.info("[PROGRESS] Adicionando preço ao produto e produto ao preço...");
            produtoEstoque.addPrecoFornecedor(precoFornecedorEntity);

            log.info("[PROGRESS] Persistindo produto no banco de dados com o novo preço na lista...");
            produtoEstoqueRepository.save(produtoEstoque);

            log.info("[PROGRESS] Adicionando preço ao fornecedor e fornecedor ao preço...");
            FornecedorEntity fornecedor = fornecedorRepository.findById(idFornecedor).get();
            fornecedor.addPrecoFornecedor(precoFornecedorEntity);

            log.info("[PROGRESS] Persistindo fornecedor no banco de dados com o novo preço na lista...");
            fornecedorRepository.save(fornecedor);

            log.info("[PROGRESS] Persistindo novo preço na base de dados com relacionamento bidirecional finalizado...");
            log.warn(REQUISICAO_FINALIZADA_COM_SUCESSO);
            return modelMapper.mapper().map(repository.save(precoFornecedorEntity), PrecoFornecedorDTO.class);

        }

        throw new InvalidRequestException("A validação da requisição falhou. Verificar corpo da requisição.");
    }

    public List<PrecoFornecedorDTO> buscaTodos() {
        if (!repository.findAll().isEmpty()) return repository.findAll().stream()
                .map(x -> modelMapper.mapper().map(x, PrecoFornecedorDTO.class)).collect(Collectors.toList());
        throw new ObjectNotFoundException("Não existe nenhum preço salvo na base de dados.");
    }

    public List<PrecoFornecedorDTO> buscaPorPaginacao(Pageable paginacao) {
        if (!repository.findAll(paginacao).isEmpty()) return repository.findAll(paginacao)
                .getContent().stream().map(x -> modelMapper.mapper().map(x, PrecoFornecedorDTO.class)).collect(Collectors.toList());
        throw new ObjectNotFoundException("Não existe nenhum preço cadastrado na página indicada");
    }

    public List<PrecoFornecedorDTO> buscaPorRangeDeDataCadastro(String dataInicio, String dataFim) {

        List<PrecoFornecedorEntity> precos = repository.buscaPorRangeDeDataCadastro(
                (LocalDate.parse(dataInicio)).atTime(0, 0),
                (LocalDate.parse(dataFim)).atTime(23, 59, 59, 999999999));

        if (!precos.isEmpty())
            return precos.stream().map(x -> modelMapper.mapper().map(x, PrecoFornecedorDTO.class)).collect(Collectors.toList());
        throw new ObjectNotFoundException("Não existe nenhum produto cadastrado no range de datas indicado");

    }

    public PrecoFornecedorDTO buscaPorId(Long id) {
        if (repository.findById(id).isPresent()) {
            return modelMapper.mapper().map(repository.findById(id).get(), PrecoFornecedorDTO.class);
        }
        throw new ObjectNotFoundException("Não existe nenhum preço cadastrado no banco de dados com o id " + id);
    }

    public PrecoFornecedorDTO atualizaPorId(Long id, PrecoFornecedorDTO preco) {

        Optional<PrecoFornecedorEntity> precoOptional = repository.findById(id);

        if (precoOptional.isPresent()) {

            PrecoFornecedorEntity precoAtualizado = precoOptional.get();

            if (validation.validaCorpoRequisicao(preco)) {
                precoAtualizado.setValor(preco.getValor());
                precoAtualizado.setObservacao(preco.getObservacao());
                return modelMapper.mapper().map(repository.save(precoAtualizado), PrecoFornecedorDTO.class);
            }
            throw new InvalidRequestException("Requisição inválida.");
        }
        throw new ObjectNotFoundException("Nenhum preço foi encontrado com o id " + id);
    }

    public Boolean deletaPorId(Long id) {
        if (repository.findById(id).isPresent()) {
            repository.delete(repository.findById(id).get());
            return true;
        }
        throw new ObjectNotFoundException("Nenhum preço foi encontrado com o id " + id);
    }

}
