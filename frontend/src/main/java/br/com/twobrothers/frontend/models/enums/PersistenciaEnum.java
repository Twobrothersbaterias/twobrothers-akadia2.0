package br.com.twobrothers.frontend.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PersistenciaEnum {

    NAO(0, "Não", 0),
    UM_MES(1, "Um mês", 1),
    UM_TRIMESTRE(2, "Um trimestre", 3),
    UM_SEMESTRE(3, "Um semestre", 6),
    UM_ANO(4, "Um ano", 12),
    PERSISTIDO(5, "Persistido", 0);

    private final int code;
    private final String desc;
    private final Integer meses;

}
