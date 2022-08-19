package com.example.integrando.util.builder;

import com.example.integrando.models.PacoteTarifas;

import java.math.BigDecimal;

public class PacoteTarifasBuilder {

    private Long id;

    private String nome;

    private BigDecimal valorMinimo;

    private BigDecimal valorMaximo;

    public PacoteTarifasBuilder comId(Long id) {
        this.id = id;

        return this;
    }

    public PacoteTarifasBuilder comNome(String nome) {
        this.nome = nome;

        return this;
    }

    public PacoteTarifasBuilder comValorMinimo(BigDecimal valorMinimo) {
        this.valorMinimo = valorMinimo;

        return this;
    }

    public PacoteTarifasBuilder comValorMaximo(BigDecimal valorMaximo) {
        this.valorMaximo = valorMaximo;

        return this;
    }

    public PacoteTarifas criar() {
        return new PacoteTarifas(nome, valorMinimo, valorMaximo);
    }

}
