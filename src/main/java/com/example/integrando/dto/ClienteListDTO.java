package com.example.integrando.dto;

import com.example.integrando.models.Cliente;

public class ClienteListDTO extends ClienteDTO {

    private PacoteTarifasDTO pacoteTarifas;

    public ClienteListDTO(Cliente cliente) {
        super(cliente);
        this.pacoteTarifas = new PacoteTarifasDTO(cliente.getPacoteTarifas());
    }

    public PacoteTarifasDTO getPacoteTarifas() {
        return pacoteTarifas;
    }

    public void setPacoteTarifas(PacoteTarifasDTO pacoteTarifas) {
        this.pacoteTarifas = pacoteTarifas;
    }
}
