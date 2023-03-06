package br.com.builder;

import br.com.models.Cliente;
import br.com.models.PacoteTarifas;

import java.math.BigDecimal;
import java.util.List;

public class PacoteTarifasBuilder {

    private Long id;

    private String nome;

    private BigDecimal valorMinimo;

    private BigDecimal valorMaximo;

    private List<Cliente> clientes;

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

    public PacoteTarifasBuilder comClientes(List<Cliente> clientes) {
        this.clientes = clientes;

        return this;
    }

    public PacoteTarifas criar() {
        return new PacoteTarifas(id, nome, valorMinimo, valorMaximo, clientes);
    }

}