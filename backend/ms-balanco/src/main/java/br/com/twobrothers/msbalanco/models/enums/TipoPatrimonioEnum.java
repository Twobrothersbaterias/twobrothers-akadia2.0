package br.com.twobrothers.msbalanco.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipoPatrimonioEnum {

    ATIVO(0, "Ativo"),
    PASSIVO(1, "Passivo"),
    CHEQUE(2, "Cheque"),
    BOLETO(3, "Boleto"),
    DUPLICATA(4, "Duplicata"),
    A_RECEBER(5, "A receber");

    private final int code;
    private final String desc;

}
