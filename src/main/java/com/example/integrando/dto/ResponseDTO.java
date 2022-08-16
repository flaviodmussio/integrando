package com.example.integrando.dto;

public class ResponseDTO<T> {

    private String status;
    private T mensagem;

    public ResponseDTO(String status, T mensagem) {
        this.status = status;
        this.mensagem = mensagem;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public T getMensagem() {
        return mensagem;
    }

    public void setMensagem(T mensagem) {
        this.mensagem = mensagem;
    }
}
