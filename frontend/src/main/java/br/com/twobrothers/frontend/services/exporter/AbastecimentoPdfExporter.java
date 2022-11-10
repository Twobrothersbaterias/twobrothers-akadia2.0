package br.com.twobrothers.frontend.services.exporter;

import br.com.twobrothers.frontend.models.entities.AbastecimentoEntity;
import br.com.twobrothers.frontend.models.enums.FormaPagamentoEnum;
import br.com.twobrothers.frontend.repositories.UsuarioRepository;
import br.com.twobrothers.frontend.services.AbastecimentoService;
import br.com.twobrothers.frontend.utils.UsuarioUtils;
import com.lowagie.text.Font;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static br.com.twobrothers.frontend.utils.ConversorDeDados.converteDataUsParaDataBr;
import static br.com.twobrothers.frontend.utils.ConversorDeDados.converteValorDoubleParaValorMonetario;

@Service
public class AbastecimentoPdfExporter {

    private void escreveHeaderTabela(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(new Color(125, 161, 129));
        cell.setPadding(10);
        cell.setPaddingBottom(15);
        cell.setHorizontalAlignment(Cell.ALIGN_CENTER);

        com.lowagie.text.Font font = FontFactory.getFont(FontFactory.COURIER, 12, Color.BLACK);

        cell.setPhrase(new Phrase("Data", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Fornecedor", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Produto", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Qtd.", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Total", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Meio de pgto.", font));
        table.addCell(cell);

    }

    private void escreveDadosTabela(PdfPTable table, java.util.List<AbastecimentoEntity> abastecimentos) {

        Integer contador = 1;

        for (AbastecimentoEntity abastecimento : abastecimentos) {

            PdfPCell cell = new PdfPCell();
            com.lowagie.text.Font font = FontFactory.getFont(FontFactory.COURIER, 10, Color.BLACK);
            cell.setPadding(5);

            if (contador % 2 == 1) cell.setBackgroundColor(new Color(216, 230, 217));
            else cell.setBackgroundColor(new Color(200, 219, 201));

            cell.setPhrase(new Phrase(converteDataUsParaDataBr(abastecimento.getDataCadastro()), font));
            table.addCell(cell);

            String fornecedor = "Sem fornecedor";
            if (abastecimento.getFornecedor() != null) fornecedor = abastecimento.getFornecedor().getNome();
            cell.setPhrase(new Phrase(fornecedor, font));
            table.addCell(cell);

            cell.setPhrase(new Phrase(abastecimento.getProduto().getSigla(), font));
            table.addCell(cell);

            cell.setPhrase(new Phrase(abastecimento.getQuantidade().toString(), font));
            table.addCell(cell);

            cell.setPhrase(new Phrase(abastecimento.getFormaPagamento().getDesc(), font));
            table.addCell(cell);

            cell.setPhrase(new Phrase(converteValorDoubleParaValorMonetario(abastecimento.getCustoTotal()), font));
            table.addCell(cell);

            contador++;
        }
    }

    public void export(HttpServletResponse response,
                       java.util.List<AbastecimentoEntity> abastecimentos,
                       AbastecimentoService abastecimentoService,
                       HashMap<String, String> filtros,
                       UsuarioRepository usuarioRepository)
            throws DocumentException, IOException {

        constroiLayoutArquivo(
                response,
                filtros,
                usuarioRepository,
                abastecimentos,
                abastecimentoService,
                constroiParagrafoTitulo(filtros),
                constroiParagrafoDataHora(usuarioRepository),
                constroiTabelaInformativos(abastecimentos, abastecimentoService),
                constroiTabelaObjetos(abastecimentos));

    }

    public void constroiLayoutArquivo(HttpServletResponse response,
                                      HashMap<String, String> filtros,
                                      UsuarioRepository usuarioRepository,
                                      java.util.List<AbastecimentoEntity> abastecimentos,
                                      AbastecimentoService abastecimentoService,
                                      Paragraph paragrafoTitulo,
                                      Paragraph paragrafoDataHora,
                                      PdfPTable informativos,
                                      PdfPTable objetos) throws IOException {

        Document document = new Document(PageSize.A3);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
        document.add(constroiParagrafoTitulo(filtros));
        document.add(constroiParagrafoDataHora(usuarioRepository));
        document.add(constroiTabelaObjetos(abastecimentos));
        document.add(constroiTabelaInformativos(abastecimentos, abastecimentoService));
        document.close();
    }

    public Paragraph constroiParagrafoTitulo(HashMap<String, String> filtros) {

        String titulo = null;

        for (Map.Entry<String, String> set : filtros.entrySet()) {
            if (set.getKey().equals("fornecedor") && set.getValue() != null) {
                titulo = "Relatório de compras com fornecedores de nome " + filtros.get("fornecedor");
                if (filtros.get("meio") != null) {
                    titulo += " na forma de pagamento " + filtros.get("meio");
                }
                break;
            } else if (set.getKey().equals("fornecedorId") && set.getValue() != null) {
                titulo = "Relatório de compras com o fornecedor " + filtros.get("fornecedorId");
                if (filtros.get("meio") != null) {
                    titulo += " na forma de pagamento " + filtros.get("meio");
                }
                break;
            } else if (set.getKey().equals("produto") && set.getValue() != null) {
                titulo = "Relatório de compras do produto " + filtros.get("produto");
                if (filtros.get("meio") != null) {
                    titulo += " na forma de pagamento " + filtros.get("meio");
                }
                break;
            } else if (set.getKey().equals("inicio") && set.getValue() != null || set.getKey().equals("fim") && set.getValue() != null) {
                titulo = "Relatório de compras cadastradas do dia " + converteDataUsParaDataBr(filtros.get("inicio"))
                        + " ao dia " + converteDataUsParaDataBr(filtros.get("fim"));
                if (filtros.get("meio") != null) {
                    titulo += " na forma de pagamento " + filtros.get("meio");
                }
                break;
            } else if (set.getKey().equals("mes") && set.getValue() != null || set.getKey().equals("ano") && set.getValue() != null) {
                titulo = "Relatório de compras cadastradas no mês " + filtros.get("mes")
                        + " de " + filtros.get("ano");
                if (filtros.get("meio") != null) {
                    titulo += " na forma de pagamento " + filtros.get("meio");
                }
                break;
            } else {
                titulo = "Relatório geral de compras";
            }
        }

        com.lowagie.text.Font font = FontFactory.getFont(FontFactory.COURIER_BOLD, 15, Color.BLACK);
        Paragraph p = new Paragraph(titulo, font);
        p.setAlignment(Paragraph.ALIGN_CENTER);
        p.setSpacingBefore(0);
        p.setSpacingAfter(5);

        return p;
    }

    public Paragraph constroiParagrafoDataHora(UsuarioRepository usuarioRepository) {

        String currentDateTime = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss").format(new Date());

        com.lowagie.text.Font font = FontFactory.getFont(FontFactory.COURIER, 12, Color.BLACK);
        Paragraph p = new Paragraph(currentDateTime + ", por " + UsuarioUtils.loggedUser(usuarioRepository).getNomeUsuario(), font);
        p.setAlignment(Paragraph.ALIGN_CENTER);
        p.setSpacingBefore(0);
        p.setSpacingAfter(5);
        return p;

    }

    public PdfPTable constroiTabelaInformativos(java.util.List<AbastecimentoEntity> abastecimentos,
                                                AbastecimentoService abastecimentoService) {

        PdfPTable table;
        table = new PdfPTable(6);
        table.setWidthPercentage(100f);
        table.setWidths(new float[]{2f, 2f, 2f, 2f, 2f, 2f});
        table.setSpacingBefore(15);
        table.setSpacingAfter(15);

        Font font = FontFactory.getFont(FontFactory.COURIER, 9, Color.BLACK);

        PdfPCell cell = new PdfPCell();
        cell.setPadding(5);
        cell.setPaddingBottom(8);
        cell.setBackgroundColor(new Color(125, 161, 129));
        cell.setBorderColor(new Color(125, 161, 129));
        cell.setHorizontalAlignment(Cell.ALIGN_CENTER);

        cell.setPhrase(new Phrase("Espécie: "
                + converteValorDoubleParaValorMonetario(abastecimentoService.calculaFormaPagamento(abastecimentos, FormaPagamentoEnum.DINHEIRO)), font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Crédito: "
                + converteValorDoubleParaValorMonetario(abastecimentoService.calculaFormaPagamento(abastecimentos, FormaPagamentoEnum.CREDITO)), font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Débito: "
                + converteValorDoubleParaValorMonetario(abastecimentoService.calculaFormaPagamento(abastecimentos, FormaPagamentoEnum.DEBITO)), font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Cheque: "
                + converteValorDoubleParaValorMonetario(abastecimentoService.calculaFormaPagamento(abastecimentos, FormaPagamentoEnum.CHEQUE)), font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Pix: "
                + converteValorDoubleParaValorMonetario(abastecimentoService.calculaFormaPagamento(abastecimentos, FormaPagamentoEnum.PIX)), font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Boleto: "
                + converteValorDoubleParaValorMonetario(abastecimentoService.calculaFormaPagamento(abastecimentos, FormaPagamentoEnum.BOLETO)), font));
        table.addCell(cell);

        return table;

    }

    public PdfPTable constroiTabelaObjetos(List<AbastecimentoEntity> abastecimentos) {
        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100f);
        table.setWidths(new float[]{2f, 2f, 2f, 2f, 2f, 2f});
        table.setSpacingBefore(15);
        escreveHeaderTabela(table);
        escreveDadosTabela(table, abastecimentos);
        return table;
    }

}
