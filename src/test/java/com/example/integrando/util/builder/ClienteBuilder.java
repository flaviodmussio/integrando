package com.example.integrando.util.builder;

import com.example.integrando.models.Cliente;
import com.example.integrando.models.PacoteTarifas;

import java.time.LocalDate;

public class ClienteBuilder {

    private String nome;

    private String cpf;

    private LocalDate dataNascimento;

    private PacoteTarifas pacoteTarifas;


    public ClienteBuilder comNome(String nome) {
        this.nome = nome;

        return this;
    }

    public ClienteBuilder comCpf(String cpf) {
        this.cpf = cpf;

        return this;
    }

    public ClienteBuilder comDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;

        return this;
    }

    public ClienteBuilder comPacoteTarifas(PacoteTarifas pacoteTarifas) {
        this.pacoteTarifas = pacoteTarifas;

        return this;
    }

    public Cliente criar() {
        return new Cliente(nome, cpf, dataNascimento);
    }

}
