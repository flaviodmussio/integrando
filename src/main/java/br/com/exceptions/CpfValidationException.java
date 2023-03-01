package br.com.exceptions;

public class CpfValidationException extends RuntimeException{

    public CpfValidationException(String message) {
        super(message);
    }

}