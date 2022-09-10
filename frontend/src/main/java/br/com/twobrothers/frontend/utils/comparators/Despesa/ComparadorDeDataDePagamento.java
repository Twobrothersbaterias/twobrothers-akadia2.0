package br.com.twobrothers.frontend.utils.comparators.Despesa;

import br.com.twobrothers.frontend.models.entities.DespesaEntity;

import java.util.Comparator;

public class ComparadorDeDataDePagamento implements Comparator<DespesaEntity> {
    @Override
    public int compare(DespesaEntity despesa1, DespesaEntity despesa2) {
        if (despesa1.getDataPagamento() != null && despesa2.getDataPagamento() != null) {
            if (despesa1.getDataPagamento().compareTo(despesa2.getDataPagamento()) > 0) {
                return -1;
            }
        }
        return 1;
    }
}
