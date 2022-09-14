package com.example.integrando.dto;

import com.example.integrando.models.PacoteTarifas;

import java.util.List;
import java.util.stream.Collectors;

public class PacoteTarifasListDTO extends PacoteTarifasDTO {

    public List<ClienteDTO> clientes;

    public PacoteTarifasListDTO(PacoteTarifas pacoteTarifas) {
        super(pacoteTarifas);
        this.clientes = pacoteTarifas.getClientes()
                .stream().map(ClienteDTO::new).collect(Collectors.toList());
    }

    public List<ClienteDTO> getClientes() {
        return clientes;
    }

    public void setClientes(List<ClienteDTO> clientes) {
        this.clientes = clientes;
    }
}
