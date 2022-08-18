package br.com.twobrothers.msbalanco.validations;

import br.com.twobrothers.msbalanco.models.dto.PatrimonioDTO;
import br.com.twobrothers.msbalanco.services.exceptions.InvalidRequestException;

import static br.com.twobrothers.msbalanco.utils.RegexPatterns.DATE_REGEX;

public class PatrimonioValidation {

    public boolean validaCorpoDaRequisicao(PatrimonioDTO patrimonio) {
        validaSePossuiAtributosNulos(patrimonio);
        if (patrimonio.getDataAgendamentoPatrimonio() != null) {
            validaAtributoDataAgendamento(patrimonio.getDataAgendamentoPatrimonio());
        }
        return true;
    }

    public boolean validaSePossuiAtributosNulos(PatrimonioDTO patrimonio) {
        if (patrimonio.getNome() != null
                && patrimonio.getTipoPatrimonio() != null
                && patrimonio.getStatusPatrimonio() != null
                && patrimonio.getValor() != null)
            return true;
        throw new InvalidRequestException("Validação do patrimônio falhou. Motivo: um ou mais atributos recebido(s) " +
                "na requisição são nulos");
    }

    public boolean validaAtributoDataAgendamento(String data) {
        if (data.matches(DATE_REGEX)) return true;
        throw new InvalidRequestException
                ("Validação do patrimoônio falhou. Motivo: o padrão da data de agendamento é inválido");
    }

}
