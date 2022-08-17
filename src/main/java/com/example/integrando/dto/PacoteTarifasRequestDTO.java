package com.example.integrando.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class PacoteTarifasRequestDTO {

    @NotNull
    @NotEmpty
    private String nome;

    @NotNull
    @NotEmpty
    private String valorMinimo;

    @NotNull
    @NotEmpty
    private String valorMaximo;

    @NotNull
    @NotEmpty
    private PacoteTarifasClienteRequestDTO cliente;

    public PacoteTarifasRequestDTO(String nome, String valorMinimo, String valorMaximo, PacoteTarifasClienteRequestDTO cliente) {
        this.nome = nome;
        this.valorMinimo = valorMinimo;
        this.valorMaximo = valorMaximo;
        this.cliente = cliente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getValorMinimo() {
        return valorMinimo;
    }

    public void setValorMinimo(String valorMinimo) {
        this.valorMinimo = valorMinimo;
    }

    public String getValorMaximo() {
        return valorMaximo;
    }

    public void setValorMaximo(String valorMaximo) {
        this.valorMaximo = valorMaximo;
    }

    public PacoteTarifasClienteRequestDTO getCliente() {
        return cliente;
    }

    public void setCliente(PacoteTarifasClienteRequestDTO cliente) {
        this.cliente = cliente;
    }
}
