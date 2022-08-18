package com.example.integrando.config.validation;

import com.example.integrando.exception.CpfValidationException;
import com.example.integrando.exception.PacoteTarifasRemoveException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ErroHandlerValidation {

    @Autowired
    private MessageSource messageSource;

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<ErroFormularioDTO> argumentNotValidHandle(MethodArgumentNotValidException exception) {
        List<ErroFormularioDTO> dto = new ArrayList<>();

        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        fieldErrors.forEach( e-> {
            String mensagem = messageSource.getMessage(e, LocaleContextHolder.getLocale());
            ErroFormularioDTO erro = new ErroFormularioDTO(e.getField(), mensagem);

            dto.add(erro);
        });

        return dto;
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CpfValidationException.class)
    public ErroFormularioDTO cpfValidationHandle(CpfValidationException exception) {
       return new ErroFormularioDTO("cpf", exception.getMessage());
    }

    @ExceptionHandler(PacoteTarifasRemoveException.class)
    public ErroDTO pacoteTarifasRemocaoHandle(PacoteTarifasRemoveException pacoteTarifasRemoveException) {
        return new ErroDTO(pacoteTarifasRemoveException.getMessage());
    }

}
