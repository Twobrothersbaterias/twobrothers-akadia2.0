package br.com.twobrothers.msvendas.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipoEntradaEnum {

    COMUM(0, "Comum"),
    TROCA(1, "Troca");

    private final int code;
    private final String desc;

}
