package br.com.twobrothers.frontend.services;

import br.com.twobrothers.frontend.models.entities.DespesaEntity;
import br.com.twobrothers.frontend.models.entities.EntradaOrdemEntity;
import br.com.twobrothers.frontend.models.entities.OrdemEntity;
import br.com.twobrothers.frontend.models.enums.FormaPagamentoEnum;
import br.com.twobrothers.frontend.models.enums.StatusRetiradaEnum;
import br.com.twobrothers.frontend.models.enums.TipoOrdemEnum;
import br.com.twobrothers.frontend.repositories.services.DespesaCrudService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import static br.com.twobrothers.frontend.utils.ConversorDeDados.converteValorDoubleParaValorMonetario;

@Slf4j
@Service
public class RelatorioService {

    private static final DecimalFormat df = new DecimalFormat("0.00");

    @Autowired
    DespesaCrudService despesaCrudService;

    @Autowired
    OrdemService ordemService;

    @Autowired
    DespesaService despesaService;

    public String serializaHashMapParaJson(HashMap<String, Object> maps) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(maps);
    }

    public void sortbykey(HashMap<Integer, Object> maps) {
        TreeMap<Integer, Object> sorted = new TreeMap<>();
        sorted.putAll(maps);
    }

    public HashMap<String, Integer> quantidadeBateriasPorBairro(List<OrdemEntity> ordens) {

        HashMap<String, Integer> bateriasPorBairro = new HashMap<>();

        for (OrdemEntity ordem: ordens) {

            if (ordem.getRetirada().getStatusRetirada() != StatusRetiradaEnum.LOJA_FISICA
            && ordem.getCliente() != null) {

                if (ordem.getCliente().getEndereco().getBairro() != null
                && !ordem.getCliente().getEndereco().getBairro().isEmpty()) {

                    for (EntradaOrdemEntity entrada : ordem.getEntradas()) {
                        if (entrada.getProduto() != null && !entrada.getTipoOrdem().equals(TipoOrdemEnum.GARANTIA)) {
                            if (!bateriasPorBairro.containsKey(ordem.getCliente().getEndereco().getBairro())) {
                                bateriasPorBairro.put(ordem.getCliente().getEndereco().getBairro(), entrada.getQuantidade());
                            } else {
                                bateriasPorBairro.replace(ordem.getCliente().getEndereco().getBairro(),
                                        (bateriasPorBairro.get(ordem.getCliente().getEndereco().getBairro()) + entrada.getQuantidade()));
                            }

                        }
                    }

                }
            }
        }

        return bateriasPorBairro;
    }

    public HashMap<String, Integer> geraQuantidadeBateriasPorTipo(List<OrdemEntity> ordens) {

        HashMap<String, Integer> bateriasPorTipo = new HashMap<>();

        for (OrdemEntity ordem: ordens) {

            for(EntradaOrdemEntity entrada: ordem.getEntradas()) {

                if (entrada.getProduto() != null && !entrada.getTipoOrdem().equals(TipoOrdemEnum.GARANTIA)) {
                    if (!bateriasPorTipo.containsKey(entrada.getProduto().getSigla())) {
                        bateriasPorTipo.put(entrada.getProduto().getSigla(), entrada.getQuantidade());
                    }
                    else {
                        bateriasPorTipo.replace(entrada.getProduto().getSigla(),
                                (bateriasPorTipo.get(entrada.getProduto().getSigla()) + entrada.getQuantidade()));
                    }
                }
            }

        }

        return bateriasPorTipo;
    }

    public HashMap<Integer, Object> geraFaturamentoLiquidoPorDia(List<OrdemEntity> ordens, Integer mes, Integer ano) {

        LocalDate dataFim =
                LocalDate.of(ano, mes, LocalDate.now().withMonth(mes).withYear(ano)
                        .with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth());

        HashMap<Integer, Object> faturamentoLiquidoPorDia = new HashMap<>();
        for (int i = 1; i <= dataFim.getDayOfMonth(); i++) {
            Double totalDia = 0.0;
            for (OrdemEntity ordem : ordens) {
                if (Integer.parseInt(ordem.getDataCadastro().split("-")[2]) == i) {
                    for (EntradaOrdemEntity entrada : ordem.getEntradas()) {

                        if (entrada.getProduto() == null) {
                            totalDia += entrada.getValor();
                        } else {
                            totalDia += (entrada.getValor() - entrada.getProduto().getCustoUnitario());
                        }
                    }
                }
            }
            faturamentoLiquidoPorDia.put(i, totalDia);
        }
        return faturamentoLiquidoPorDia;
    }

    public HashMap<Integer, Object> geraFaturamentoBrutoPorDia(List<OrdemEntity> ordens, Integer mes, Integer ano) {

        LocalDate dataFim =
                LocalDate.of(ano, mes, LocalDate.now().withMonth(mes).withYear(ano)
                        .with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth());
        HashMap<Integer, Object> faturamentoBrutoPorDia = new HashMap<>();

        for (int i = 1; i <= dataFim.getDayOfMonth(); i++) {
            Double totalDia = 0.0;
            for (OrdemEntity ordem : ordens) {
                if (Integer.parseInt(ordem.getDataCadastro().split("-")[2]) == i) totalDia += ordem.getTotalVendas();
            }
            faturamentoBrutoPorDia.put(i, totalDia);
        }

        return faturamentoBrutoPorDia;

    }

    public HashMap<Integer, Object> geraTicketMedioPorDia(List<OrdemEntity> ordens, Integer mes, Integer ano) {

        LocalDate dataFim =
                LocalDate.of(ano, mes, LocalDate.now().withMonth(mes).withYear(ano)
                        .with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth());

        HashMap<Integer, Object> ticketMedioPorDia = new HashMap<>();

        df.setRoundingMode(RoundingMode.DOWN);

        for (int i = 1; i <= dataFim.getDayOfMonth(); i++) {

            Double totalDia = 0.0;
            Integer quantidadeDia = 0;

            for (OrdemEntity ordem : ordens) {
                if (Integer.parseInt(ordem.getDataCadastro().split("-")[2]) == i) {
                    totalDia += ordem.getTotalVendas();
                    for (EntradaOrdemEntity entrada : ordem.getEntradas()) {
                        if (entrada.getProduto() == null) quantidadeDia += 1;
                        else quantidadeDia += entrada.getQuantidade();
                    }
                }
            }

            if (quantidadeDia != 0) ticketMedioPorDia.put(i,
                    Double.parseDouble((df.format(totalDia / quantidadeDia)).replace(",", ".")));
            else ticketMedioPorDia.put(i, 0.0);

        }

        return ticketMedioPorDia;

    }

    public String geraTicketMedioTotal(List<OrdemEntity> ordens) {
        Double total = 0.0;
        Integer quantidade = 0;
        for (OrdemEntity ordem : ordens) {
            for (EntradaOrdemEntity entrada : ordem.getEntradas()) {
                if (entrada.getProduto() != null) {
                    total += entrada.getValor();
                    quantidade += entrada.getQuantidade();
                }
            }
        }

        if (quantidade == 0) return "0";
        else return converteValorDoubleParaValorMonetario(total / quantidade);
    }

    public HashMap<Integer, Object> geraCustosPorDia(List<OrdemEntity> ordens, Integer mes, Integer ano) {

        LocalDate dataFim =
                LocalDate.of(ano, mes, LocalDate.now().withMonth(mes).withYear(ano)
                        .with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth());

        HashMap<Integer, Object> custosDoDia = new HashMap<>();
        for (int i = 1; i <= dataFim.getDayOfMonth(); i++) {
            Double custosDia = 0.0;
            for (OrdemEntity ordem : ordens) {
                if (Integer.parseInt(ordem.getDataCadastro().split("-")[2]) == i) {
                    for (EntradaOrdemEntity entrada : ordem.getEntradas()) {
                        if (entrada.getProduto() != null) {
                            custosDia += entrada.getProduto().getCustoUnitario();
                        }
                    }
                }
            }
            custosDoDia.put(i, custosDia);
        }
        return custosDoDia;
    }

    public HashMap<Integer, Object> geraDespesasPorDia(List<DespesaEntity> despesas, Integer mes, Integer ano) {

        LocalDate dataFim =
                LocalDate.of(ano, mes, LocalDate.now().withMonth(mes).withYear(ano)
                        .with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth());

        HashMap<Integer, Object> despesasPorDia = new HashMap<>();
        for (int i = 1; i <= dataFim.getDayOfMonth(); i++) {
            Double totalDespesas = 0.0;
            for (DespesaEntity despesa : despesas) {
                if (Integer.parseInt(despesa.getDataPagamento().split("-")[2]) == i) {
                    totalDespesas += despesa.getValor();
                }
            }
            despesasPorDia.put(i, totalDespesas);
        }
        return despesasPorDia;
    }

    public ModelMap modelMapBuilder(ModelMap modelMap, String mes, String ano) throws JsonProcessingException {

        log.info("[STARTING] Iniciando construção do modelMap...");
        HashMap<String, Object> atributos = new HashMap<>();

        List<OrdemEntity> ordensNoMes = ordemService.filtroOrdensRelatorio(mes, ano);
        List<DespesaEntity> despesasNoMes =
                despesaCrudService.buscaDespesasPagasNoMes(Integer.parseInt(mes), Integer.parseInt(ano));

        atributos.put("mes", mes);
        atributos.put("ano", ano);

        atributos.put("totalBateriasVendidas", ordemService.calculaQuantidadeVendida(ordensNoMes));
        atributos.put("totalBruto", converteValorDoubleParaValorMonetario(ordemService.calculaBrutoVendido(ordensNoMes)));
        atributos.put("totalCusto", converteValorDoubleParaValorMonetario(ordemService.calculaCustoVenda(ordensNoMes)));
        atributos.put("totalLiquido", converteValorDoubleParaValorMonetario(ordemService.calculaBrutoVendido(ordensNoMes) - ordemService.calculaCustoVenda(ordensNoMes)));
        atributos.put("totalDinheiro", converteValorDoubleParaValorMonetario(ordemService.calculaFormaPagamento(ordensNoMes, FormaPagamentoEnum.DINHEIRO)));
        atributos.put("totalCredito", converteValorDoubleParaValorMonetario(ordemService.calculaFormaPagamento(ordensNoMes, FormaPagamentoEnum.CREDITO)));
        atributos.put("totalDebito", converteValorDoubleParaValorMonetario(ordemService.calculaFormaPagamento(ordensNoMes, FormaPagamentoEnum.DEBITO)));
        atributos.put("totalPix", converteValorDoubleParaValorMonetario(ordemService.calculaFormaPagamento(ordensNoMes, FormaPagamentoEnum.PIX)));
        atributos.put("totalBoletos", converteValorDoubleParaValorMonetario(ordemService.calculaFormaPagamento(ordensNoMes, FormaPagamentoEnum.BOLETO)));
        atributos.put("totalFaturados", converteValorDoubleParaValorMonetario(ordemService.calculaFormaPagamento(ordensNoMes, FormaPagamentoEnum.FATURADO)));
        atributos.put("message", despesaService.serializeToJson(despesaCrudService.buscaDespesasAtrasadas()));

        atributos.put("diasDoMes", geraFaturamentoLiquidoPorDia(ordensNoMes, 11, 2022).keySet().toArray());

        TreeMap<String, Integer> tiposBateriaSorted = new TreeMap<>();
        tiposBateriaSorted.putAll(geraQuantidadeBateriasPorTipo(ordensNoMes));
        atributos.put("tiposBateria", tiposBateriaSorted.keySet().toArray());

        TreeMap<String, Integer> tiposBateriaBairroSorted = new TreeMap<>();
        tiposBateriaBairroSorted.putAll(quantidadeBateriasPorBairro(ordensNoMes));
        atributos.put("tiposBateriaPorBairro", tiposBateriaBairroSorted.keySet().toArray());

        atributos.put("ticketMedioTotal",
                geraTicketMedioTotal(ordensNoMes));
        atributos.put("faturamentoBrutoTotal",
                converteValorDoubleParaValorMonetario(ordemService.calculaBrutoVendido(ordensNoMes)));
        atributos.put("custoTotal",
                converteValorDoubleParaValorMonetario(ordemService.calculaCustoVenda(ordensNoMes)));
        atributos.put("faturamentoLiquidoTotal",
                converteValorDoubleParaValorMonetario(ordemService.calculaBrutoVendido(ordensNoMes) - ordemService.calculaCustoVenda(ordensNoMes)));
        atributos.put("despesasTotal", converteValorDoubleParaValorMonetario(despesaService.calculaValorPago(despesasNoMes)));

        atributos.put("faturamentoBrutoPorDiasMes", geraFaturamentoBrutoPorDia(ordensNoMes, 11, 2022).values().toArray());
        atributos.put("faturamentoLiquidoPorDiasMes", geraFaturamentoLiquidoPorDia(ordensNoMes, 11, 2022).values().toArray());
        atributos.put("ticketMedioPorDiaMes", geraTicketMedioPorDia(ordensNoMes, 11, 2022).values().toArray());
        atributos.put("custosPorDiaMes", geraCustosPorDia(ordensNoMes, 11, 2022).values().toArray());
        atributos.put("despesasPorDiaMes", geraDespesasPorDia(despesasNoMes, 11, 2022).values().toArray());

        TreeMap<String, Integer> quantidadeBateriasPorTipoSorted = new TreeMap<>();
        quantidadeBateriasPorTipoSorted.putAll(geraQuantidadeBateriasPorTipo(ordensNoMes));
        atributos.put("quantidadeBateriasPorTipo", quantidadeBateriasPorTipoSorted.values().toArray());

        TreeMap<String, Integer> quantidadeBateriasPorBairro = new TreeMap<>();
        quantidadeBateriasPorBairro.putAll(quantidadeBateriasPorBairro(ordensNoMes));
        atributos.put("quantidadeBateriasPorBairro", quantidadeBateriasPorBairro.values().toArray());

        sortbykey(geraFaturamentoLiquidoPorDia(ordensNoMes, 11, 2022));
        sortbykey(geraFaturamentoBrutoPorDia(ordensNoMes, 11, 2022));
        sortbykey(geraTicketMedioPorDia(ordensNoMes, 11, 2022));
        sortbykey(geraCustosPorDia(ordensNoMes, 11, 2022));
        sortbykey(geraDespesasPorDia(despesasNoMes, 11, 2022));
        modelMap.addAllAttributes(atributos);

        log.info("[SUCCESS] ModelMap construído com sucesso. Retornando para o controller...");
        return modelMap;
    }

}
