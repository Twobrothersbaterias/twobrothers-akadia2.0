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
public enum TipoOrdemEnum {

    PADRAO_PRODUTO(0, "Padrão - produto"),
    PADRAO_SERVICO(1, "Padrão - serviço"),
    GARANTIA(2, "Ordem de garantia");

    private final int code;
    private final String desc;

}
