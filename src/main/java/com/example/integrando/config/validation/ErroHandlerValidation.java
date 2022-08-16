package com.example.integrando.config.validation;

import com.example.integrando.exception.CpfValidationException;
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
    public List<ErroDTO> handle(MethodArgumentNotValidException exception) {
        List<ErroDTO> dto = new ArrayList<>();

        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        fieldErrors.forEach( e-> {
            String mensagem = messageSource.getMessage(e, LocaleContextHolder.getLocale());
            ErroDTO erro = new ErroDTO(e.getField(), mensagem);

            dto.add(erro);
        });

        return dto;
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CpfValidationException.class)
    public ErroDTO cpfValidationHandle(CpfValidationException exception) {
       return new ErroDTO("cpf", exception.getMessage());
    }

}
