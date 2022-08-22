package br.com.twobrothers.msvendas.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ValidationType {

    CREATE(1, "Create"),
    UPDATE(2, "Update");

    private final int code;
    private final String desc;

}
