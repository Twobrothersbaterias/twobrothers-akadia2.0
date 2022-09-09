package br.com.twobrothers.frontend.services;

import br.com.twobrothers.frontend.models.dto.EntradaOrdemDTO;
import br.com.twobrothers.frontend.models.entities.AbastecimentoEntity;
import br.com.twobrothers.frontend.models.entities.ProdutoEstoqueEntity;
import br.com.twobrothers.frontend.models.enums.TipoEntradaEnum;
import br.com.twobrothers.frontend.models.enums.TipoProdutoEnum;
import br.com.twobrothers.frontend.repositories.AbastecimentoRepository;
import br.com.twobrothers.frontend.repositories.ProdutoEstoqueRepository;
import br.com.twobrothers.frontend.repositories.services.exceptions.InvalidRequestException;
import br.com.twobrothers.frontend.repositories.services.exceptions.ObjectNotFoundException;
import br.com.twobrothers.frontend.services.enums.OperacaoEstoque;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Gabriel Lagrota
 * @email gabriellagrota23@gmail.com
 * @phone (11)97981-5415
 * @github https://github.com/LagrotaGabriel
 * @version 1.0
 * @since 30-08-22
 */
@Service
public class GerenciamentoEstoqueService {

    @Autowired
    ProdutoEstoqueRepository produtoEstoqueRepository;

    @Autowired
    AbastecimentoRepository abastecimentoRepository;

    public void distribuiParaOperacaoCorreta(EntradaOrdemDTO entradaOrdemDTO, OperacaoEstoque operacaoEstoque) {
        if (entradaOrdemDTO.getTipoEntrada().equals(TipoEntradaEnum.COMUM) && operacaoEstoque.equals(OperacaoEstoque.CRIACAO)) {
            criacaoDeOrdemComum(entradaOrdemDTO);
        } else if (entradaOrdemDTO.getTipoEntrada().equals(TipoEntradaEnum.TROCA) && operacaoEstoque.equals(OperacaoEstoque.CRIACAO)) {
            criacaoDeOrdemDeTroca(entradaOrdemDTO);
        } else if (entradaOrdemDTO.getTipoEntrada().equals(TipoEntradaEnum.COMUM) && operacaoEstoque.equals(OperacaoEstoque.REMOCAO)) {
            remocaoDeOrdemComum(entradaOrdemDTO);
        } else if (entradaOrdemDTO.getTipoEntrada().equals(TipoEntradaEnum.TROCA) && operacaoEstoque.equals(OperacaoEstoque.REMOCAO)) {
            remocaoDeOrdemDeTroca(entradaOrdemDTO);
        }
    }

    public void criacaoDeOrdemComum(EntradaOrdemDTO entradaOrdemDTO) {
        if (entradaOrdemDTO.getProduto().getTipoProduto().equals(TipoProdutoEnum.BATERIA)) {
            ProdutoEstoqueEntity produtoEstoque = produtoEstoqueRepository.buscaPorSigla(entradaOrdemDTO.getProduto().getSigla()).get();
            produtoEstoqueRepository.save(produtoEstoque);
        } else {
            throw new InvalidRequestException("A ordem do tipo COMUM deve possuir um produto do tipo BATERIA");
        }
    }

    public void remocaoDeOrdemComum(EntradaOrdemDTO entradaOrdemDTO) {
        verificaSeExiste(entradaOrdemDTO);
        if (entradaOrdemDTO.getProduto().getTipoProduto().equals(TipoProdutoEnum.BATERIA)) {
            ProdutoEstoqueEntity produtoEstoque = produtoEstoqueRepository.buscaPorSigla(entradaOrdemDTO.getProduto().getSigla()).get();
            produtoEstoque.setQuantidade(produtoEstoque.getQuantidade() + entradaOrdemDTO.getQuantidade());
            produtoEstoqueRepository.save(produtoEstoque);
        } else {
            throw new InvalidRequestException("A ordem do tipo COMUM deve possuir um produto do tipo BATERIA");
        }
    }

