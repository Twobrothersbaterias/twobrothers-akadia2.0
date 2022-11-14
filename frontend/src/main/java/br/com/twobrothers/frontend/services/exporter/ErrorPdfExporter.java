package br.com.twobrothers.frontend.services.exporter;

import br.com.twobrothers.frontend.repositories.UsuarioRepository;
import br.com.twobrothers.frontend.repositories.services.exceptions.ErrorLog;
import br.com.twobrothers.frontend.utils.UsuarioUtils;
import com.lowagie.text.Font;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class ErrorPdfExporter {

    public void export(HttpServletResponse response,
                       ErrorLog errorLog,
                       UsuarioRepository usuarioRepository)
            throws DocumentException, IOException {

        constroiLayoutArquivo(
                response,
                errorLog,
                usuarioRepository);

    }

    public void constroiLayoutArquivo(HttpServletResponse response,
                                      ErrorLog errorLog,
                                      UsuarioRepository usuarioRepository) throws IOException {

        Document document = new Document(PageSize.A3);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
        document.add(constroiParagrafoTitulo(errorLog));
        document.add(constroiParagrafoDataHora(usuarioRepository));

        Paragraph p;
        Font font;

        font = FontFactory.getFont(FontFactory.COURIER_BOLD, 12, Color.BLACK);
        p = new Paragraph("Message: ", font);
        p.setAlignment(Paragraph.ALIGN_LEFT);
        p.setSpacingBefore(15);
        p.setSpacingAfter(5);
        document.add(p);

        font = FontFactory.getFont(FontFactory.COURIER, 9, Color.BLACK);
        p = new Paragraph(errorLog.getMessage(), font);
        p.setAlignment(Paragraph.ALIGN_LEFT);
        p.setSpacingBefore(0);
        p.setSpacingAfter(5);
        document.add(p);

        font = FontFactory.getFont(FontFactory.COURIER_BOLD, 12, Color.BLACK);
        p = new Paragraph("Localized Message: ", font);
        p.setAlignment(Paragraph.ALIGN_LEFT);
        p.setSpacingBefore(15);
        p.setSpacingAfter(5);
        document.add(p);

        font = FontFactory.getFont(FontFactory.COURIER, 9, Color.BLACK);
        p = new Paragraph(errorLog.getLocalizedMessage(), font);
        p.setAlignment(Paragraph.ALIGN_LEFT);
        p.setSpacingBefore(0);
        p.setSpacingAfter(5);
        document.add(p);

        font = FontFactory.getFont(FontFactory.COURIER_BOLD, 12, Color.BLACK);
        p = new Paragraph("Cause: ", font);
        p.setAlignment(Paragraph.ALIGN_LEFT);
        p.setSpacingBefore(15);
        p.setSpacingAfter(5);
        document.add(p);

        font = FontFactory.getFont(FontFactory.COURIER, 9, Color.BLACK);
        p = new Paragraph(errorLog.getCause(), font);
        p.setAlignment(Paragraph.ALIGN_LEFT);
        p.setSpacingBefore(0);
        p.setSpacingAfter(5);
        document.add(p);

        font = FontFactory.getFont(FontFactory.COURIER_BOLD, 12, Color.BLACK);
        p = new Paragraph("Trace: ", font);
        p.setAlignment(Paragraph.ALIGN_LEFT);
        p.setSpacingBefore(15);
        p.setSpacingAfter(5);
        document.add(p);

        font = FontFactory.getFont(FontFactory.COURIER, 9, Color.BLACK);
        p = new Paragraph(errorLog.getTrace(), font);
        p.setAlignment(Paragraph.ALIGN_LEFT);
        p.setSpacingBefore(0);
        p.setSpacingAfter(5);
        document.add(p);

        document.close();
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

    public Paragraph constroiParagrafoTitulo(ErrorLog errorLog) {

        String titulo = "Report de erro ";

        com.lowagie.text.Font font = FontFactory.getFont(FontFactory.COURIER_BOLD, 15, Color.BLACK);
        Paragraph p = new Paragraph(titulo, font);
        p.setAlignment(Paragraph.ALIGN_CENTER);
        p.setSpacingBefore(0);
        p.setSpacingAfter(5);

        return p;
    }

}
