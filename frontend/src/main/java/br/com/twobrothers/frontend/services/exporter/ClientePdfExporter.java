package br.com.twobrothers.frontend.services.exporter;

import br.com.twobrothers.frontend.models.entities.ClienteEntity;
import br.com.twobrothers.frontend.repositories.UsuarioRepository;
import br.com.twobrothers.frontend.services.ClienteService;
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

@Service
public class ClientePdfExporter {

    private void escreveHeaderTabela(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(new Color(125, 161, 129));
        cell.setPadding(10);
        cell.setPaddingBottom(15);
        cell.setHorizontalAlignment(Cell.ALIGN_CENTER);

        com.lowagie.text.Font font = FontFactory.getFont(FontFactory.COURIER, 12, Color.BLACK);

        cell.setPhrase(new Phrase("Cadastro", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Nome", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Endereço", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Telefone", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Cpf/Cnpj", font));
        table.addCell(cell);

    }

    private void escreveDadosTabela(PdfPTable table, java.util.List<ClienteEntity> clientes) {

        Integer contador = 1;

        for (ClienteEntity cliente : clientes) {

            PdfPCell cell = new PdfPCell();
            com.lowagie.text.Font font = FontFactory.getFont(FontFactory.COURIER, 10, Color.BLACK);
            cell.setPadding(5);

            if (contador % 2 == 1) cell.setBackgroundColor(new Color(216, 230, 217));
            else cell.setBackgroundColor(new Color(200, 219, 201));

            cell.setPhrase(new Phrase(converteDataUsParaDataBr(cliente.getDataCadastro()), font));
            table.addCell(cell);

            cell.setPhrase(new Phrase(cliente.getNomeCompleto(), font));
            table.addCell(cell);

            String endereço = "Sem endereço";
            if (cliente.getEndereco() != null && cliente.getEndereco().getLogradouro() != null) {
                if (cliente.getEndereco().getBairro() != null) {
                    endereço =
                            cliente.getEndereco().getLogradouro() + ", " +
                                    cliente.getEndereco().getNumero() + " - " +
                                    cliente.getEndereco().getBairro();
                } else {
                    endereço =
                            cliente.getEndereco().getLogradouro() + ", " +
                                    cliente.getEndereco().getNumero() + " - Sem bairro";
                }
            }
            cell.setPhrase(new Phrase(endereço, font));
            table.addCell(cell);

            String telefone = "Sem telefone";
            if (cliente.getTelefone() != null) telefone = cliente.getTelefone();
            cell.setPhrase(new Phrase(telefone, font));
            table.addCell(cell);

            String cpfCnpj = "Sem Cpf/Cnpj";
            if (cliente.getCpfCnpj() != null) cpfCnpj = cliente.getCpfCnpj();
            cell.setPhrase(new Phrase(cpfCnpj, font));
            table.addCell(cell);

            contador++;
        }
    }

    public void export(HttpServletResponse response,
                       java.util.List<ClienteEntity> clientes,
                       ClienteService clienteService,
                       HashMap<String, String> filtros,
                       UsuarioRepository usuarioRepository)
            throws DocumentException, IOException {

        constroiLayoutArquivo(
                response,
                filtros,
                usuarioRepository,
                clientes,
                clienteService,
                constroiParagrafoTitulo(filtros),
                constroiParagrafoDataHora(usuarioRepository),
                constroiTabelaInformativos(clientes, clienteService),
                constroiTabelaObjetos(clientes));

    }

    public void constroiLayoutArquivo(HttpServletResponse response,
                                      HashMap<String, String> filtros,
                                      UsuarioRepository usuarioRepository,
                                      java.util.List<ClienteEntity> clientes,
                                      ClienteService clienteService,
                                      Paragraph paragrafoTitulo,
                                      Paragraph paragrafoDataHora,
                                      PdfPTable informativos,
                                      PdfPTable objetos) throws IOException {

        Document document = new Document(PageSize.A3);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
        document.add(constroiParagrafoTitulo(filtros));
        document.add(constroiParagrafoDataHora(usuarioRepository));
        document.add(constroiTabelaObjetos(clientes));
        document.add(constroiTabelaInformativos(clientes, clienteService));
        document.close();
    }

    public Paragraph constroiParagrafoTitulo(HashMap<String, String> filtros) {

        String titulo = null;

        for (Map.Entry<String, String> set : filtros.entrySet()) {
            if (set.getKey().equals("descricao") && set.getValue() != null) {
                titulo = "Relatório de clientes de nome " + filtros.get("descricao");
                break;
            } else if (set.getKey().equals("cpfCnpj") && set.getValue() != null) {
                titulo = "Relatório de clientes de Cpf/Cnpj " + filtros.get("cpfCnpj");
                break;
            } else if (set.getKey().equals("telefone") && set.getValue() != null) {
                titulo = "Relatório de clientes de telefone " + filtros.get("telefone");
                break;
            } else if (set.getKey().equals("inicio") && set.getValue() != null || set.getKey().equals("fim") && set.getValue() != null) {
                titulo = "Relatório de clientes cadastrados do dia " + converteDataUsParaDataBr(filtros.get("inicio"))
                        + " ao dia " + converteDataUsParaDataBr(filtros.get("fim"));
                break;
            } else if (set.getKey().equals("mes") && set.getValue() != null || set.getKey().equals("ano") && set.getValue() != null) {
                titulo = "Relatório de clientes cadastrados no mês " + filtros.get("mes")
                        + " de " + filtros.get("ano");
                break;
            } else {
                titulo = "Relatório geral de clientes";
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

    public PdfPTable constroiTabelaInformativos(java.util.List<ClienteEntity> clientes,
                                                ClienteService clienteService) {

        PdfPTable table;
        table = new PdfPTable(1);
        table.setWidthPercentage(100f);
        table.setWidths(new float[]{4f});
        table.setSpacingBefore(15);
        table.setSpacingAfter(15);

        Font font = FontFactory.getFont(FontFactory.COURIER, 9, Color.BLACK);

        PdfPCell cell = new PdfPCell();
        cell.setPadding(5);
        cell.setPaddingBottom(8);
        cell.setBackgroundColor(new Color(125, 161, 129));
        cell.setBorderColor(new Color(125, 161, 129));
        cell.setHorizontalAlignment(Cell.ALIGN_RIGHT);

        cell.setPhrase(new Phrase("Quantidade de clientes: " + clientes.size(), font));
        table.addCell(cell);

        return table;

    }

    public PdfPTable constroiTabelaObjetos(List<ClienteEntity> clientes) {
        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100f);
        table.setWidths(new float[]{1f, 2.75f, 3f, 1.5f, 1.75f});
        table.setSpacingBefore(15);
        escreveHeaderTabela(table);
        escreveDadosTabela(table, clientes);
        return table;
    }

}
