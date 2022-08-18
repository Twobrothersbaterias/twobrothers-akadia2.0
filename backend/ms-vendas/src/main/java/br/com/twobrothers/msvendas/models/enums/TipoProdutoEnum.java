package br.com.twobrothers.msvendas.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipoProdutoEnum {

    BATERIA(0, "Bateria"),
    SUCATA(1, "Sucata");

    private final int code;
    private final String desc;

}
