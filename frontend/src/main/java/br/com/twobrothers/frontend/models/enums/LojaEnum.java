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
public enum LojaEnum {

    PONTO_1(0, "Loja 1"),
    PONTO_VALT(1, "Loja Valt"),
    PONTO_MOBILE(2, "Lançamento mobile");

    private final int code;
    private final String desc;

}
