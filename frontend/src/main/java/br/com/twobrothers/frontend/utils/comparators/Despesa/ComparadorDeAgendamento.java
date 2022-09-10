package br.com.twobrothers.frontend.utils.comparators.Despesa;

import br.com.twobrothers.frontend.models.entities.DespesaEntity;

import java.util.Comparator;

public class ComparadorDeAgendamento implements Comparator<DespesaEntity> {
    @Override
    public int compare(DespesaEntity despesa1, DespesaEntity despesa2) {

        if (despesa1.getDataAgendamento() != null && despesa2.getDataAgendamento() != null) {
            if (despesa1.getDataAgendamento().compareTo(despesa2.getDataAgendamento()) > 0) {
                return 1;
            }

        }
        return -1;
    }
}
