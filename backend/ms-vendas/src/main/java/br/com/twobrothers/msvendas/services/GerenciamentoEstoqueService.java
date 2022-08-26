package br.com.twobrothers.msvendas.services;

import br.com.twobrothers.msvendas.exceptions.InvalidRequestException;
import br.com.twobrothers.msvendas.exceptions.ObjectNotFoundException;
import br.com.twobrothers.msvendas.models.dto.EntradaOrdemDTO;
import br.com.twobrothers.msvendas.models.entities.AbastecimentoEntity;
import br.com.twobrothers.msvendas.models.entities.ProdutoEstoqueEntity;
import br.com.twobrothers.msvendas.models.enums.TipoEntradaEnum;
import br.com.twobrothers.msvendas.models.enums.TipoProdutoEnum;
import br.com.twobrothers.msvendas.repositories.AbastecimentoRepository;
import br.com.twobrothers.msvendas.repositories.ProdutoEstoqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class GerenciamentoEstoqueService {

    @Autowired
    ProdutoEstoqueRepository produtoEstoqueRepository;

    @Autowired
    AbastecimentoRepository abastecimentoRepository;

    public void distribuiParaTrocaOuProduto(EntradaOrdemDTO entradaOrdemDTO) {
        if (entradaOrdemDTO.getTipoEntrada().equals(TipoEntradaEnum.COMUM)) {
            reduzQuantidadeEstoque(entradaOrdemDTO);
        } else {
            aumentaQuantidadeEstoque(entradaOrdemDTO);
        }
    }

    public void reduzQuantidadeEstoque(EntradaOrdemDTO entradaOrdemDTO) {
        if (verificaSeExiste(entradaOrdemDTO) && verificaSePossuiQuantidadeEmEstoque(entradaOrdemDTO)) {
            ProdutoEstoqueEntity produtoEstoque = produtoEstoqueRepository.buscaPorSigla(entradaOrdemDTO.getProduto().getSigla()).get();
            produtoEstoque.setQuantidade(produtoEstoque.getQuantidade() - entradaOrdemDTO.getQuantidade());
            produtoEstoqueRepository.save(produtoEstoque);
        }
    }

    public void aumentaQuantidadeEstoque(EntradaOrdemDTO entradaOrdemDTO) {
        if (verificaSeExiste(entradaOrdemDTO)) {
            ProdutoEstoqueEntity produtoEstoque = produtoEstoqueRepository.buscaPorSigla(entradaOrdemDTO.getProduto().getSigla()).get();
            produtoEstoque.setQuantidade(produtoEstoque.getQuantidade() + entradaOrdemDTO.getQuantidade());
            produtoEstoqueRepository.save(produtoEstoque);

            AbastecimentoEntity abastecimento = new AbastecimentoEntity
                    (null,
                    LocalDateTime.now(),
                    entradaOrdemDTO.getQuantidade(),
                    0.0,
                    0.0,
                    "Abastecimento de troca",
                    entradaOrdemDTO.getOrdem().getIdUsuarioResponsavel(),
                    entradaOrdemDTO.getOrdem().getFormaPagamento(),
                    produtoEstoque,
                    null);
            abastecimentoRepository.save(abastecimento);
        }
    }

    public boolean verificaSePossuiQuantidadeEmEstoque(EntradaOrdemDTO entradaOrdemDTO) {
        if (verificaSeExiste(entradaOrdemDTO)) {
            ProdutoEstoqueEntity produtoEstoque = produtoEstoqueRepository.buscaPorSigla(entradaOrdemDTO.getProduto().getSigla()).get();
            if (entradaOrdemDTO.getQuantidade() <= produtoEstoque.getQuantidade()) return true;
        }
        throw new InvalidRequestException("A quantidade passada pela ordem é maior do que a que existe em estoque");
    }

    public boolean verificaSeExiste(EntradaOrdemDTO entradaOrdemDTO) {

        Optional<ProdutoEstoqueEntity> produtoEstoqueEntityOptional =
                produtoEstoqueRepository.buscaPorSigla(entradaOrdemDTO.getProduto().getSigla());

        if (produtoEstoqueEntityOptional.isPresent()) {

            ProdutoEstoqueEntity produtoEstoqueEntity = produtoEstoqueEntityOptional.get();
            if (entradaOrdemDTO.getTipoEntrada().equals(TipoEntradaEnum.COMUM) && produtoEstoqueEntity.getTipoProduto().equals(TipoProdutoEnum.BATERIA)
                    || entradaOrdemDTO.getTipoEntrada().equals(TipoEntradaEnum.TROCA) && produtoEstoqueEntity.getTipoProduto().equals(TipoProdutoEnum.SUCATA)) {
                return true;
            }
            throw new InvalidRequestException("O produto não possui o tipo condizente com o tipo da entrada da ordem");

        }
        throw new ObjectNotFoundException("Não existe nenhum produto com a sigla " + entradaOrdemDTO.getProduto().getSigla());

    }

}
