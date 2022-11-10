package br.com.twobrothers.frontend.services.exporter;

import br.com.twobrothers.frontend.models.entities.PrecoFornecedorEntity;
import br.com.twobrothers.frontend.repositories.FornecedorRepository;
import br.com.twobrothers.frontend.repositories.UsuarioRepository;
import br.com.twobrothers.frontend.services.PrecoService;
import br.com.twobrothers.frontend.utils.UsuarioUtils;
import com.lowagie.text.Font;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
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
public class PrecoPdfExporter {

    private void escreveHeaderTabela(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(new Color(125, 161, 129));
        cell.setPadding(5);
        cell.setPaddingBottom(10);
        cell.setHorizontalAlignment(Cell.ALIGN_CENTER);

        com.lowagie.text.Font font = FontFactory.getFont(FontFactory.COURIER, 10, Color.BLACK);

        cell.setPhrase(new Phrase("Cadastro", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Produto", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Fornecedor", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Preço", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Observação", font));
        table.addCell(cell);

    }

    private void escreveDadosTabela(PdfPTable table, java.util.List<PrecoFornecedorEntity> precos) {

        Integer contador = 1;

        for (PrecoFornecedorEntity preco : precos) {

            PdfPCell cell = new PdfPCell();
            com.lowagie.text.Font font = FontFactory.getFont(FontFactory.COURIER, 8, Color.BLACK);
            cell.setPadding(5);

            if (contador % 2 == 1) cell.setBackgroundColor(new Color(216, 230, 217));
            else cell.setBackgroundColor(new Color(200, 219, 201));

            cell.setPhrase(new Phrase(converteDataUsParaDataBr(preco.getDataCadastro()), font));
            table.addCell(cell);

            cell.setPhrase(new Phrase(preco.getProduto().getSigla(), font));
            table.addCell(cell);

            String fornecedor = "Vazio";
            if (preco.getFornecedor() != null) fornecedor = preco.getFornecedor().getNome();
            cell.setPhrase(new Phrase(fornecedor, font));
            table.addCell(cell);

            cell.setPhrase(new Phrase(converteValorDoubleParaValorMonetario(preco.getValor()), font));
            table.addCell(cell);

            String observacao = "Vazio";
            if (preco.getObservacao() != null && !preco.getObservacao().isEmpty()) observacao = preco.getObservacao();
            cell.setPhrase(new Phrase(observacao, font));
            table.addCell(cell);

            contador++;
        }
    }

    public void export(HttpServletResponse response,
                       java.util.List<PrecoFornecedorEntity> precos,
                       PrecoService precoService,
                       HashMap<String, String> filtros,
                       UsuarioRepository usuarioRepository,
                       FornecedorRepository fornecedorRepository)
            throws DocumentException, IOException {

        constroiLayoutArquivo(
                response,
                filtros,
                usuarioRepository,
                fornecedorRepository,
                precos,
                precoService,
                constroiParagrafoTitulo(filtros, fornecedorRepository),
                constroiParagrafoDataHora(usuarioRepository),
                constroiTabelaInformativos(precos, precoService),
                constroiTabelaObjetos(precos));

    }

    public void constroiLayoutArquivo(HttpServletResponse response,
                                      HashMap<String, String> filtros,
                                      UsuarioRepository usuarioRepository,
                                      FornecedorRepository fornecedorRepository,
                                      java.util.List<PrecoFornecedorEntity> precos,
                                      PrecoService precoService,
                                      Paragraph paragrafoTitulo,
                                      Paragraph paragrafoDataHora,
                                      PdfPTable informativos,
                                      PdfPTable objetos) throws IOException {

        Document document = new Document(PageSize.A3);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
        document.add(constroiParagrafoTitulo(filtros, fornecedorRepository));
        document.add(constroiParagrafoDataHora(usuarioRepository));
        document.add(constroiTabelaObjetos(precos));
        document.add(constroiTabelaInformativos(precos, precoService));
        document.close();
    }

    public Paragraph constroiParagrafoTitulo(HashMap<String, String> filtros, FornecedorRepository fornecedorRepository) {

        String titulo = null;

        for (Map.Entry<String, String> set : filtros.entrySet()) {
            if (set.getKey().equals("fornecedorId") && set.getValue() != null) {
                titulo = "Relatório de preços do fornecedor "
                        + fornecedorRepository.findById(Long.parseLong(filtros.get("fornecedorId"))).get().getNome();
                break;
            } else if (set.getKey().equals("fornecedor") && set.getValue() != null) {
                titulo = "Relatório de preços de fornecedores de nome " + filtros.get("fornecedor");
                break;
            } else if (set.getKey().equals("produtoId") && set.getValue() != null) {
                titulo = "Relatório de preços do produto " + filtros.get("produtoId");
                break;
            } else if (set.getKey().equals("produto") && set.getValue() != null) {
                titulo = "Relatório de preços de produtos de sigla " + filtros.get("produto");
                break;
            } else {
                titulo = "Relatório geral de preços";
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

    public PdfPTable constroiTabelaInformativos(java.util.List<PrecoFornecedorEntity> precos,
                                                PrecoService precoService) {

        PdfPTable table;
        table = new PdfPTable(1);
        table.setWidthPercentage(100f);
        table.setWidths(new float[]{4f});
        table.setSpacingBefore(15);
        table.setSpacingAfter(15);

        Font font = FontFactory.getFont(FontFactory.COURIER, 8, Color.BLACK);

        PdfPCell cell = new PdfPCell();
        cell.setPadding(5);
        cell.setPaddingBottom(8);
        cell.setBackgroundColor(new Color(125, 161, 129));
        cell.setBorderColor(new Color(125, 161, 129));
        cell.setHorizontalAlignment(Cell.ALIGN_RIGHT);

        cell.setPhrase(new Phrase("Quantidade de preços: " + precos.size(), font));
        table.addCell(cell);

        return table;

    }

    public PdfPTable constroiTabelaObjetos(List<PrecoFornecedorEntity> precos) {
        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100f);
        table.setWidths(new float[]{1.25f, 1.25f, 2.5f, 1.25f, 3.75f});
        table.setSpacingBefore(15);
        escreveHeaderTabela(table);
        escreveDadosTabela(table, precos);
        return table;
    }
}