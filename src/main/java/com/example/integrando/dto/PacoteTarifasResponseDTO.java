package com.example.integrando.dto;

public class PacoteTarifasResponseDTO extends ResponseDTO{

    private PacoteTarifasDTO pacoteTarifas;

    public PacoteTarifasResponseDTO(String status, PacoteTarifasDTO pacoteTarifas) {
        super(status);
        this.pacoteTarifas = pacoteTarifas;
    }

    public PacoteTarifasDTO getPacoteTarifas() {
        return pacoteTarifas;
    }

    public void setPacoteTarifas(PacoteTarifasDTO pacoteTarifas) {
        this.pacoteTarifas = pacoteTarifas;
    }
}
