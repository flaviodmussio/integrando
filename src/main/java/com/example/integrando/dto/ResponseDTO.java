package com.example.integrando.dto;

public abstract class ResponseDTO {

    String status;

    public ResponseDTO(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
