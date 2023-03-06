package br.com.dto;

import br.com.models.PacoteTarifas;

import java.math.BigDecimal;

public class PacoteTarifasDTO {

    private Long id;
    private String nome;
    private BigDecimal valorMinimo;
    private BigDecimal valorMaximo;

    public PacoteTarifasDTO(PacoteTarifas pacoteTarifas) {
        this.id = pacoteTarifas.getId();
        this.nome = pacoteTarifas.getNome();
        this.valorMinimo = pacoteTarifas.getValorMinimo();
        this.valorMaximo = pacoteTarifas.getValorMaximo();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getValorMinimo() {
        return valorMinimo;
    }

    public void setValorMinimo(BigDecimal valorMinimo) {
        this.valorMinimo = valorMinimo;
    }

    public BigDecimal getValorMaximo() {
        return valorMaximo;
    }

    public void setValorMaximo(BigDecimal valorMaximo) {
        this.valorMaximo = valorMaximo;
    }
}