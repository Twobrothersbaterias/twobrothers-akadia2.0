package br.com.twobrothers.frontend.services.exporter;

import br.com.twobrothers.frontend.models.entities.DespesaEntity;
import br.com.twobrothers.frontend.repositories.UsuarioRepository;
import br.com.twobrothers.frontend.services.DespesaService;
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
public class DespesaPdfExporter {

    private void escreveHeaderTabela(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(new Color(125, 161, 129));
        cell.setPadding(10);
        cell.setPaddingBottom(15);
        cell.setHorizontalAlignment(Cell.ALIGN_CENTER);

        com.lowagie.text.Font font = FontFactory.getFont(FontFactory.COURIER);
        font.setSize(12);
        font.setColor(Color.BLACK);

        cell.setPhrase(new Phrase("Descrição", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Valor", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Tipo", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Status", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Agendamento", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Pagamento", font));
        table.addCell(cell);

    }

    private void escreveDadosTabela(PdfPTable table, java.util.List<DespesaEntity> despesas) {

        Integer contador = 1;

        for (DespesaEntity despesa : despesas) {

            PdfPCell cell = new PdfPCell();
            com.lowagie.text.Font font = FontFactory.getFont(FontFactory.COURIER);
            cell.setPadding(5);

            if (contador%2 == 1) cell.setBackgroundColor(new Color(216, 230, 217));
            else cell.setBackgroundColor(new Color(200, 219, 201));

            cell.setPhrase(new Phrase(despesa.getDescricao(), font));
            table.addCell(cell);

            cell.setPhrase(new Phrase(converteValorDoubleParaValorMonetario(despesa.getValor()), font));
            table.addCell(cell);

            cell.setPhrase(new Phrase(despesa.getTipoDespesa().getDesc(), font));
            table.addCell(cell);

            cell.setPhrase(new Phrase(despesa.getStatusDespesa().getDesc(), font));
            table.addCell(cell);

            cell.setPhrase(new Phrase(converteDataUsParaDataBr(despesa.getDataAgendamento()), font));
            table.addCell(cell);

            cell.setPhrase(new Phrase(converteDataUsParaDataBr(despesa.getDataPagamento()), font));
            table.addCell(cell);

            contador++;
        }
    }

    public void export(HttpServletResponse response,
                       List<DespesaEntity> despesas,
                       DespesaService DespesaServiceDespesaService,
                       HashMap<String, String> filtros,
                       UsuarioRepository usuarioRepository)
            throws DocumentException, IOException {

        constroiLayoutArquivo(
                response,
                filtros,
                usuarioRepository,
                despesas,
                DespesaServiceDespesaService,
                constroiParagrafoTitulo(filtros),
                constroiParagrafoDataHora(usuarioRepository),
                constroiTabelaInformativos(despesas, DespesaServiceDespesaService),
                constroiTabelaObjetos(despesas));

    }

    public void constroiLayoutArquivo(HttpServletResponse response,
                                      HashMap<String, String> filtros,
                                      UsuarioRepository usuarioRepository,
                                      java.util.List<DespesaEntity> despesas,
                                      DespesaService DespesaService,
                                      Paragraph paragrafoTitulo,
                                      Paragraph paragrafoDataHora,
                                      PdfPTable informativos,
                                      PdfPTable objetos) throws IOException {

        Document document = new Document(PageSize.A3);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
        document.add(constroiParagrafoTitulo(filtros));
        document.add(constroiParagrafoDataHora(usuarioRepository));
        document.add(constroiTabelaObjetos(despesas));
        document.add(constroiTabelaInformativos(despesas, DespesaService));
        document.close();
    }
    public Paragraph constroiParagrafoTitulo(HashMap<String, String> filtros) {

        String titulo = null;

        for (Map.Entry<String, String> set : filtros.entrySet()) {
            if (set.getKey().equals("descricao") && set.getValue() != null) {
                titulo = "Relatório de despesas de nome " + filtros.get("descricao");
                break;
            }
            else if(set.getKey().equals("inicio") && set.getValue() != null || set.getKey().equals("fim") && set.getValue() != null) {
                titulo = "Relatório de despesas cadastradas do dia " + converteDataUsParaDataBr(filtros.get("inicio")) + " ao dia " + converteDataUsParaDataBr(filtros.get("fim"));
                break;
            }
            else if(set.getKey().equals("mes") && set.getValue() != null || set.getKey().equals("ano") && set.getValue() != null) {
                titulo = "Relatório de despesas cadastradas no mês " + filtros.get("mes") + " de " + filtros.get("ano");
                break;
            }
            else {
                titulo = "Relatório geral de despesas";
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

    public PdfPTable constroiTabelaInformativos(java.util.List<DespesaEntity> despesas,
                                                DespesaService despesaService) {

        PdfPTable table;
        table = new PdfPTable(2);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {4f, 4f});
        table.setSpacingBefore(15);
        table.setSpacingAfter(15);

        Font font = FontFactory.getFont(FontFactory.COURIER, 12, Color.BLACK);

        PdfPCell cell = new PdfPCell();
        cell.setPadding(5);
        cell.setPaddingBottom(8);
        cell.setBackgroundColor(new Color(125, 161, 129));
        cell.setBorderColor(new Color(125, 161, 129));
        cell.setHorizontalAlignment(Cell.ALIGN_CENTER);

        cell.setPhrase(new Phrase("Total Pendente: "
                + (converteValorDoubleParaValorMonetario(despesaService.calculaValorPendente(despesas))), font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Total Pago: "
                + (converteValorDoubleParaValorMonetario(despesaService.calculaValorPago(despesas))), font));
        table.addCell(cell);

        return table;

    }
    public PdfPTable constroiTabelaObjetos(List<DespesaEntity> despesas) {
        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {2.5f, 2.5f, 2.5f, 2.5f, 2.5f, 2.5f});
        table.setSpacingBefore(15);
        escreveHeaderTabela(table);
        escreveDadosTabela(table, despesas);
        return table;
    }

}
