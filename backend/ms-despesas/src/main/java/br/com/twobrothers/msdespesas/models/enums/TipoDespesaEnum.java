package br.com.twobrothers.msdespesas.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipoDespesaEnum {

    FIXO(1, "Fixo"),
    VARIAVEL(2, "Variavel");

    private final int code;
    private final String desc;

}
