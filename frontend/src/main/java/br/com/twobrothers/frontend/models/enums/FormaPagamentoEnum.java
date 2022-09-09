package br.com.twobrothers.frontend.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Gabriel Lagrota
 * @email gabriellagrota23@gmail.com
 * @phone (11)97981-5415
 * @github https://github.com/LagrotaGabriel
 * @version 1.0
 * @since 30-08-22
 */
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
