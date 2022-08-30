package br.com.twobrothers.msvendas.services;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Gabriel Lagrota
 * @email gabriellagrota23@gmail.com
 * @phone (11)97981-5415
 * @github https://github.com/LagrotaGabriel
 * @version 1.0
 * @since 30-08-22
 */
@Getter
@AllArgsConstructor
public enum OperacaoEstoque {

    CRIACAO(0, "Criação"),
    ALTERACAO(1, "Alteração"),
    REMOCAO(2, "Remoção");

    private final int code;
    private final String desc;

}
