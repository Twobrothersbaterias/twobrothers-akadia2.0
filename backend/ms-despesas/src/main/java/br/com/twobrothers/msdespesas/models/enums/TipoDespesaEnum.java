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
public enum TipoDespesaEnum {

    FIXO(1, "Fixo"),
    VARIAVEL(2, "Variavel");

    private final int code;
    private final String desc;

}
