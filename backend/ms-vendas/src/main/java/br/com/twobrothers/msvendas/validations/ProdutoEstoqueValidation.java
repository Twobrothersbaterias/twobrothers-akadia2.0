package br.com.twobrothers.msvendas.validations;

import br.com.twobrothers.msvendas.exceptions.InvalidRequestException;
import br.com.twobrothers.msvendas.models.dto.ProdutoEstoqueDTO;
import br.com.twobrothers.msvendas.models.enums.ValidationType;
import br.com.twobrothers.msvendas.repositories.ProdutoEstoqueRepository;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Gabriel Lagrota
 * @email gabriellagrota23@gmail.com
 * @phone (11)97981-5415
 * @github https://github.com/LagrotaGabriel
 * @version 1.0
 * @since 30-08-22
 */
@Slf4j
public class ProdutoEstoqueValidation {

    public void validaCorpoRequisicao(ProdutoEstoqueDTO produtoEstoque,
                                      ProdutoEstoqueRepository repository,
                                      ValidationType validationType) {

        validaSePossuiAtributosNulos(produtoEstoque);
        if (validationType == ValidationType.CREATE) validaSeSiglaJaExiste(produtoEstoque.getSigla(), repository);
        validaAtributoSigla(produtoEstoque.getSigla());
        validaAtributoMarcaBateria(produtoEstoque.getMarcaBateria());
        validaAtributoEspecificacao(produtoEstoque.getEspecificacao());
        validaAtributoQuantidadeMinima(produtoEstoque.getQuantidadeMinima());
        log.warn("[VALIDAÇÃO - PRODUTO] Validação do objeto ProdutoEstoqueDTO finalizada com sucesso");
    }

    public void validaSePossuiAtributosNulos(ProdutoEstoqueDTO produtoEstoque) {
        log.info("[VALIDAÇÃO - PRODUTO] Inicializando validação de atributos obrigatórios nulos...");
        List<String> atributosNulos = new ArrayList<>();

        if (produtoEstoque.getSigla() == null) atributosNulos.add("sigla");
        if (produtoEstoque.getMarcaBateria() == null) atributosNulos.add("marcaBateria");
        if (produtoEstoque.getEspecificacao() == null) atributosNulos.add("especificacao");
        if (produtoEstoque.getIdUsuarioResponsavel() == null) atributosNulos.add("idUsuarioResponsavel");
        if (produtoEstoque.getTipoProduto() == null) atributosNulos.add("tipoProduto");

        if (!atributosNulos.isEmpty())
            throw new InvalidRequestException("Validação do produto falhou. A inserção de um ou mais atributos " +
                    "obrigatórios é necessária no corpo da requisição: " + atributosNulos);

        log.warn("Validação de atributos nulos OK");
    }

    public void validaSeSiglaJaExiste(String sigla, ProdutoEstoqueRepository repository) {
        log.info("[VALIDAÇÃO - PRODUTO] Inicializando se a sigla já existe...");
        if (repository.buscaPorSigla(sigla).isPresent())
            throw new InvalidRequestException("Validação do produto falhou. Motivo: já existe um produto salvo com a sigla " + sigla);
        log.warn("Validação de disponibilidade de sigla OK");
    }

    public void validaAtributoSigla(String sigla) {
        log.info("[VALIDAÇÃO - PRODUTO] Inicializando validação do atributo sigla...");
        if (sigla.length() > 10)
            throw new InvalidRequestException("Validação da sigla falhou. Motivo: A sigla deve possuir no máximo 10 caracteres");
        log.warn("Validação do atributo sigla OK");
    }

    public void validaAtributoMarcaBateria(String marcaBateria) {
        log.info("[VALIDAÇÃO - PRODUTO] Inicializando validação do atributo marcaBateria...");
        if (marcaBateria.length() > 20)
            throw new InvalidRequestException("Validação da marca da bateria falhou. " +
                    "Motivo: A marca da bateria deve possuir no máximo 20 caracteres");
        log.warn("Validação do atributo marcaBateria OK");
    }

    public void validaAtributoEspecificacao(String especificacao) {
        log.info("[VALIDAÇÃO - PRODUTO] Inicializando validação do atributo especificacao...");
        if (especificacao.length() > 30)
            throw new InvalidRequestException("Validação da especificação da bateria falhou. " +
                    "Motivo: A especificação da bateria deve possuir no máximo 30 caracteres");
        log.warn("Validação do atributo especificacao OK");
    }

    public void validaAtributoQuantidadeMinima(Integer quantidadeMinima) {
        log.info("[VALIDAÇÃO - PRODUTO] Inicializando validação do atributo quantidadeMinima...");
        if (quantidadeMinima != null && quantidadeMinima > 200 || quantidadeMinima != null && quantidadeMinima < 0)
            throw new InvalidRequestException("Validação da quantidade mínima falhou. " +
                    "Motivo: A quantidade mínima deve ser um valor numérico maior ou igual a 0 e menor ou igual a 200");
        log.warn("Validação do atributo quantidadeMinima OK");
    }
}
