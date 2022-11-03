package br.com.twobrothers.frontend.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FonteEnum {

    CAIRO(0, "Cairo", "'Cairo', sans-serif"),
    CARTER_ONE(1, "Carter One", "'Carter One', cursive"),
    JETBRAINS_MONO(2, "JetBrains Mono", "'JetBrains Mono', monospace"),
    LATO(3, "Lato", "'Lato', sans-serif"),
    LOBSTER(4, "Lobster", "'Lobster', cursive"),
    OLEO_SCRIPT(5, "Oleo Script", "'Oleo Script', cursive"),
    ROBOTO(6, "Roboto", "'Roboto', sans-serif"),
    SATISFY(7, "Satisfy", "'Satisfy', cursive");

    private final Integer code;
    private final String desc;
    private final String fontFamily;
}
