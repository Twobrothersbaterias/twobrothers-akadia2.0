package br.com.twobrothers.frontend.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NfeEnum {

    SEM_NOTA(0, "Sem nota fiscal"),
    NFE(1, "NFe"),
    DANFE(2, "DANFE");

    private final Integer code;
    private final String desc;

}
