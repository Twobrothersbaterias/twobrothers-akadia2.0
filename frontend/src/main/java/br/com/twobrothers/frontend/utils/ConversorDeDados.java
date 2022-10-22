package br.com.twobrothers.frontend.utils;

import br.com.twobrothers.frontend.models.dto.EntradaOrdemDTO;
import br.com.twobrothers.frontend.models.dto.OrdemDTO;
import br.com.twobrothers.frontend.models.dto.PagamentoDTO;
import br.com.twobrothers.frontend.models.enums.FormaPagamentoEnum;
import br.com.twobrothers.frontend.models.enums.TipoEntradaEnum;
import br.com.twobrothers.frontend.models.enums.TipoOrdemEnum;
import br.com.twobrothers.frontend.services.ProdutoEstoqueService;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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

    public static OrdemDTO converteStringEntradaParaListaDeObjetos(ProdutoEstoqueService produtoEstoqueService, OrdemDTO ordem, String entradasString) {

        List<EntradaOrdemDTO> entradasList = new ArrayList<>();

        String[] entradasSplitadas = entradasString.split("ENTRADA=");

        for(int i = 1; i < entradasSplitadas.length; i++) {

            EntradaOrdemDTO entrada = new EntradaOrdemDTO();
            String[] entradaSplitada = entradasSplitadas[i].split(";");

            entrada.setTipoOrdem(TipoOrdemEnum.valueOf(entradaSplitada[0]));
            entrada.setTipoEntrada(TipoEntradaEnum.valueOf(entradaSplitada[1]));

            if (entradaSplitada[2].equals("Serviço")) entrada.setProduto(null);
            else entrada.setProduto(produtoEstoqueService.buscaPorId(Long.parseLong(entradaSplitada[2])));

            entrada.setValor(Double.parseDouble(entradaSplitada[3]));
            entrada.setQuantidade(Integer.parseInt(entradaSplitada[4]));
            entrada.setOrdem(ordem);

            ordem.getEntradas().add(entrada);

        }

        return ordem;

    }

    public static OrdemDTO converteStringPagamentoParaListaDeObjetos(OrdemDTO ordem, String pagamentosString) {

        List<PagamentoDTO> pagamentosList = new ArrayList<>();

        String[] pagamentosSplitados = pagamentosString.split("PAGAMENTO=");

        for(int i = 1; i < pagamentosSplitados.length; i++) {

            PagamentoDTO pagamento = new PagamentoDTO();
            String[] pagamentoSplitado = pagamentosSplitados[i].split(";");

            pagamento.setFormaPagamento(FormaPagamentoEnum.valueOf(pagamentoSplitado[0]));
            pagamento.setValor(Double.valueOf(pagamentoSplitado[1]));

            if (pagamentoSplitado[2].equals("") || pagamentoSplitado[2].isEmpty()) {
                pagamento.setDataAgendamento("Não possui");
                pagamento.setDataPagamento(LocalDate.now().toString());
            }
            else {
                pagamento.setDataAgendamento(pagamentoSplitado[2]);
                pagamento.setDataPagamento("Em aberto");
            }

            pagamento.setObservacao(pagamentoSplitado[3]);
            pagamento.setOrdem(ordem);

            ordem.getPagamentos().add(pagamento);

        }

        return ordem;

    }

}
