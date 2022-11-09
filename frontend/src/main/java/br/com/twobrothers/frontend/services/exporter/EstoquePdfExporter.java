package br.com.twobrothers.frontend.services.exporter;

import br.com.twobrothers.frontend.models.entities.ProdutoEstoqueEntity;
import br.com.twobrothers.frontend.repositories.UsuarioRepository;
import br.com.twobrothers.frontend.services.ProdutoEstoqueService;
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

import static br.com.twobrothers.frontend.utils.ConversorDeDados.converteValorDoubleParaValorMonetario;


@Service
public class EstoquePdfExporter {

    private void escreveHeaderTabela(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(new Color(125, 161, 129));
        cell.setPadding(10);
        cell.setPaddingBottom(15);
        cell.setHorizontalAlignment(Cell.ALIGN_CENTER);

        Font font = FontFactory.getFont(FontFactory.COURIER);
        font.setSize(12);
        font.setColor(Color.BLACK);

        cell.setPhrase(new Phrase("Sigla", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Descrição", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Marca", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Qtd", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Valor u.", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Valor tot.", font));
        table.addCell(cell);

    }

    private void escreveDadosTabela(PdfPTable table, List<ProdutoEstoqueEntity> produtos) {

        Integer contador = 1;

        for (ProdutoEstoqueEntity produto : produtos) {

            PdfPCell cell = new PdfPCell();
            Font font = FontFactory.getFont(FontFactory.COURIER);
            cell.setPadding(5);

            if (contador%2 == 1) cell.setBackgroundColor(new Color(216, 230, 217));
            else cell.setBackgroundColor(new Color(200, 219, 201));

            cell.setPhrase(new Phrase(produto.getSigla(), font));
            table.addCell(cell);

            cell.setPhrase(new Phrase(produto.getEspecificacao(), font));
            table.addCell(cell);

            cell.setPhrase(new Phrase(produto.getMarcaBateria(), font));
            table.addCell(cell);

            cell.setPhrase(new Phrase(produto.getQuantidade().toString(), font));
            table.addCell(cell);

            cell.setPhrase(new Phrase(converteValorDoubleParaValorMonetario(produto.getCustoUnitario()).toString(), font));
            table.addCell(cell);

            cell.setPhrase(new Phrase(converteValorDoubleParaValorMonetario(produto.getCustoTotal()).toString(), font));
            table.addCell(cell);

            contador++;
        }
    }

    public void export(HttpServletResponse response,
                       List<ProdutoEstoqueEntity> produtos,
                       ProdutoEstoqueService produtoEstoqueService,
                       HashMap<String, String> filtros,
                       UsuarioRepository usuarioRepository)
            throws DocumentException, IOException {

        constroiLayoutArquivo(
                response,
                filtros,
                usuarioRepository,
                produtos,
                produtoEstoqueService,
                constroiParagrafoTitulo(filtros),
                constroiParagrafoDataHora(usuarioRepository),
                constroiTabelaInformativos(produtos, produtoEstoqueService),
                constroiTabelaObjetos(produtos));

    }

    public void constroiLayoutArquivo(HttpServletResponse response,
                                      HashMap<String, String> filtros,
                                      UsuarioRepository usuarioRepository,
                                      List<ProdutoEstoqueEntity> produtos,
                                      ProdutoEstoqueService produtoEstoqueService,
                                      Paragraph paragrafoTitulo,
                                      Paragraph paragrafoDataHora,
                                      PdfPTable informativos,
                                      PdfPTable objetos) throws IOException {

        Document document = new Document(PageSize.A3);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
        document.add(constroiParagrafoTitulo(filtros));
        document.add(constroiParagrafoDataHora(usuarioRepository));
        document.add(constroiTabelaObjetos(produtos));
        document.add(constroiTabelaInformativos(produtos, produtoEstoqueService));
        document.close();
    }
    public Paragraph constroiParagrafoTitulo(HashMap<String, String> filtros) {

        String chave = null, titulo = null;

        for (Map.Entry<String, String> set : filtros.entrySet()) {
            if (set.getKey().equals("descricao") && set.getValue() != null) {
                titulo = "Relatório de estoque com produtos de sigla " + filtros.get("descricao");
                break;
            }
            else if(set.getKey().equals("inicio") && set.getValue() != null || set.getKey().equals("fim") && set.getValue() != null) {
                titulo = "Relatório de estoque com produtos cadastrados do dia " + filtros.get("inicio") + " ao dia " + filtros.get("fim");
                break;
            }
            else if(set.getKey().equals("mes") && set.getValue() != null || set.getKey().equals("ano") && set.getValue() != null) {
                titulo = "Relatório de estoque de produtos cadastrados no mês " + filtros.get("mes") + " de " + filtros.get("ano");
                break;
            }
            else {
                titulo = "Relatório geral de estoque";
            }
        }

        Font font = FontFactory.getFont(FontFactory.COURIER_BOLD, 15, Color.BLACK);
        Paragraph p = new Paragraph(titulo, font);
        p.setAlignment(Paragraph.ALIGN_CENTER);
        p.setSpacingBefore(0);
        p.setSpacingAfter(5);

        return p;
    }

    public Paragraph constroiParagrafoDataHora(UsuarioRepository usuarioRepository) {

        String currentDateTime = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss").format(new Date());

        Font font = FontFactory.getFont(FontFactory.COURIER, 12, Color.BLACK);
        Paragraph p = new Paragraph(currentDateTime + ", por " + UsuarioUtils.loggedUser(usuarioRepository).getNomeUsuario(), font);
        p.setAlignment(Paragraph.ALIGN_CENTER);
        p.setSpacingBefore(0);
        p.setSpacingAfter(5);
        return p;

    }
    public PdfPTable constroiTabelaInformativos(List<ProdutoEstoqueEntity> produtos,
                       ProdutoEstoqueService produtoEstoqueService) {

        PdfPTable table;
        table = new PdfPTable(3);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {4f, 4f, 4f});
        table.setSpacingBefore(15);
        table.setSpacingAfter(15);

        Font font = FontFactory.getFont(FontFactory.COURIER, 12, Color.BLACK);

        PdfPCell cell = new PdfPCell();
        cell.setPadding(5);
        cell.setPaddingBottom(8);
        cell.setBackgroundColor(new Color(125, 161, 129));
        cell.setBorderColor(new Color(125, 161, 129));
        cell.setHorizontalAlignment(Cell.ALIGN_CENTER);

        cell.setPhrase(new Phrase("Qtd de baterias: "
                + produtoEstoqueService.calculaQtdBaterias(produtos), font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Qtd de sucatas: "
                + produtoEstoqueService.calculaQtdSucatas(produtos), font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Valor em estoque: "
                + converteValorDoubleParaValorMonetario(produtoEstoqueService.calculaValorBruto(produtos)), font));
        table.addCell(cell);

        return table;

    }
    public PdfPTable constroiTabelaObjetos(List<ProdutoEstoqueEntity> produtos) {
        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {2.5f, 2.5f, 2.5f, 1.0f, 2.0f, 2.0f});
        table.setSpacingBefore(15);
        escreveHeaderTabela(table);
        escreveDadosTabela(table, produtos);
        return table;
    }
}
