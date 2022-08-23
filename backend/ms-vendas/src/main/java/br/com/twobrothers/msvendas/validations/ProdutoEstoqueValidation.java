package br.com.twobrothers.msvendas.validations;

import br.com.twobrothers.msvendas.exceptions.InvalidRequestException;
import br.com.twobrothers.msvendas.models.dto.ProdutoEstoqueDTO;
import br.com.twobrothers.msvendas.models.enums.ValidationType;
import br.com.twobrothers.msvendas.repositories.ProdutoEstoqueRepository;

public class ProdutoEstoqueValidation {

    public boolean validaCorpoRequisicao(ProdutoEstoqueDTO produtoEstoque,
                                         ProdutoEstoqueRepository repository,
                                         ValidationType validationType) {

        validaSePossuiAtributosNulos(produtoEstoque);
        if (validationType == ValidationType.CREATE) validaSeSiglaJaExiste(produtoEstoque.getSigla(), repository);
        validaAtributoSigla(produtoEstoque.getSigla());
        validaAtributoMarcaBateria(produtoEstoque.getMarcaBateria());
        validaAtributoEspecificacao(produtoEstoque.getEspecificacao());
        validaAtributoQuantidadeMinima(produtoEstoque.getQuantidadeMinima());
        return true;
    }

    public boolean validaSePossuiAtributosNulos(ProdutoEstoqueDTO produtoEstoque) {
        if (produtoEstoque.getSigla() != null &&
                produtoEstoque.getMarcaBateria() != null &&
                produtoEstoque.getEspecificacao() != null &&
                produtoEstoque.getIdUsuarioResponsavel() != null &&
                produtoEstoque.getTipoProduto() != null) return true;
        throw new InvalidRequestException("Validação do produto falhou. Motivo: um ou mais atributos recebido(s) " +
                "na requisição são nulos");
    }

    public boolean validaSeSiglaJaExiste(String sigla, ProdutoEstoqueRepository repository) {
        if (repository.buscaPorSigla(sigla).isEmpty()) return true;
        throw new InvalidRequestException("Validação do produto falhou. Motivo: já existe um produto salvo com a sigla " + sigla);
    }

    public boolean validaAtributoSigla(String sigla) {
        if (sigla.length() <= 10) return true;
        throw new InvalidRequestException("Validação da sigla falhou. Motivo: A sigla deve possuir no máximo 10 caracteres");
    }

    public boolean validaAtributoMarcaBateria(String marcaBateria) {
        if (marcaBateria.length() <= 20) return true;
        throw new InvalidRequestException("Validação da marca da bateria falhou. " +
                "Motivo: A marca da bateria deve possuir no máximo 20 caracteres");
    }

    public boolean validaAtributoEspecificacao(String especificacao) {
        if (especificacao.length() <= 30) return true;
        throw new InvalidRequestException("Validação da especificação da bateria falhou. " +
                "Motivo: A especificação da bateria deve possuir no máximo 30 caracteres");
    }

    public boolean validaAtributoQuantidadeMinima(Integer quantidadeMinima) {
        if (quantidadeMinima != null && quantidadeMinima > 200 || quantidadeMinima != null && quantidadeMinima < 0) {
            throw new InvalidRequestException("Validação da quantidade mínima falhou. " +
                    "Motivo: A quantidade mínima deve ser um valor numérico maior ou igual a 0 e menor ou igual a 200");
        }
        return true;
    }

}
