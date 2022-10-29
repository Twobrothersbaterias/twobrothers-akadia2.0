package br.com.twobrothers.frontend.services;

import br.com.twobrothers.frontend.models.dto.EntradaOrdemDTO;
import br.com.twobrothers.frontend.models.entities.ProdutoEstoqueEntity;
import br.com.twobrothers.frontend.models.enums.TipoProdutoEnum;
import br.com.twobrothers.frontend.repositories.AbastecimentoRepository;
import br.com.twobrothers.frontend.repositories.ProdutoEstoqueRepository;
import br.com.twobrothers.frontend.repositories.services.exceptions.InvalidRequestException;
import br.com.twobrothers.frontend.repositories.services.exceptions.ObjectNotFoundException;
import br.com.twobrothers.frontend.services.enums.OperacaoEstoque;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Gabriel Lagrota
 * @version 1.0
 * @email gabriellagrota23@gmail.com
 * @phone (11)97981-5415
 * @github https://github.com/LagrotaGabriel
 * @since 30-08-22
 */
@Service
public class GerenciamentoEstoqueService {

    @Autowired
    ProdutoEstoqueRepository produtoEstoqueRepository;

    @Autowired
    AbastecimentoRepository abastecimentoRepository;

    public void distribuiParaOperacaoCorreta(EntradaOrdemDTO entradaOrdemDTO, OperacaoEstoque operacaoEstoque) {
        if (operacaoEstoque.equals(OperacaoEstoque.CRIACAO)) {
            criacaoDeOrdemComum(entradaOrdemDTO);
        }
        else if (operacaoEstoque.equals(OperacaoEstoque.REMOCAO)) {
            remocaoDeOrdemComum(entradaOrdemDTO);
        }
    }

    public void criacaoDeOrdemComum(EntradaOrdemDTO entradaOrdemDTO) {

        Optional<ProdutoEstoqueEntity> produtoEstoqueEntityOptional =
                produtoEstoqueRepository.buscaPorSigla(entradaOrdemDTO.getProduto().getSigla());

        if (produtoEstoqueEntityOptional.isPresent()) {
            ProdutoEstoqueEntity produtoEstoque = produtoEstoqueEntityOptional.get();
            produtoEstoqueRepository.save(produtoEstoque);
        }


    }

    public void remocaoDeOrdemComum(EntradaOrdemDTO entradaOrdemDTO) {
        verificaSeExiste(entradaOrdemDTO);
        ProdutoEstoqueEntity produtoEstoque = produtoEstoqueRepository.buscaPorSigla(entradaOrdemDTO.getProduto().getSigla()).get();
        produtoEstoque.setQuantidade(produtoEstoque.getQuantidade() + entradaOrdemDTO.getQuantidade());
        produtoEstoque.setCustoTotal(produtoEstoque.getQuantidade() * produtoEstoque.getCustoUnitario());
        produtoEstoqueRepository.save(produtoEstoque);
    }

    public boolean verificaSeExiste(EntradaOrdemDTO entradaOrdemDTO) {
        if(entradaOrdemDTO.getProduto() != null) {
            Optional<ProdutoEstoqueEntity> produtoEstoqueEntityOptional =
                    produtoEstoqueRepository.buscaPorSigla(entradaOrdemDTO.getProduto().getSigla());
            if (produtoEstoqueEntityOptional.isPresent()) return true;
        }
        else return true;

        throw new ObjectNotFoundException("Não existe nenhum produto com a sigla " + entradaOrdemDTO.getProduto().getSigla());
    }

    public void validacoesEmMassa(List<EntradaOrdemDTO> entradas) {
        verificaSeExisteEmMassa(entradas);
        verificaSePossuiQuantidadeEmEstoqueEmMassa(entradas);
    }

    public void verificaSeExisteEmMassa(List<EntradaOrdemDTO> entradas) {
        for (EntradaOrdemDTO entradaOrdemDTO : entradas) verificaSeExiste(entradaOrdemDTO);
    }

    public void verificaSePossuiQuantidadeEmEstoqueEmMassa(List<EntradaOrdemDTO> entradas) {

        List<ProdutoEstoqueEntity> produtosInseridos = new ArrayList<>();

        ProdutoEstoqueEntity produtoEstoque = new ProdutoEstoqueEntity();

        for (EntradaOrdemDTO entradaOrdemDTO : entradas) {
            if (entradaOrdemDTO.getProduto() != null) {
                if (entradaOrdemDTO.getProduto().getTipoProduto().equals(TipoProdutoEnum.BATERIA)) {
                    produtoEstoque = produtoEstoqueRepository.buscaPorSigla(entradaOrdemDTO.getProduto().getSigla()).get();
                    produtosInseridos.add(produtoEstoque);
                }
            }
        }

        for (EntradaOrdemDTO entradaOrdemDTO : entradas) {
            if (entradaOrdemDTO.getProduto() != null) {
                if (entradaOrdemDTO.getProduto().getTipoProduto().equals(TipoProdutoEnum.BATERIA)) {
                    ProdutoEstoqueEntity produtoEstoqueEntity = produtosInseridos.get(produtosInseridos.indexOf(produtoEstoque));
                    produtoEstoqueEntity.setQuantidade(produtoEstoqueEntity.getQuantidade() - entradaOrdemDTO.getQuantidade());
                    produtoEstoqueEntity.setCustoTotal(produtoEstoqueEntity.getQuantidade() * produtoEstoqueEntity.getCustoUnitario());
                    if (produtoEstoqueEntity.getQuantidade() < 0)
                        throw new InvalidRequestException("A quantidade do produto" + produtoEstoqueEntity.getSigla() +
                                "passada pela ordem é maior do que a que existe em estoque");
                }
            }
        }

    }

}