    public void criacaoDeOrdemDeTroca(EntradaOrdemDTO entradaOrdemDTO) {
        if (verificaSeExiste(entradaOrdemDTO)) {
            if (entradaOrdemDTO.getProduto().getTipoProduto().equals(TipoProdutoEnum.SUCATA)) {
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
                                0L,
                                null,
                                produtoEstoque,
                                null);
                abastecimentoRepository.save(abastecimento);
            } else {
                throw new InvalidRequestException("A ordem do tipo TROCA deve possuir um produto do tipo SUCATA");
            }
        }
    }

    public void remocaoDeOrdemDeTroca(EntradaOrdemDTO entradaOrdemDTO) {
        if (entradaOrdemDTO.getProduto().getTipoProduto().equals(TipoProdutoEnum.SUCATA)) {
            verificaSeExiste(entradaOrdemDTO);
            ProdutoEstoqueEntity produtoEstoque = produtoEstoqueRepository.buscaPorSigla(entradaOrdemDTO.getProduto().getSigla()).get();
            produtoEstoque.setQuantidade(produtoEstoque.getQuantidade() - entradaOrdemDTO.getQuantidade());
            produtoEstoqueRepository.save(produtoEstoque);
        } else {
            throw new InvalidRequestException("A ordem do tipo TROCA deve possuir um produto do tipo SUCATA");
        }
    }

    public boolean verificaSeExiste(EntradaOrdemDTO entradaOrdemDTO) {
        Optional<ProdutoEstoqueEntity> produtoEstoqueEntityOptional =
                produtoEstoqueRepository.buscaPorSigla(entradaOrdemDTO.getProduto().getSigla());
        if (produtoEstoqueEntityOptional.isPresent()) return true;
        throw new ObjectNotFoundException("Não existe nenhum produto com a sigla " + entradaOrdemDTO.getProduto().getSigla());
    }

    public void validacoesEmMassa(List<EntradaOrdemDTO> entradas) {
        verificaSeExisteEmMassa(entradas);
        verificaTiposEmMassa(entradas);
        verificaSePossuiQuantidadeEmEstoqueEmMassa(entradas);
    }

    public void verificaSeExisteEmMassa(List<EntradaOrdemDTO> entradas) {

        for (EntradaOrdemDTO entradaOrdemDTO : entradas) {
            if (produtoEstoqueRepository.buscaPorSigla(entradaOrdemDTO.getProduto().getSigla()).isEmpty())
                throw new ObjectNotFoundException("O produto de sigla "
                        + entradaOrdemDTO.getProduto().getSigla() + " não existe na base de dados");
        }

    }

    public void verificaTiposEmMassa(List<EntradaOrdemDTO> entradas) {

        for (EntradaOrdemDTO entradaOrdemDTO : entradas) {
            if (entradaOrdemDTO.getTipoEntrada().equals(TipoEntradaEnum.COMUM) && entradaOrdemDTO.getProduto().getTipoProduto().equals(TipoProdutoEnum.SUCATA)) {
                throw new InvalidRequestException("O produto inserido em uma ordem de venda comum não pode ser uma sucata");
            } else if (entradaOrdemDTO.getTipoEntrada().equals(TipoEntradaEnum.TROCA) && entradaOrdemDTO.getProduto().getTipoProduto().equals(TipoProdutoEnum.BATERIA)) {
                throw new InvalidRequestException("O produto inserido em uma ordem de venda de troca não pode ser uma bateria");
            }
        }

    }

    public void verificaSePossuiQuantidadeEmEstoqueEmMassa(List<EntradaOrdemDTO> entradas) {

        List<ProdutoEstoqueEntity> produtosInseridos = new ArrayList<>();

        ProdutoEstoqueEntity produtoEstoque = new ProdutoEstoqueEntity();

        for (EntradaOrdemDTO entradaOrdemDTO : entradas) {
            if (entradaOrdemDTO.getProduto().getTipoProduto().equals(TipoProdutoEnum.BATERIA)) {
                produtoEstoque = produtoEstoqueRepository.buscaPorSigla(entradaOrdemDTO.getProduto().getSigla()).get();
                produtosInseridos.add(produtoEstoque);
            }
        }

        for (EntradaOrdemDTO entradaOrdemDTO : entradas) {
            if (entradaOrdemDTO.getProduto().getTipoProduto().equals(TipoProdutoEnum.BATERIA)) {
                ProdutoEstoqueEntity produtoEstoqueEntity = produtosInseridos.get(produtosInseridos.indexOf(produtoEstoque));
                produtoEstoqueEntity.setQuantidade(produtoEstoqueEntity.getQuantidade() - entradaOrdemDTO.getQuantidade());
                if (produtoEstoqueEntity.getQuantidade() < 0)
                    throw new InvalidRequestException("A quantidade passada pela ordem é maior do que a que existe em estoque");
            }
        }

    }

}
