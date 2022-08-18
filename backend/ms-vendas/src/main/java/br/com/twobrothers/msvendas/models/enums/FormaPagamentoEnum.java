package br.com.twobrothers.msvendas.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FormaPagamentoEnum {

    DINHEIRO(0, "Dinheiro"),
    CREDITO(1, "Credito"),
    DEBITO(2, "Debito"),
    CHEQUE(3, "Cheque"),
    TED(4, "TED"),
    PIX(5, "PIX"),
    BOLETO(6, "Boleto"),
    FATURADO_PENDENTE(7, "Faturado - pendente"),
    FATURADO_PAGO(8, "Faturado - pago");

    private final int code;
    private final String desc;

}
