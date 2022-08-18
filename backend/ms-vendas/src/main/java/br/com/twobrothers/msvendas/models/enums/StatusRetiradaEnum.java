package br.com.twobrothers.msvendas.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusRetiradaEnum {

    LOJA_FISICA(0, "Loja física"),
    ENTREGA_EM_TRANSITO(1, "Entrega - em trânsito"),
    ENTREGA_ENTREGUE(2, "Entrega - entregue");

    private final int code;
    private final String desc;

}
