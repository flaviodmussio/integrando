package br.com.dto;

import br.com.models.PacoteTarifas;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

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


    public PacoteTarifasRequestDTO(String nome, String valorMinimo, String valorMaximo) {
        this.nome = nome;
        this.valorMinimo = valorMinimo;
        this.valorMaximo = valorMaximo;
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

    public PacoteTarifas toPacoteTarifas() {
        return new PacoteTarifas(this.nome, new BigDecimal(valorMinimo), new BigDecimal(valorMaximo));
    }

}