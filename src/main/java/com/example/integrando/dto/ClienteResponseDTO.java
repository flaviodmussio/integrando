package com.example.integrando.dto;

public class ClienteResponseDTO {

    private String status;
    private ClienteDTO cliente;

    public ClienteResponseDTO(String status, ClienteDTO cliente) {
        this.status = status;
        this.cliente = cliente;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ClienteDTO getCliente() {
        return cliente;
    }

    public void setCliente(ClienteDTO cliente) {
        this.cliente = cliente;
    }
}
