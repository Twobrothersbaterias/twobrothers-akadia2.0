package br.com.twobrothers.frontend.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PrivilegioEnum {

    VENDEDOR(0, "Vendedor"),
    GERENTE(1, "Gerente"),
    DIRETOR(2, "Diretor"),
    DESENVOLVEDOR(3, "Desenvolvedor");

    private final Integer code;
    private final String desc;

}
