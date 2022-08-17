package br.com.twobrothers.msbalanco.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusPatrimonioEnum {

    PENDENTE(0, "Pendente"),
    PAGO(1, "Pago");

    private final int code;
    private final String desc;

}
