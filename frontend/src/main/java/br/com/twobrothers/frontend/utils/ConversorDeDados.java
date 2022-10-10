package br.com.twobrothers.frontend.utils;

import java.text.NumberFormat;
import java.util.Locale;

public class ConversorDeDados {

    ConversorDeDados(){}

    public static String converteValorDoubleParaValorMonetario(Double valor) {
        Locale ptBr = new Locale("pt", "BR");
        return NumberFormat.getCurrencyInstance(ptBr).format(valor);
    }

    public static String converteDataUsParaDataBr(String dataUs) {
        String[] dataSplitada = dataUs.split("-");
        return dataSplitada[2] + "/" + dataSplitada[1] + "/" + dataSplitada[0];
    }

}
