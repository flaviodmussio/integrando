package com.example.integrando.dto;

import com.example.integrando.models.Cliente;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PacoteTarifasClienteRequestDTO {

    private static DateTimeFormatter ofPattern = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @NotNull
    @NotEmpty
    private String nome;

    @NotNull
    @NotEmpty
    @CPF(message = "cpf invalido")
    @Pattern(regexp = "[0-9]{3}\\.[0-9]{3}\\.[0-9]{3}-[0-9]{2}", message = "cpf deve estar no formato XXX.XXX.XXX-XX")
    private String cpf;

    public PacoteTarifasClienteRequestDTO(String nome, String cpf) {
        this.nome = nome;
        this.cpf = cpf;
    }

    public static DateTimeFormatter getOfPattern() {
        return ofPattern;
    }

    public static void setOfPattern(DateTimeFormatter ofPattern) {
        PacoteTarifasClienteRequestDTO.ofPattern = ofPattern;
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

}