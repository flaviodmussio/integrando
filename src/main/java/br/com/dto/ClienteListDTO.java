package br.com.dto;

import br.com.models.Cliente;

public class ClienteListDTO extends ClienteResponseDTO {

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