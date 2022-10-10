package br.com.twobrothers.frontend.validations;

import br.com.twobrothers.frontend.models.dto.AbastecimentoDTO;
import br.com.twobrothers.frontend.repositories.services.exceptions.InvalidRequestException;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static br.com.twobrothers.frontend.utils.RegexPatterns.QUANTIDADE_REGEX;

/**
 * @author Gabriel Lagrota
 * @email gabriellagrota23@gmail.com
 * @phone (11)97981-5415
 * @github https://github.com/LagrotaGabriel
 * @version 1.0
 * @since 30-08-22
 */
@Slf4j
public class AbastecimentoValidation {

    public void validaCorpoRequisicao(AbastecimentoDTO abastecimento) {
        validaSePossuiAtributosNulos(abastecimento);
        validaAtributoQuantidade(abastecimento.getQuantidade());
        validaAtributoCustoTotal(abastecimento.getCustoTotal());
        if (abastecimento.getObservacao() != null) validaAtributoObservacao(abastecimento.getObservacao());
        log.warn("[VALIDAÇÃO - ABASTECIMENTO] Validação do objeto abastecimento finalizada com sucesso");
    }

    public void validaSePossuiAtributosNulos(AbastecimentoDTO abastecimento) {

        log.info("[VALIDAÇÃO - ABASTECIMENTO] Inicializando validação de atributos obrigatórios nulos...");
        List<String> atributosNulos = new ArrayList<>();

        if (abastecimento.getQuantidade() == null) atributosNulos.add("Quantidade");
        if (abastecimento.getCustoTotal() == null) atributosNulos.add("Custo total");
        if (abastecimento.getFormaPagamento() == null) atributosNulos.add("Forma de pagamento");

        if (!atributosNulos.isEmpty())
            throw new InvalidRequestException("Validação do abastecimento falhou. A inserção de um ou mais atributos " +
                    "obrigatórios é necessária no corpo da requisição: " + atributosNulos);

        log.warn("Validação de atributos nulos OK");
    }

    public void validaAtributoQuantidade(Integer quantidade) {
        log.info("[VALIDAÇÃO - ABASTECIMENTO] Inicializando validação do atributo quantidade...");
        if (!quantidade.toString().matches(QUANTIDADE_REGEX))
            throw new InvalidRequestException("Validação do abastecimento falhou. Motivo: A quantidade deve estar entre 1 e 999");
        log.warn("Validação do atributo quantidade OK");
    }

    public void validaAtributoCustoTotal(Double custoTotal) {
        log.info("[VALIDAÇÃO - ABASTECIMENTO] Inicializando validação do atributo custoTotal...");
        if (custoTotal < 0.0 || custoTotal > 999999.0)
            throw new InvalidRequestException("Validação do abastecimento falhou. Motivo: O custo total deve deve estar entre R$ 1,00 e R$ 999.999,00");
        log.warn("Validação do atributo custoTotal OK");
    }

    public void validaAtributoObservacao(String observacao) {
        log.info("[VALIDAÇÃO - ABASTECIMENTO] Inicializando validação do atributo observacao...");
        if (observacao.length() > 100)
            throw new InvalidRequestException("Validação do abastecimento falhou. Motivo: O campo observação deve possuir " +
                    "até 100 caracteres. Quantidade atual: " + observacao.length());
        log.warn("Validação do atributo observacao OK");
    }

    public void validaRangeData(String inicio, String fim) {

        LocalDate dataInicio = LocalDate.parse(inicio);
        LocalDate dataFim = LocalDate.parse(fim);

        if (dataInicio.isAfter(dataFim))
            throw new InvalidRequestException("O conteúdo do campo data início não pode ser anterior ao campo data fim");

    }

}
