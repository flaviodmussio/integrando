package com.example.integrando.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

@Entity
public class PacoteTarifas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private BigDecimal valorMinimo;
    private BigDecimal valorMaximo;

    @ManyToOne
    private Cliente cliente;


    public PacoteTarifas() {
    }

    public PacoteTarifas(String nome, BigDecimal valorMinimo, BigDecimal valorMaximo, Cliente cliente) {
        this.nome = nome;
        this.valorMinimo = valorMinimo;
        this.valorMaximo = valorMaximo;
        this.cliente = cliente;
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

    @Override
    public String toString() {
        return "PacoteTarifas {" + "id=" + id + ", nome='" + nome + '\'' + ", valorMinimo=" + valorMinimo + ", valorMaximo=" + valorMaximo + '}';
    }
}
