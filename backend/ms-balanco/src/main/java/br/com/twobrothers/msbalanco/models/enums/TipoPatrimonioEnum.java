package br.com.twobrothers.msbalanco.models.enums;

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
public enum TipoPatrimonioEnum {

    ATIVO(0, "Ativo"),
    PASSIVO(1, "Passivo"),
    CHEQUE(2, "Cheque"),
    BOLETO(3, "Boleto"),
    DUPLICATA(4, "Duplicata"),
    A_RECEBER(5, "A receber");

    private final int code;
    private final String desc;

}
