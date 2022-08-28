package br.com.twobrothers.msvendas.services;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OperacaoEstoque {

    CRIACAO(0, "Criação"),
    ALTERACAO(1, "Alteração"),
    REMOCAO(2, "Remoção");

    private final int code;
    private final String desc;

}
