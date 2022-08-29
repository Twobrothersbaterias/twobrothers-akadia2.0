package br.com.twobrothers.msbalanco.validations;

import br.com.twobrothers.msbalanco.models.dto.PatrimonioDTO;
import br.com.twobrothers.msbalanco.services.exceptions.InvalidRequestException;

import java.time.LocalDate;

import static br.com.twobrothers.msbalanco.utils.RegexPatterns.DATE_REGEX;

public class PatrimonioValidation {

    public boolean validaCorpoDaRequisicao(PatrimonioDTO patrimonio) {
        validaSePossuiAtributosNulos(patrimonio);
        if (patrimonio.getDataAgendamento() != null) {
            validaAtributoDataAgendamento(patrimonio.getDataAgendamento());
        }
        return true;
    }

    public boolean validaSePossuiAtributosNulos(PatrimonioDTO patrimonio) {
        if (patrimonio.getNome() != null
                && patrimonio.getTipoPatrimonio() != null
                && patrimonio.getStatusPatrimonio() != null
                && patrimonio.getValor() != null
                && patrimonio.getIdUsuarioResponsavel() != null)
            return true;
        throw new InvalidRequestException("Validação do patrimônio falhou. Motivo: um ou mais atributos recebido(s) " +
                "na requisição são nulos");
    }

    public boolean validaAtributoDataAgendamento(String data) {

        LocalDate hoje = LocalDate.now();

        if (data.matches(DATE_REGEX)) {

            String dataDeAgendamento = data.replace("/", "-").split("-")[2] + "-"
                    + data.replace("/", "-").split("-")[1] + "-"
                    + data.replace("/", "-").split("-")[0];

            LocalDate dataAgendada = LocalDate.parse(dataDeAgendamento);
            if (dataAgendada.isBefore(hoje))
                throw new InvalidRequestException("Não é possível realizar um agendamento para uma data no passado");

            return true;
        }
        throw new InvalidRequestException
                ("Validação do patrimoônio falhou. Motivo: o padrão da data de agendamento é inválido");
    }

}
