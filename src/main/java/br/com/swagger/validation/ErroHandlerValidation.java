package br.com.swagger.validation;

import br.com.exceptions.ClienteException;
import br.com.exceptions.CpfValidationException;
import br.com.exceptions.PacoteTarifasException;
import br.com.exceptions.PacoteTarifasRemoveException;
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

    @ExceptionHandler(ClienteException.class)
    public ErroDTO clienteHandle(ClienteException clienteException) {
        return new ErroDTO(clienteException.getMessage());
    }

    @ExceptionHandler(PacoteTarifasException.class)
    public ErroDTO pacoteTarifasHandle(PacoteTarifasException pacoteTarifasException) {
        return new ErroDTO(pacoteTarifasException.getMessage());
    }

    @ExceptionHandler(PacoteTarifasRemoveException.class)
    public ErroDTO pacoteTarifasRemocaoHandle(PacoteTarifasRemoveException pacoteTarifasRemoveException) {
        return new ErroDTO(pacoteTarifasRemoveException.getMessage());
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<ErroFormsDTO> argumentNotValidHandle(MethodArgumentNotValidException exception) {
        List<ErroFormsDTO> dto = new ArrayList<>();

        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        fieldErrors.forEach( e-> {
            String mensagem = messageSource.getMessage(e, LocaleContextHolder.getLocale());
            ErroFormsDTO erro = new ErroFormsDTO(e.getField(), mensagem);

            dto.add(erro);
        });

        return dto;
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CpfValidationException.class)
    public ErroFormsDTO cpfValidationHandle(CpfValidationException exception) {
        return new ErroFormsDTO("cpf", exception.getMessage());
    }

}