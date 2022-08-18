package br.com.twobrothers.msvendas.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipoOrdemEnum {

    PADRAO_PRODUTO(0, "Padrão - produto"),
    PADRAO_SERVICO(1, "Padrão - serviço"),
    GARANTIA(2, "Ordem de garantia");

    private final int code;
    private final String desc;

}
