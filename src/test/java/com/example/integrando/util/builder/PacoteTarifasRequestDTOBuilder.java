package com.example.integrando.util.builder;

import com.example.integrando.dto.PacoteTarifasRequestDTO;

public class PacoteTarifasRequestDTOBuilder {

    private String nome;

    private String valorMinimo;

    private String valorMaximo;

    public PacoteTarifasRequestDTOBuilder comNome(String nome) {
        this.nome = nome;

        return this;
    }

    public PacoteTarifasRequestDTOBuilder comValorMinimo(String valorMinimo) {
        this.valorMinimo = valorMinimo;

        return this;
    }

    public PacoteTarifasRequestDTOBuilder comValorMaximo(String valorMaximo) {
        this.valorMaximo = valorMaximo;

        return this;
    }

    public PacoteTarifasRequestDTO criar() {
        return new PacoteTarifasRequestDTO(nome, valorMinimo, valorMaximo);
    }
}
