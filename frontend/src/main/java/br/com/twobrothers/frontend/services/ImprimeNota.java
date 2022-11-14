package br.com.twobrothers.frontend.services;

import javax.print.*;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ImprimeNota {

    private static PrintService impressora;
    public static List<String> retornaImpressoras(){
        try {
            List<String> listaImpressoras = new ArrayList<>();
            DocFlavor df = DocFlavor.SERVICE_FORMATTED.PRINTABLE;
            PrintService[] ps = PrintServiceLookup.lookupPrintServices(df, null);
            for (PrintService p : ps) {
                listaImpressoras.add(p.getName());
            }
            return listaImpressoras;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void detectaImpressoras(String impressoraSelecionada) {  //Passa por parâmetro o nome salvo da impressora
        try {
            DocFlavor df = DocFlavor.SERVICE_FORMATTED.PRINTABLE;
            PrintService[] ps = PrintServiceLookup.lookupPrintServices(df, null);
            for (PrintService p : ps) {
                if(p.getName()!=null && p.getName().contains(impressoraSelecionada)){
                    System.err.println("Impressora encontrada: " + p.getName());
                    impressora = p;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean imprime(String texto) {

        if (impressora == null) {
            System.err.println("Nenhuma impressora foi encontrada");
        } else {
            try {
                System.err.println("IMPRESSORA: " + impressora);
                DocPrintJob dpj = impressora.createPrintJob();
                InputStream stream = new ByteArrayInputStream((texto + "\n").getBytes());
                DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
                Doc doc = new SimpleDoc(stream, flavor, null);
                dpj.print(doc, null);
                return true;
            } catch (PrintException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

}