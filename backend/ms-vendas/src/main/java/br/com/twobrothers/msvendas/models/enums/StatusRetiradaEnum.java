package br.com.twobrothers.msvendas.models.enums;

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
public enum StatusRetiradaEnum {

    LOJA_FISICA(0, "Loja física"),
    ENTREGA_EM_TRANSITO(1, "Entrega - em trânsito"),
    ENTREGA_ENTREGUE(2, "Entrega - entregue");

    private final int code;
    private final String desc;

}
