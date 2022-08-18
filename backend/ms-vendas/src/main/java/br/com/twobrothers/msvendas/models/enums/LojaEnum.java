package br.com.twobrothers.msvendas.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LojaEnum {

    LOJA_1(0, "Loja 1"),
    LOJA_2(1, "Loja 2");

    private final int code;
    private final String desc;

}
