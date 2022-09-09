package br.com.twobrothers.frontend.repositories.services;

import br.com.twobrothers.frontend.config.ModelMapperConfig;
import br.com.twobrothers.frontend.models.dto.PrecoFornecedorDTO;
import br.com.twobrothers.frontend.models.entities.FornecedorEntity;
import br.com.twobrothers.frontend.models.entities.PrecoFornecedorEntity;
import br.com.twobrothers.frontend.models.entities.ProdutoEstoqueEntity;
import br.com.twobrothers.frontend.repositories.FornecedorRepository;
import br.com.twobrothers.frontend.repositories.PrecoFornecedorRepository;
import br.com.twobrothers.frontend.repositories.ProdutoEstoqueRepository;
import br.com.twobrothers.frontend.repositories.services.exceptions.InvalidRequestException;
import br.com.twobrothers.frontend.repositories.services.exceptions.ObjectNotFoundException;
import br.com.twobrothers.frontend.validations.PrecoFornecedorValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static br.com.twobrothers.frontend.utils.StringConstants.BARRA_DE_LOG;
import static br.com.twobrothers.frontend.utils.StringConstants.REQUISICAO_FINALIZADA_COM_SUCESSO;

/**
 * @author Gabriel Lagrota
 * @version 1.0
 * @email gabriellagrota23@gmail.com
 * @phone (11)97981-5415
 * @github https://github.com/LagrotaGabriel
 * @since 30-08-22
 */
@Slf4j
@Service
public class PrecoFornecedorCrudService {

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
        validation.validaCorpoRequisicao(preco);

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

        log.info("[PROGRESS] Persistindo novo preço na base de dados com relacionamento bidirecional...");
        log.warn(REQUISICAO_FINALIZADA_COM_SUCESSO);
        return modelMapper.mapper().map(repository.save(precoFornecedorEntity), PrecoFornecedorDTO.class);

    }

    public List<PrecoFornecedorDTO> buscaTodos() {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de buscar todos os preços de fornecedor...");
        if (!repository.findAll().isEmpty()) {
            log.info(REQUISICAO_FINALIZADA_COM_SUCESSO);
            return repository.findAll().stream()
                    .map(x -> modelMapper.mapper().map(x, PrecoFornecedorDTO.class)).collect(Collectors.toList());
        }
        log.error("[ERROR] Não existe nenhum preço de fornecedor salvo na base de dados");
        throw new ObjectNotFoundException("Não existe nenhum preço salvo na base de dados.");
    }

    public List<PrecoFornecedorDTO> buscaPorPaginacao(Pageable paginacao) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de preço de fornecedor por paginação...");
        if (!repository.findAll(paginacao).isEmpty()) {
            log.info(REQUISICAO_FINALIZADA_COM_SUCESSO);
            return repository.findAll(paginacao)
                    .getContent().stream().map(x -> modelMapper.mapper().map(x, PrecoFornecedorDTO.class)).collect(Collectors.toList());
        }
        log.error("[ERROR] Não existe nenhum preço cadastrado na página indicada");
        throw new ObjectNotFoundException("Não existe nenhum preço cadastrado na página indicada");
    }

    public List<PrecoFornecedorDTO> buscaPorRangeDeDataCadastro(String dataInicio, String dataFim) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de preço de fornecedor por range de cadastro...");

        List<PrecoFornecedorEntity> precos = repository.buscaPorRangeDeDataCadastro(
                (LocalDate.parse(dataInicio)).atTime(0, 0),
                (LocalDate.parse(dataFim)).atTime(23, 59, 59, 999999999));

        if (!precos.isEmpty()) {
            log.info(REQUISICAO_FINALIZADA_COM_SUCESSO);
            return precos.stream().map(x -> modelMapper.mapper().map(x, PrecoFornecedorDTO.class)).collect(Collectors.toList());
        }
        log.error("[ERROR] Não existe nenhum produto cadastrado no range de datas indicado");
        throw new ObjectNotFoundException("Não existe nenhum produto cadastrado no range de datas indicado");

    }

    public PrecoFornecedorDTO buscaPorId(Long id) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de preço de fornecedor por id...");
        if (repository.findById(id).isPresent()) {
            log.info(REQUISICAO_FINALIZADA_COM_SUCESSO);
            return modelMapper.mapper().map(repository.findById(id).get(), PrecoFornecedorDTO.class);
        }
        log.error("[ERROR] Não existe nenhum preço cadastrado no banco de dados com o id {}", id);
        throw new ObjectNotFoundException("Não existe nenhum preço cadastrado no banco de dados com o id " + id);
    }

    public PrecoFornecedorDTO atualizaPorId(Long id, PrecoFornecedorDTO preco) {

        Optional<PrecoFornecedorEntity> precoOptional = repository.findById(id);

        if (precoOptional.isEmpty()) {
            log.error("[ERROR] Nenhum preço foi encontrado com o id {}", id);
            throw new ObjectNotFoundException("Nenhum preço foi encontrado com o id " + id);
        }

        log.info("[PROGRESS] Iniciando classe de validação de preço de fornecedor...");
        validation.validaCorpoRequisicao(preco);

        log.info("[PROGRESS] Atribuindo valor do objeto preco recebido pelo requisição ao objeto precoAtualizado...");
        PrecoFornecedorEntity precoAtualizado = precoOptional.get();

        log.info("[PROGRESS] Atualizando PrecoFornecedorDTO com os valores recebidos via requisição...");

        precoAtualizado.setValor(preco.getValor());
        precoAtualizado.setObservacao(preco.getObservacao());

        log.info(REQUISICAO_FINALIZADA_COM_SUCESSO);
        return modelMapper.mapper().map(repository.save(precoAtualizado), PrecoFornecedorDTO.class);


    }

    public Boolean deletaPorId(Long id) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de remoção de preço de fornecedor por id...");
        if (repository.findById(id).isPresent()) {
            log.info(REQUISICAO_FINALIZADA_COM_SUCESSO);
            repository.delete(repository.findById(id).get());
            return true;
        }
        log.error("[ERROR] Não existe nenhum preço de fornecedor cadastrado com o id {}", id);
        throw new ObjectNotFoundException("Nenhum preço foi encontrado com o id " + id);
    }

}
