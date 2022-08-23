package br.com.twobrothers.msvendas.services;

import br.com.twobrothers.msvendas.config.ModelMapperConfig;
import br.com.twobrothers.msvendas.exceptions.InvalidRequestException;
import br.com.twobrothers.msvendas.exceptions.ObjectNotFoundException;
import br.com.twobrothers.msvendas.models.dto.ProdutoEstoqueDTO;
import br.com.twobrothers.msvendas.models.entities.ProdutoEstoqueEntity;
import br.com.twobrothers.msvendas.models.enums.ValidationType;
import br.com.twobrothers.msvendas.repositories.ProdutoEstoqueRepository;
import br.com.twobrothers.msvendas.validations.ProdutoEstoqueValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProdutoEstoqueService {

    @Autowired
    ProdutoEstoqueRepository repository;

    @Autowired
    ModelMapperConfig modelMapper;

    ProdutoEstoqueValidation validation = new ProdutoEstoqueValidation();

    public ProdutoEstoqueDTO criaNovo(ProdutoEstoqueDTO produto) {
        if (validation.validaCorpoRequisicao(produto, repository, ValidationType.CREATE)) {
            produto.setDataCadastro(LocalDateTime.now());
            produto.setQuantidade(0);
            if (produto.getQuantidadeMinima() == null) produto.setQuantidadeMinima(0);
            return modelMapper.mapper().map(repository.save(modelMapper.mapper()
                    .map(produto, ProdutoEstoqueEntity.class)), ProdutoEstoqueDTO.class);
        }
        throw new InvalidRequestException("A validação da requisição falhou. Verificar corpo da requisição.");
    }

    public List<ProdutoEstoqueDTO> buscaTodos() {
        if (!repository.findAll().isEmpty()) return repository.findAll().stream()
                .map(x -> modelMapper.mapper().map(x, ProdutoEstoqueDTO.class)).collect(Collectors.toList());
        throw new ObjectNotFoundException("Não existe nenhum produto salvo na base de dados.");
    }

    public List<ProdutoEstoqueDTO> buscaPorPaginacao(Pageable paginacao) {
        if (!repository.findAll(paginacao).isEmpty()) return repository.findAll(paginacao)
                .getContent().stream().map(x -> modelMapper.mapper().map(x, ProdutoEstoqueDTO.class)).collect(Collectors.toList());
        throw new ObjectNotFoundException("Não existe nenhum produto cadastrado na página indicada");
    }

    public List<ProdutoEstoqueDTO> buscaPorRangeDeDataCadastro(String dataInicio, String dataFim) {

        List<ProdutoEstoqueEntity> produtos = repository.buscaPorRangeDeDataCadastro(
                (LocalDate.parse(dataInicio)).atTime(0, 0),
                (LocalDate.parse(dataFim)).atTime(23, 59, 59, 999999999));

        if (!produtos.isEmpty())
            return produtos.stream().map(x -> modelMapper.mapper().map(x, ProdutoEstoqueDTO.class)).collect(Collectors.toList());
        throw new ObjectNotFoundException("Não existe nenhum produto cadastrado no range de datas indicado");

    }

    public ProdutoEstoqueDTO buscaPorSigla(String sigla) {
        if (repository.buscaPorSigla(sigla).isPresent())
            return modelMapper.mapper().map(repository.buscaPorSigla(sigla).get(), ProdutoEstoqueDTO.class);
        throw new ObjectNotFoundException("Nenhum produto foi encontrado através do atributo sigla enviado.");
    }

    public ProdutoEstoqueDTO buscaPorId(Long id) {
        if (repository.findById(id).isPresent()) {
            return modelMapper.mapper().map(repository.findById(id).get(), ProdutoEstoqueDTO.class);
        }
        throw new ObjectNotFoundException("Não existe nenhum produto cadastrado no banco de dados com o id " + id);
    }

    public ProdutoEstoqueDTO atualizaPorId(Long id, ProdutoEstoqueDTO produto) {

        Optional<ProdutoEstoqueEntity> produtoOptional = repository.findById(id);

        if (produtoOptional.isPresent()) {

            ProdutoEstoqueEntity produtoAtualizado = produtoOptional.get();

            if (repository.buscaPorSigla(produto.getSigla()).isPresent()
                    && repository.buscaPorSigla(produto.getSigla()).get().getId() != produtoOptional.get().getId()) {
                throw new InvalidRequestException("O produto com a sigla " + produto.getSigla() + " já existe na base de dados");
            }

            if (validation.validaCorpoRequisicao(produto, repository, ValidationType.UPDATE)) {

                produtoAtualizado.setSigla(produto.getSigla());
                produtoAtualizado.setEspecificacao(produto.getEspecificacao());
                produtoAtualizado.setMarcaBateria(produto.getMarcaBateria());
                produtoAtualizado.setTipoProduto(produto.getTipoProduto());

                if (produto.getQuantidadeMinima() == null) produtoAtualizado.setQuantidadeMinima(0);
                else produtoAtualizado.setQuantidadeMinima(produto.getQuantidadeMinima());

                return modelMapper.mapper().map(repository.save(produtoAtualizado), ProdutoEstoqueDTO.class);

            }

            throw new InvalidRequestException("Corpo da requisição inválido");

        }
        throw new ObjectNotFoundException("Não existe nenhum produto cadastrado com o id " + id);

    }

    public Boolean deletaPorId(Long id) {
        Optional<ProdutoEstoqueEntity> produtoOptional = repository.findById(id);
        if (produtoOptional.isPresent()) {
            repository.deleteById(id);
            return true;
        }
        throw new ObjectNotFoundException("Não existe nenhum produto cadastrado com o id " + id);
    }

}
