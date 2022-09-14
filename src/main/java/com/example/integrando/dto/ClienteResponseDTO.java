package com.example.integrando.dto;

public class ClienteResponseDTO extends ResponseDTO {

    private ClienteDTO cliente;

    public ClienteResponseDTO(String status, ClienteDTO cliente) {
        super(status);
        this.cliente = cliente;
    }

    public ClienteDTO getCliente() {
        return cliente;
    }

    public void setCliente(ClienteDTO cliente) {
        this.cliente = cliente;
    }
}
