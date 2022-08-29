package br.com.twobrothers.msvendas.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@ControllerAdvice
@RestController
public class ExceptionHandler extends ResponseEntityExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<StandartError> invalidRequestException(InvalidRequestException invalidRequestException,
                                                                 HttpServletRequest httpServletRequest) {

        StandartError standartError = new StandartError(
                LocalDateTime.now(),
                400,
                invalidRequestException.getMessage(),
                httpServletRequest.getRequestURI());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(standartError);

    }

    @org.springframework.web.bind.annotation.ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandartError> objectNotFoundException(ObjectNotFoundException objectNotFoundException,
                                                                 HttpServletRequest httpServletRequest) {

        StandartError standartError = new StandartError(
                LocalDateTime.now(),
                404,
                objectNotFoundException.getMessage(),
                httpServletRequest.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(standartError);

    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {

        StandartError standartError = new StandartError(
                LocalDateTime.now(),
                500,
                "Houve uma falha de violação de dados",
                request.getContextPath()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(standartError);
    }

}
