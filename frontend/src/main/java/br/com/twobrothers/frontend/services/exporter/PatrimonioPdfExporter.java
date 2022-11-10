package br.com.twobrothers.frontend.services.exporter;

import br.com.twobrothers.frontend.models.entities.PatrimonioEntity;
import br.com.twobrothers.frontend.repositories.UsuarioRepository;
import br.com.twobrothers.frontend.services.PatrimonioService;
import br.com.twobrothers.frontend.utils.UsuarioUtils;
import com.lowagie.text.*;
import com.lowagie.text.Font;
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
public class PatrimonioPdfExporter {

    private void escreveHeaderTabela(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(new Color(125, 161, 129));
        cell.setPadding(10);
        cell.setPaddingBottom(15);
        cell.setHorizontalAlignment(Cell.ALIGN_CENTER);

        com.lowagie.text.Font font = FontFactory.getFont(FontFactory.COURIER, 12, Color.BLACK);

        cell.setPhrase(new Phrase("Cadastro", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Descrição", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Status", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Tipo", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Valor", font));
        table.addCell(cell);

    }

    private void escreveDadosTabela(PdfPTable table, java.util.List<PatrimonioEntity> patrimonios) {

        Integer contador = 1;

        for (PatrimonioEntity patrimonio : patrimonios) {

            PdfPCell cell = new PdfPCell();
            com.lowagie.text.Font font = FontFactory.getFont(FontFactory.COURIER, 12, Color.BLACK);
            cell.setPadding(5);

            if (contador % 2 == 1) cell.setBackgroundColor(new Color(216, 230, 217));
            else cell.setBackgroundColor(new Color(200, 219, 201));

            cell.setPhrase(new Phrase(converteDataUsParaDataBr(patrimonio.getDataCadastro()), font));
            table.addCell(cell);

            cell.setPhrase(new Phrase(patrimonio.getNome(), font));
            table.addCell(cell);

            cell.setPhrase(new Phrase(patrimonio.getStatusPatrimonio().getDescResumida(), font));
            table.addCell(cell);

            cell.setPhrase(new Phrase(patrimonio.getTipoPatrimonio().getDesc(), font));
            table.addCell(cell);

            cell.setPhrase(new Phrase(converteValorDoubleParaValorMonetario(patrimonio.getValor()), font));
            table.addCell(cell);

            contador++;
        }
    }

    public void export(HttpServletResponse response,
                       java.util.List<PatrimonioEntity> patrimonios,
                       PatrimonioService patrimonioService,
                       HashMap<String, String> filtros,
                       UsuarioRepository usuarioRepository)
            throws DocumentException, IOException {

        constroiLayoutArquivo(
                response,
                filtros,
                usuarioRepository,
                patrimonios,
                patrimonioService,
                constroiParagrafoTitulo(filtros),
                constroiParagrafoDataHora(usuarioRepository),
                constroiTabelaInformativos(patrimonios, patrimonioService),
                constroiTabelaObjetos(patrimonios));

    }

    public void constroiLayoutArquivo(HttpServletResponse response,
                                      HashMap<String, String> filtros,
                                      UsuarioRepository usuarioRepository,
                                      java.util.List<PatrimonioEntity> patrimonios,
                                      PatrimonioService patrimonioService,
                                      Paragraph paragrafoTitulo,
                                      Paragraph paragrafoDataHora,
                                      PdfPTable informativos,
                                      PdfPTable objetos) throws IOException {

        Document document = new Document(PageSize.A3);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
        document.add(constroiParagrafoTitulo(filtros));
        document.add(constroiParagrafoDataHora(usuarioRepository));
        document.add(constroiTabelaObjetos(patrimonios));
        document.add(constroiTabelaInformativos(patrimonios, patrimonioService));
        document.close();
    }

    public Paragraph constroiParagrafoTitulo(HashMap<String, String> filtros) {

        String chave = null, titulo = null;

        for (Map.Entry<String, String> set : filtros.entrySet()) {
            if (set.getKey().equals("descricao") && set.getValue() != null) {
                titulo = "Relatório de patrimonios de nome " + filtros.get("descricao");
                break;
            } else if (set.getKey().equals("tipo") && set.getValue() != null) {
                titulo = "Relatório de patrimonios de tipo " + filtros.get("tipo");
                break;
            }  else if (set.getKey().equals("mes") && set.getValue() != null || set.getKey().equals("ano") && set.getValue() != null) {
                titulo = "Relatório de patrimonios cadastrados no mês " + filtros.get("mes")
                        + " de " + filtros.get("ano");
                break;
            } else {
                titulo = "Relatório geral de patrimônios";
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

    public PdfPTable constroiTabelaInformativos(java.util.List<PatrimonioEntity> patrimonios,
                                                PatrimonioService patrimonioService) {

        PdfPTable table;
        table = new PdfPTable(4);
        table.setWidthPercentage(100f);
        table.setWidths(new float[]{4f, 4f, 4f, 4f});
        table.setSpacingBefore(15);
        table.setSpacingAfter(15);

        Font font = FontFactory.getFont(FontFactory.COURIER, 9, Color.BLACK);

        PdfPCell cell = new PdfPCell();
        cell.setPadding(5);
        cell.setPaddingBottom(8);
        cell.setBackgroundColor(new Color(125, 161, 129));
        cell.setBorderColor(new Color(125, 161, 129));
        cell.setHorizontalAlignment(Cell.ALIGN_CENTER);

        cell.setPhrase(new Phrase("Bruto: "
                + converteValorDoubleParaValorMonetario(patrimonioService.calculaValorBruto(patrimonios)), font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Passivos: "
                + converteValorDoubleParaValorMonetario(patrimonioService.calculaValorPassivos(patrimonios)), font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Pendente: "
                + converteValorDoubleParaValorMonetario(patrimonioService.calculaValorPendente(patrimonios)), font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Em caixa: "
                + converteValorDoubleParaValorMonetario((patrimonioService.calculaValorCaixa(patrimonios))), font));
        table.addCell(cell);

        return table;

    }

    public PdfPTable constroiTabelaObjetos(List<PatrimonioEntity> patrimonios) {
        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100f);
        table.setWidths(new float[]{2f, 2f, 2f, 2f, 2f});
        table.setSpacingBefore(15);
        escreveHeaderTabela(table);
        escreveDadosTabela(table, patrimonios);
        return table;
    }


}
