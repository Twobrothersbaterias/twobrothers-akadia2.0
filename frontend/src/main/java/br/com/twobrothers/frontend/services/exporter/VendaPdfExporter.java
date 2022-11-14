package br.com.twobrothers.frontend.services.exporter;

import br.com.twobrothers.frontend.models.entities.OrdemEntity;
import br.com.twobrothers.frontend.repositories.ClienteRepository;
import br.com.twobrothers.frontend.repositories.UsuarioRepository;
import br.com.twobrothers.frontend.services.OrdemService;
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
public class VendaPdfExporter {

    private void escreveHeaderTabela(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(new Color(125, 161, 129));
        cell.setPadding(10);
        cell.setPaddingBottom(15);
        cell.setHorizontalAlignment(Cell.ALIGN_CENTER);

        com.lowagie.text.Font font = FontFactory.getFont(FontFactory.COURIER, 9, Color.BLACK);

        cell.setPhrase(new Phrase("Data", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("PDV", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Vendas/Serviços", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Retirada", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Técnico", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Cliente", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Total", font));
        table.addCell(cell);


    }

    private void escreveDadosTabela(PdfPTable table, java.util.List<OrdemEntity> ordens) {

        Integer contador = 1;

        for (OrdemEntity ordem : ordens) {

            PdfPCell cell = new PdfPCell();
            com.lowagie.text.Font font = FontFactory.getFont(FontFactory.COURIER, 8, Color.BLACK);
            cell.setPadding(5);

            if (contador % 2 == 1) cell.setBackgroundColor(new Color(216, 230, 217));
            else cell.setBackgroundColor(new Color(200, 219, 201));

            cell.setPhrase(new Phrase(converteDataUsParaDataBr(ordem.getDataCadastro()), font));
            table.addCell(cell);

            cell.setPhrase(new Phrase(ordem.getLoja().getDesc(), font));
            table.addCell(cell);

            cell.setPhrase(new Phrase(ordem.getEntradasPorNomeEmString(), font));
            table.addCell(cell);

            cell.setPhrase(new Phrase(ordem.getRetirada().getStatusRetirada().getDescResumida(), font));
            table.addCell(cell);

            cell.setPhrase(new Phrase(ordem.getRetirada().getTecnicoEntrada().getNome(), font));
            table.addCell(cell);

            String nomeCliente = "";
            if (ordem.getCliente() == null) nomeCliente = "Vazio";
            else nomeCliente = ordem.getCliente().getNomeCompleto();
            cell.setPhrase(new Phrase(nomeCliente, font));
            table.addCell(cell);

            cell.setPhrase(new Phrase(converteValorDoubleParaValorMonetario(ordem.getTotalVendas()).toString(), font));
            table.addCell(cell);

            contador++;
        }
    }

    public void export(HttpServletResponse response,
                       java.util.List<OrdemEntity> ordens,
                       OrdemService ordemService,
                       HashMap<String, String> filtros,
                       UsuarioRepository usuarioRepository,
                       ClienteRepository clienteRepository)
            throws DocumentException, IOException {

        constroiLayoutArquivo(
                response,
                filtros,
                usuarioRepository,
                clienteRepository,
                ordens,
                ordemService,
                constroiParagrafoTitulo(filtros, clienteRepository),
                constroiParagrafoDataHora(usuarioRepository),
                constroiTabelaInformativos(ordens, ordemService),
                constroiTabelaObjetos(ordens));

    }

    public void constroiLayoutArquivo(HttpServletResponse response,
                                      HashMap<String, String> filtros,
                                      UsuarioRepository usuarioRepository,
                                      ClienteRepository clienteRepository,
                                      java.util.List<OrdemEntity> ordens,
                                      OrdemService ordemService,
                                      Paragraph paragrafoTitulo,
                                      Paragraph paragrafoDataHora,
                                      PdfPTable informativos,
                                      PdfPTable objetos) throws IOException {

        Document document = new Document(PageSize.A3);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
        document.add(constroiParagrafoTitulo(filtros, clienteRepository));
        document.add(constroiParagrafoDataHora(usuarioRepository));
        document.add(constroiTabelaObjetos(ordens));
        document.add(constroiTabelaInformativos(ordens, ordemService));
        document.close();
    }

    public Paragraph constroiParagrafoTitulo(HashMap<String, String> filtros, ClienteRepository clienteRepository) {

        String chave = null, titulo = null;

        for (Map.Entry<String, String> set : filtros.entrySet()) {
            if (set.getKey().equals("produto") && set.getValue() != null) {
                titulo = "Relatório de ordens de produto de sigla " + filtros.get("produto");
                break;
            } else if (set.getKey().equals("bairro") && set.getValue() != null) {
                titulo = "Relatório de ordens de cliente residente no bairro" + filtros.get("bairro");
                break;
            } else if (set.getKey().equals("cliente") && set.getValue() != null) {
                titulo = "Relatório de ordens do cliente "
                        + clienteRepository.findById(Long.parseLong(filtros.get("cliente"))).get().getNomeCompleto();
                break;
            } else if (set.getKey().equals("inicio") && set.getValue() != null || set.getKey().equals("fim") && set.getValue() != null) {
                titulo = "Relatório de ordens cadastradas do dia " + converteDataUsParaDataBr(filtros.get("inicio"))
                        + " ao dia " + converteDataUsParaDataBr(filtros.get("fim"));
                break;
            } else if (set.getKey().equals("mes") && set.getValue() != null || set.getKey().equals("ano") && set.getValue() != null) {
                titulo = "Relatório de ordens cadastradas no mês " + filtros.get("mes")
                        + " de " + filtros.get("ano");
                break;
            } else if (set.getKey().equals("marca") && set.getValue() != null) {
                titulo = "Relatório de ordens com o produto " + filtros.get("marca");
                break;
            } else if (set.getKey().equals("pdv") && set.getValue() != null) {
                titulo = "Relatório de ordens cadastradas no ponto de venda " + filtros.get("pdv");
                break;
            } else {
                titulo = "Relatório geral de vendas";
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

    public PdfPTable constroiTabelaInformativos(java.util.List<OrdemEntity> ordens,
                                                OrdemService ordemService) {

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

        cell.setPhrase(new Phrase("Baterias vendidas: "
                + ordemService.calculaQuantidadeVendida(ordens), font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Valor bruto: "
                + converteValorDoubleParaValorMonetario(ordemService.calculaBrutoVendido(ordens)), font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Custo: "
                + converteValorDoubleParaValorMonetario(ordemService.calculaCustoVenda(ordens)), font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Total líquido: "
                + converteValorDoubleParaValorMonetario((ordemService.calculaBrutoVendido(ordens)
                - ordemService.calculaCustoVenda(ordens))), font));
        table.addCell(cell);

        return table;

    }

    public PdfPTable constroiTabelaObjetos(List<OrdemEntity> ordens) {
        PdfPTable table = new PdfPTable(7);
        table.setWidthPercentage(100f);
        table.setWidths(new float[]{2f, 2f, 5f, 3f, 3f, 4f, 2f});
        table.setSpacingBefore(15);
        escreveHeaderTabela(table);
        escreveDadosTabela(table, ordens);
        return table;
    }

}
