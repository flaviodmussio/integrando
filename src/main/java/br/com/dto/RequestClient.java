package br.com.dto;

import br.com.models.Cliente;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class RequestClient {

    private static DateTimeFormatter ofPattern = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @NotNull
    @NotEmpty
    private String nome;

    @NotNull
    @NotEmpty
    @CPF(message = "cpf invalido")
    private String cpf;

    @NotNull
    @NotEmpty
    @Pattern(regexp = "^\\d{2}/\\d{2}/\\d{4}$", message = "Inserir no formato dd/MM/aaaa")
    private String dataNascimento;


    public RequestClient(String nome, String cpf, String dataNascimento) {
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
    }


    public static DateTimeFormatter getOfPattern() {
        return ofPattern;
    }

    public static void setOfPattern(DateTimeFormatter ofPattern) {
        RequestClient.ofPattern = ofPattern;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Cliente toCliente() {
        return new Cliente(this.nome, this.cpf, LocalDate.parse(this.dataNascimento, ofPattern));
    }
}