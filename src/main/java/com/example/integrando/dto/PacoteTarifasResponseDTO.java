package com.example.integrando.dto;

public class PacoteTarifasResponseDTO {

    private String status;

    private PacoteTarifasDTO pacoteTarifas;

    public PacoteTarifasResponseDTO(String status, PacoteTarifasDTO pacoteTarifas) {
        this.status = status;
        this.pacoteTarifas = pacoteTarifas;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public PacoteTarifasDTO getPacoteTarifas() {
        return pacoteTarifas;
    }

    public void setPacoteTarifas(PacoteTarifasDTO pacoteTarifas) {
        this.pacoteTarifas = pacoteTarifas;
    }
}
