package br.com.twobrothers.msdespesas.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusDespesaEnum {

    PAGO(1, "Pago"),
    PENDENTE(2, "Pendente");

    private final int code;
    private final String desc;

}
