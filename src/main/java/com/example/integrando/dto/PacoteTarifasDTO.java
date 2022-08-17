package com.example.integrando.dto;

import java.math.BigDecimal;

public class PacoteTarifasDTO {

    private Long id;
    private String nome;
    private BigDecimal valorMinimo;
    private BigDecimal valorMaximo;
    private ClienteDTO clienteDTO;

    public PacoteTarifasDTO(Long id, String nome, BigDecimal valorMinimo, BigDecimal valorMaximo, ClienteDTO clienteDTO) {
        this.id = id;
        this.nome = nome;
        this.valorMinimo = valorMinimo;
        this.valorMaximo = valorMaximo;
        this.clienteDTO = clienteDTO;
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

    public ClienteDTO getClienteDTO() {
        return clienteDTO;
    }

    public void setClienteDTO(ClienteDTO clienteDTO) {
        this.clienteDTO = clienteDTO;
    }
}
