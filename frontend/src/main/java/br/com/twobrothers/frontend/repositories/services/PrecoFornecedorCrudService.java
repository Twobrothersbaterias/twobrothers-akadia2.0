package br.com.twobrothers.frontend.repositories.services;

import br.com.twobrothers.frontend.config.ModelMapperConfig;
import br.com.twobrothers.frontend.models.dto.PrecoFornecedorDTO;
import br.com.twobrothers.frontend.models.dto.UsuarioDTO;
import br.com.twobrothers.frontend.models.entities.FornecedorEntity;
import br.com.twobrothers.frontend.models.entities.PrecoFornecedorEntity;
import br.com.twobrothers.frontend.models.entities.ProdutoEstoqueEntity;
import br.com.twobrothers.frontend.repositories.FornecedorRepository;
import br.com.twobrothers.frontend.repositories.PrecoFornecedorRepository;
import br.com.twobrothers.frontend.repositories.ProdutoEstoqueRepository;
import br.com.twobrothers.frontend.repositories.UsuarioRepository;
import br.com.twobrothers.frontend.repositories.services.exceptions.InvalidRequestException;
import br.com.twobrothers.frontend.repositories.services.exceptions.ObjectNotFoundException;
import br.com.twobrothers.frontend.utils.UsuarioUtils;
import br.com.twobrothers.frontend.validations.PrecoFornecedorValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
    UsuarioRepository usuarioRepository;

    @Autowired
    ModelMapperConfig modelMapper;

    PrecoFornecedorValidation validation = new PrecoFornecedorValidation();

    public PrecoFornecedorDTO criaNovo(PrecoFornecedorDTO preco, Long idFornecedor, Long idProduto) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de criação...");

        log.info("[PROGRESS] Setando usuário responsável pelo cadastro do cliente...");
        preco.setUsuarioResponsavel(modelMapper.mapper().map(UsuarioUtils.loggedUser(usuarioRepository), UsuarioDTO.class));

        log.info("[PROGRESS] Validando objeto do tipo PrecoFornecedorDTO enviado via JSON...");
        validation.validaCorpoRequisicao(preco);

        log.info("[PROGRESS] Verificando se produto com o id {} existe na base de dados...", idProduto);
        if (idProduto != null && !produtoEstoqueRepository.findById(idProduto).isPresent()) {
            throw new InvalidRequestException("Não existe nenhum produto com o id " + idProduto);
        }

        log.info("[PROGRESS] Verificando se produto com o id {} existe na base de dados...", idFornecedor);
        if (idFornecedor != null && !fornecedorRepository.findById(idFornecedor).isPresent()) {
            throw new InvalidRequestException("Não existe nenhum fornecedor com o id " + idFornecedor);
        }

        log.info("[PROGRESS] Setando data de cadastro no preço: {}", LocalDate.now());
        preco.setDataCadastro(LocalDate.now().toString());

        log.info("[PROGRESS] Persistindo o preço no banco de dados SEM o produto e SEM o fornecedor...");
        PrecoFornecedorEntity precoFornecedorEntity =
                repository.save(modelMapper.mapper().map(preco, PrecoFornecedorEntity.class));

        ProdutoEstoqueEntity produtoEstoque = produtoEstoqueRepository.findById(idProduto).get();

        log.info("[PROGRESS] Adicionando preço ao produto e produto ao preço...");
        produtoEstoque.addPrecoFornecedor(precoFornecedorEntity);

        log.info("[PROGRESS] Persistindo produto no banco de dados com o novo preço na lista...");
        produtoEstoqueRepository.save(produtoEstoque);

        if (idFornecedor != null) {
            log.info("[PROGRESS] Adicionando preço ao fornecedor e fornecedor ao preço...");
            FornecedorEntity fornecedor = fornecedorRepository.findById(idFornecedor).get();
            fornecedor.addPrecoFornecedor(precoFornecedorEntity);

            log.info("[PROGRESS] Persistindo fornecedor no banco de dados com o novo preço na lista...");
            fornecedorRepository.save(fornecedor);
        }

        log.info("[PROGRESS] Persistindo novo preço na base de dados com relacionamento bidirecional...");
        log.warn(REQUISICAO_FINALIZADA_COM_SUCESSO);
        return modelMapper.mapper().map(repository.save(precoFornecedorEntity), PrecoFornecedorDTO.class);

    }

    public List<PrecoFornecedorEntity> buscaTodosPaginado(Pageable pageable) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca por todos os preços...");
        LocalDate hoje = LocalDate.now();
        return repository.findAll(pageable).toList();
    }

    public List<PrecoFornecedorEntity> buscaPorFornecedorIdPaginado(Pageable pageable, String fornecedorId) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de preço por fornecedorId: {}", fornecedorId);
        return repository.buscaPorFornecedorIdPaginado(pageable, Long.parseLong(fornecedorId));
    }

    public List<PrecoFornecedorEntity> buscaPorFornecedorPaginado(Pageable pageable, String fornecedor) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de preço por fornecedor...");
        return repository.buscaPorFornecedorPaginado(pageable, fornecedor);
    }

    public List<PrecoFornecedorEntity> buscaPorProdutoIdPaginado(Pageable pageable, String produtoId) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de preço por produtoId: {}", produtoId);
        return repository.buscaPorProdutoIdPaginado(pageable, Long.parseLong(produtoId));
    }

    public List<PrecoFornecedorEntity> buscaPorProdutoPaginado(Pageable pageable, String produto) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de preço por produto...");
        return repository.buscaPorProdutoPaginado(pageable, produto);
    }

    public List<PrecoFornecedorEntity> buscaTodosSemPaginacao() {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca por todos os preços...");
        LocalDate hoje = LocalDate.now();
        return repository.findAll();
    }

    public List<PrecoFornecedorEntity> buscaPorFornecedorIdSemPaginacao(String fornecedorId) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de preço por fornecedorId...");
        return repository.buscaPorFornecedorIdSemPaginacao(Long.parseLong(fornecedorId));
    }

    public List<PrecoFornecedorEntity> buscaPorFornecedorSemPaginacao(String fornecedor) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de preço por fornecedor...");
        return repository.buscaPorFornecedorSemPaginacao(fornecedor);
    }

    public List<PrecoFornecedorEntity> buscaPorProdutoIdSemPaginacao(String produtoId) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de preço por produtoId: {}", produtoId);
        return repository.buscaPorProdutoIdSemPaginacao(Long.parseLong(produtoId));
    }

    public List<PrecoFornecedorEntity> buscaPorProdutoSemPaginacao(String produto) {
        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de busca de preço por produto...");
        return repository.buscaPorProdutoSemPaginacao(produto);
    }

    public PrecoFornecedorDTO atualizaPorId(PrecoFornecedorDTO preco) {

        Optional<PrecoFornecedorEntity> precoOptional = repository.findById(preco.getId());

        if (precoOptional.isEmpty()) {
            log.error("[ERROR] Nenhum preço foi encontrado com o id {}", preco.getId());
            throw new ObjectNotFoundException("Nenhum preço foi encontrado com o id " + preco.getId());
        }

        log.info("[PROGRESS] Iniciando classe de validação de preço de fornecedor...");
        validation.validaCorpoRequisicao(preco);

        log.info("[PROGRESS] Verificando se produto passado existe na base de dados...");
        if (preco.getProduto() != null && !produtoEstoqueRepository.findById(preco.getProduto().getId()).isPresent()) {
            throw new InvalidRequestException("Não existe nenhum produto com o id " + preco.getProduto().getId());
        }

        log.info("[PROGRESS] Verificando se fornecedor passado existe na base de dados...");
        if (preco.getFornecedor().getId() != null && !fornecedorRepository.findById(preco.getFornecedor().getId()).isPresent()) {
            throw new InvalidRequestException("Não existe nenhum fornecedor com o id " + preco.getFornecedor().getId());
        }

        log.info("[PROGRESS] Atribuindo valor do objeto preco recebido pelo requisição ao objeto precoAtualizado...");
        PrecoFornecedorEntity precoAtualizado = precoOptional.get();

        log.info("[PROGRESS] Atualizando PrecoFornecedorDTO com os valores recebidos via requisição...");
        precoAtualizado.setValor(preco.getValor());
        precoAtualizado.setObservacao(preco.getObservacao());

        ProdutoEstoqueEntity produtoEstoque = produtoEstoqueRepository.findById(preco.getProduto().getId()).get();

        log.info("[PROGRESS] Adicionando preço ao produto e produto ao preço...");
        produtoEstoque.addPrecoFornecedor(precoAtualizado);

        log.info("[PROGRESS] Persistindo produto no banco de dados com o novo preço na lista...");
        produtoEstoqueRepository.save(produtoEstoque);

        if (preco.getFornecedor().getId() != null) {
            log.info("[PROGRESS] Adicionando preço ao fornecedor e fornecedor ao preço...");
            FornecedorEntity fornecedor = fornecedorRepository.findById(preco.getFornecedor().getId()).get();
            fornecedor.addPrecoFornecedor(precoAtualizado);
            log.info("[PROGRESS] Persistindo fornecedor no banco de dados com o novo preço na lista...");
            fornecedorRepository.save(fornecedor);
        }

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
