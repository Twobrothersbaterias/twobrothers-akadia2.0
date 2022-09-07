package br.com.twobrothers.frontend.utils;

public class ConversorDeDatas {

    ConversorDeDatas(){}

    public static String converteDataUsParaDataBr(String dataUs) {
        String[] dataSplitada = dataUs.split("-");
        return dataSplitada[2] + "/" + dataSplitada[1] + "/" + dataSplitada[0];
    }

}
