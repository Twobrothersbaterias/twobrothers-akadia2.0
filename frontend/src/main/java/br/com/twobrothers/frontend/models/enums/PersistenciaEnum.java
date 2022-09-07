package br.com.twobrothers.frontend.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PersistenciaEnum {

    NAO(0, "Não"),
    UM_MES(1, "Um mês"),
    UM_TRIMESTRE(2, "Um trimestre"),
    UM_SEMESTRE(3, "Um semestre"),
    UM_ANO(4, "Um ano");

    private final int code;
    private final String desc;

}
