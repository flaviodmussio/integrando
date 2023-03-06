package br.com.builder;

import br.com.models.Cliente;
import br.com.models.PacoteTarifas;

import java.time.LocalDate;

public class ClienteBuilder {

    private Long id;

    private String nome;

    private String cpf;

    private LocalDate dataNascimento;

    private PacoteTarifas pacoteTarifas;

    public ClienteBuilder comId(Long id) {
        this.id = id;

        return this;
    }

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
        return new Cliente(id, nome, cpf, dataNascimento, pacoteTarifas);
    }

}
