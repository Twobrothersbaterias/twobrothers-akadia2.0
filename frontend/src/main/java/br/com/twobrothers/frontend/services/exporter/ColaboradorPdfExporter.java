package br.com.twobrothers.frontend.services.exporter;

import br.com.twobrothers.frontend.models.entities.UsuarioEntity;
import br.com.twobrothers.frontend.repositories.UsuarioRepository;
import br.com.twobrothers.frontend.services.UsuarioService;
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
public class ColaboradorPdfExporter {

    private void escreveHeaderTabela(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(new Color(125, 161, 129));
        cell.setPadding(5);
        cell.setPaddingBottom(10);
        cell.setHorizontalAlignment(Cell.ALIGN_CENTER);

        com.lowagie.text.Font font = FontFactory.getFont(FontFactory.COURIER, 10, Color.BLACK);

        cell.setPhrase(new Phrase("Cadastro", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Nome", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Acesso", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Nível", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("E-mail", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Telefone", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Nascimento", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Cpf", font));
        table.addCell(cell);

    }

    private void escreveDadosTabela(PdfPTable table, java.util.List<UsuarioEntity> usuarios) {

        Integer contador = 1;

        for (UsuarioEntity usuario : usuarios) {

            PdfPCell cell = new PdfPCell();
            com.lowagie.text.Font font = FontFactory.getFont(FontFactory.COURIER,8, Color.BLACK);
            cell.setPadding(5);

            if (contador%2 == 1) cell.setBackgroundColor(new Color(216, 230, 217));
            else cell.setBackgroundColor(new Color(200, 219, 201));

            cell.setPhrase(new Phrase(converteDataUsParaDataBr(usuario.getDataCadastro()), font));
            table.addCell(cell);

            cell.setPhrase(new Phrase(usuario.getNome(), font));
            table.addCell(cell);

            cell.setPhrase(new Phrase(usuario.getUsername() + ", " + usuario.getSenha(), font));
            table.addCell(cell);

            cell.setPhrase(new Phrase(usuario.getPrivilegio().getDesc(), font));
            table.addCell(cell);

            String email = "Vazio";
            if (usuario.getEmail() != null) email = usuario.getEmail();
            cell.setPhrase(new Phrase(email, font));
            table.addCell(cell);

            String telefone = "Vazio";
            if (usuario.getTelefone() != null) telefone = usuario.getTelefone();
            cell.setPhrase(new Phrase(telefone, font));
            table.addCell(cell);

            String dataNascimento = "Vazio";
            if (usuario.getDataNascimento() != null) dataNascimento = converteDataUsParaDataBr(usuario.getDataNascimento());
            cell.setPhrase(new Phrase(dataNascimento, font));
            table.addCell(cell);

            String cpfCnpj = "Vazio";
            if (usuario.getCpfCnpj() != null) cpfCnpj = usuario.getCpfCnpj();
            cell.setPhrase(new Phrase(cpfCnpj, font));
            table.addCell(cell);

            contador++;
        }
    }

    public void export(HttpServletResponse response,
                       java.util.List<UsuarioEntity> usuarios,
                       UsuarioService usuarioService,
                       HashMap<String, String> filtros,
                       UsuarioRepository usuarioRepository)
            throws DocumentException, IOException {

        constroiLayoutArquivo(
                response,
                filtros,
                usuarioRepository,
                usuarios,
                usuarioService,
                constroiParagrafoTitulo(filtros),
                constroiParagrafoDataHora(usuarioRepository),
                constroiTabelaInformativos(usuarios, usuarioService),
                constroiTabelaObjetos(usuarios));

    }

    public void constroiLayoutArquivo(HttpServletResponse response,
                                      HashMap<String, String> filtros,
                                      UsuarioRepository usuarioRepository,
                                      java.util.List<UsuarioEntity> usuarios,
                                      UsuarioService usuarioService,
                                      Paragraph paragrafoTitulo,
                                      Paragraph paragrafoDataHora,
                                      PdfPTable informativos,
                                      PdfPTable objetos) throws IOException {

        Document document = new Document(PageSize.A3);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
        document.add(constroiParagrafoTitulo(filtros));
        document.add(constroiParagrafoDataHora(usuarioRepository));
        document.add(constroiTabelaObjetos(usuarios));
        document.add(constroiTabelaInformativos(usuarios, usuarioService));
        document.close();
    }
    public Paragraph constroiParagrafoTitulo(HashMap<String, String> filtros) {

        String titulo = null;

        for (Map.Entry<String, String> set : filtros.entrySet()) {
            if (set.getKey().equals("descricao") && set.getValue() != null) {
                titulo = "Relatório de colaboradores de nome " + filtros.get("descricao");
                break;
            }
            else if(set.getKey().equals("inicio") && set.getValue() != null || set.getKey().equals("fim") && set.getValue() != null) {
                titulo = "Relatório de cloaboradores com cadastros do dia " + converteDataUsParaDataBr(filtros.get("inicio")) + " ao dia " + converteDataUsParaDataBr(filtros.get("fim"));
                break;
            }
            else if(set.getKey().equals("mes") && set.getValue() != null || set.getKey().equals("ano") && set.getValue() != null) {
                titulo = "Relatório de colaboradores cadastrados no mês " + filtros.get("mes") + " de " + filtros.get("ano");
                break;
            }
            else {
                titulo = "Relatório geral de colaboradores";
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
    public PdfPTable constroiTabelaInformativos(java.util.List<UsuarioEntity> usuarios,
                                                UsuarioService usuarioService) {

        PdfPTable table;
        table = new PdfPTable(1);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {4f});
        table.setSpacingBefore(15);
        table.setSpacingAfter(15);

        Font font = FontFactory.getFont(FontFactory.COURIER, 8, Color.BLACK);

        PdfPCell cell = new PdfPCell();
        cell.setPadding(5);
        cell.setPaddingBottom(8);
        cell.setBackgroundColor(new Color(125, 161, 129));
        cell.setBorderColor(new Color(125, 161, 129));
        cell.setHorizontalAlignment(Cell.ALIGN_RIGHT);

        cell.setPhrase(new Phrase("Quantidade de colaboradores: " + usuarios.size(), font));
        table.addCell(cell);

        return table;

    }
    public PdfPTable constroiTabelaObjetos(List<UsuarioEntity> usuarios) {
        PdfPTable table = new PdfPTable(8);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {1.5f, 2.5f, 3.0f, 1.5f, 2.5f, 1.75f, 1.5f, 1.75f});
        table.setSpacingBefore(15);
        escreveHeaderTabela(table);
        escreveDadosTabela(table, usuarios);
        return table;
    }

}
