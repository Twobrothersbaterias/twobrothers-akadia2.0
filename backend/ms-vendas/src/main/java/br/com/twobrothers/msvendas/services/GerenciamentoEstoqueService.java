package br.com.twobrothers.msvendas.services;

import br.com.twobrothers.msvendas.exceptions.InvalidRequestException;
import br.com.twobrothers.msvendas.models.dto.EntradaOrdemDTO;
import br.com.twobrothers.msvendas.models.entities.ProdutoEstoqueEntity;
import br.com.twobrothers.msvendas.repositories.ProdutoEstoqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GerenciamentoEstoqueService {

    @Autowired
    ProdutoEstoqueRepository produtoEstoqueRepository;

    public void reduzQuantidadeEstoque(EntradaOrdemDTO entradaOrdemDTO) {

        if (verificaSeExiste(entradaOrdemDTO) && verificaSePossuiQuantidadeEmEstoque(entradaOrdemDTO)) {
            ProdutoEstoqueEntity produtoEstoque = produtoEstoqueRepository.findById(entradaOrdemDTO.getProdutoEstoqueId()).get();
            produtoEstoque.setQuantidade(produtoEstoque.getQuantidade() - entradaOrdemDTO.getQuantidade());
            produtoEstoqueRepository.save(produtoEstoque);
        }

    }

    public boolean verificaSePossuiQuantidadeEmEstoque(EntradaOrdemDTO entradaOrdemDTO) {
        if (verificaSeExiste(entradaOrdemDTO)) {
            ProdutoEstoqueEntity produtoEstoque = produtoEstoqueRepository.findById(entradaOrdemDTO.getProdutoEstoqueId()).get() ;
            if (entradaOrdemDTO.getQuantidade() <= produtoEstoque.getQuantidade()) return true;
        }
        throw new InvalidRequestException("A quantidade passada pela ordem é maior do que a que existe em estoque");
    }

    public boolean verificaSeExiste(EntradaOrdemDTO entradaOrdemDTO) {
        return produtoEstoqueRepository.findById(entradaOrdemDTO.getProdutoEstoqueId()).isPresent();
    }

}
