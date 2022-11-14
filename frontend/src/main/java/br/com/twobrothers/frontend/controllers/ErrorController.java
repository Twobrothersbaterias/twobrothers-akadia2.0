package br.com.twobrothers.frontend.controllers;

import br.com.twobrothers.frontend.repositories.UsuarioRepository;
import br.com.twobrothers.frontend.repositories.services.exceptions.ErrorLog;
import br.com.twobrothers.frontend.services.exporter.ErrorPdfExporter;
import com.lowagie.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

    @Autowired
    UsuarioRepository usuarioRepository;

    @RequestMapping("/error")
    public ModelAndView error(ModelAndView modelAndView, HttpServletRequest req, ModelMap modelMap) {

        String baseUrl = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath();
        String completeUrl = baseUrl + req.getRequestURI() + "/" + req.getQueryString();

        Object status = req.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Throwable throwable = (Throwable) req.getAttribute(RequestDispatcher.ERROR_EXCEPTION);

        if(throwable != null) {
            modelAndView.addObject("code", status);
            modelAndView.addObject("message", throwable.getMessage());
            modelAndView.addObject("trace", Arrays.toString(throwable.getStackTrace()));
            modelAndView.addObject("cause", throwable.getCause());
            modelAndView.addObject("localizedMessage", throwable.getLocalizedMessage());
            modelAndView.addObject("url", completeUrl);
        }

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());

            if(statusCode == HttpStatus.NOT_FOUND.value()) {
                modelAndView.addObject("title", "Perdido?");
                modelAndView.addObject("text", "A página que você tentou acessar não foi " +
                        "encontrada. Mas você pode voltar para a página inicial clicando no botão abaixo");
            }
            else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()){
                modelAndView.addObject("title", "Ops! Ocorreu uma falha técnica");
                modelAndView.addObject("text", "Ocorreu um erro interno no sistema. Envie imediatamente " +
                        "o log de erro para o desenvolvedor responsável para que essa falha seja corrigida");

            }
        }
        modelAndView.setViewName("error");
        return modelAndView;
    }

    @PostMapping("/error-log")
    public void relatorio(ErrorLog errorLog,
                          HttpServletResponse res) throws DocumentException, IOException {

        res.setContentType("application/pdf");
        String headerKey = "Content-Disposition";
        String headerValue = "attachement; filename=akadia_error_"
                + new SimpleDateFormat("dd.MM.yyyy_HHmmss").format(new Date())
                + ".pdf";
        res.setHeader(headerKey, headerValue);
        ErrorPdfExporter pdfExporterService = new ErrorPdfExporter();
        pdfExporterService.export(res, errorLog, usuarioRepository);
    }

}
