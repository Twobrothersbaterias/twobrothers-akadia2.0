package br.com.twobrothers.msvendas.repositories.services;

import br.com.twobrothers.msvendas.config.ModelMapperConfig;
import br.com.twobrothers.msvendas.exceptions.InvalidRequestException;
import br.com.twobrothers.msvendas.exceptions.ObjectNotFoundException;
import br.com.twobrothers.msvendas.models.dto.AbastecimentoDTO;
import br.com.twobrothers.msvendas.models.dto.PrecoFornecedorDTO;
import br.com.twobrothers.msvendas.models.entities.AbastecimentoEntity;
import br.com.twobrothers.msvendas.models.entities.FornecedorEntity;
import br.com.twobrothers.msvendas.models.entities.PrecoFornecedorEntity;
import br.com.twobrothers.msvendas.models.entities.ProdutoEstoqueEntity;
import br.com.twobrothers.msvendas.repositories.AbastecimentoRepository;
import br.com.twobrothers.msvendas.repositories.FornecedorRepository;
import br.com.twobrothers.msvendas.repositories.ProdutoEstoqueRepository;
import br.com.twobrothers.msvendas.validations.AbastecimentoValidation;
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
public class AbastecimentoService {

    @Autowired
    AbastecimentoRepository repository;

    @Autowired
    ProdutoEstoqueRepository produtoEstoqueRepository;

    @Autowired
    FornecedorRepository fornecedorRepository;

    @Autowired
    ModelMapperConfig modelMapper;

    AbastecimentoValidation validation = new AbastecimentoValidation();

    public AbastecimentoDTO criaNovo(AbastecimentoDTO abastecimento, Long idProduto, Long idFornecedor) {

        log.info(BARRA_DE_LOG);
        log.info("[STARTING] Iniciando método de criação...");

        log.info("[PROGRESS] Validando objeto do tipo AbastecimentoDTO enviado via JSON...");
        if (validation.validaCorpoRequisicao(abastecimento)) {

            log.info("[PROGRESS] Verificando se produto com o id {} existe na base de dados...", idProduto);
            if (!produtoEstoqueRepository.existsById(idProduto)) {
                throw new InvalidRequestException("Não existe nenhum produto com o id " + idProduto);
            }

            log.info("[PROGRESS] Verificando se fornecedor com o id {} existe na base de dados...", idFornecedor);
            if (!fornecedorRepository.existsById(idFornecedor)) {
                throw new InvalidRequestException("Não existe nenhum fornecedor com o id " + idFornecedor);
            }

            log.info("[PROGRESS] Setando data de cadastro no abastecimento: {}", LocalDateTime.now());
            abastecimento.setDataCadastro(LocalDateTime.now());

            log.info("[PROGRESS] Setando o custo unitário do abastecimento...");
            abastecimento.setCustoUnitario(abastecimento.getCustoTotal()/abastecimento.getQuantidade());

            log.info("[PROGRESS] Persistindo o abastecimento no banco de dados SEM o produto e SEM o fornecedor...");
            AbastecimentoEntity abastecimentoEntity =
                    repository.save(modelMapper.mapper().map(abastecimento, AbastecimentoEntity.class));

            ProdutoEstoqueEntity produtoEstoque = produtoEstoqueRepository.findById(idProduto).get();

            log.info("[PROGRESS] Aumentando a quantidade de produtos em estoque com a quantidade passada no abastecimento...");
            produtoEstoque.setQuantidade(produtoEstoque.getQuantidade() + abastecimento.getQuantidade());

            log.info("[PROGRESS] Adicionando abastecimento ao produto e produto ao abastecimento...");
            produtoEstoque.addAbastecimento(abastecimentoEntity);

            log.info("[PROGRESS] Persistindo produto no banco de dados com o novo abastecimento na lista...");
            produtoEstoqueRepository.save(produtoEstoque);

            log.info("[PROGRESS] Adicionando abastecimento ao fornecedor e fornecedor ao abastecimento...");
            FornecedorEntity fornecedor = fornecedorRepository.findById(idFornecedor).get();
            fornecedor.addAbastecimento(abastecimentoEntity);

            log.info("[PROGRESS] Persistindo fornecedor no banco de dados com o novo abastecimento na lista...");
            fornecedorRepository.save(fornecedor);

            log.info("[PROGRESS] Persistindo novo abastecimento na base de dados com relacionamento bidirecional finalizado...");
            log.warn(REQUISICAO_FINALIZADA_COM_SUCESSO);
            return modelMapper.mapper().map(repository.save(abastecimentoEntity), AbastecimentoDTO.class);
        }
        throw new InvalidRequestException("Houve uma falha na requisição");

    }

    public List<AbastecimentoDTO> buscaTodos() {
        if (!repository.findAll().isEmpty()) return repository.findAll().stream()
                .map(x -> modelMapper.mapper().map(x, AbastecimentoDTO.class)).collect(Collectors.toList());
        throw new ObjectNotFoundException("Não existe nenhum abastecimento salvo na base de dados.");
    }

    public List<AbastecimentoDTO> buscaPorPaginacao(Pageable paginacao) {
        if (!repository.findAll(paginacao).isEmpty()) return repository.findAll(paginacao)
                .getContent().stream().map(x -> modelMapper.mapper().map(x, AbastecimentoDTO.class)).collect(Collectors.toList());
        throw new ObjectNotFoundException("Não existe nenhum abastecimento cadastrado na página indicada");
    }

    public List<AbastecimentoDTO> buscaPorRangeDeDataCadastro(String dataInicio, String dataFim) {

            List<AbastecimentoEntity> abastecimentos = repository.buscaPorRangeDeDataCadastro(
                    (LocalDate.parse(dataInicio)).atTime(0, 0),
                    (LocalDate.parse(dataFim)).atTime(23, 59, 59, 999999999));

            if (!abastecimentos.isEmpty())
                return abastecimentos.stream().map(x -> modelMapper.mapper().map(x, AbastecimentoDTO.class)).collect(Collectors.toList());
            throw new ObjectNotFoundException("Não existe nenhum abastecimento cadastrado no range de datas indicado");

    }

    public AbastecimentoDTO buscaPorId(Long id) {
        if (repository.findById(id).isPresent()) {
            return modelMapper.mapper().map(repository.findById(id).get(), AbastecimentoDTO.class);
        }
        throw new ObjectNotFoundException("Não existe nenhum abastecimento cadastrado no banco de dados com o id " + id);
    }

}
