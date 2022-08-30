package br.com.twobrothers.msdespesas.models.enums;

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
public enum StatusDespesaEnum {

    PAGO(1, "Pago"),
    PENDENTE(2, "Pendente");

    private final int code;
    private final String desc;

}
