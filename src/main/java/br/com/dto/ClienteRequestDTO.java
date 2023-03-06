package br.com.dto;

import br.com.models.Cliente;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ClienteRequestDTO {

    private static DateTimeFormatter ofPattern = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @NotNull
    @NotEmpty
    private String nome;

    @NotNull
    @NotEmpty
    @CPF(message = "cpf invalido")
    @Pattern(regexp = "[0-9]{3}\\.[0-9]{3}\\.[0-9]{3}-[0-9]{2}", message = "cpf deve estar no formato XXX.XXX.XXX-XX")
    private String cpf;

    @NotNull
    @NotEmpty
    @Pattern(regexp = "^\\d{2}/\\d{2}/\\d{4}$", message = "data deve estar no formato dd/MM/aaaa")
    private String dataNascimento;

    @NotNull
    @NotEmpty
    private String pacoteTarifasId;


    public ClienteRequestDTO(String nome, String cpf, String dataNascimento, String pacoteTarifasId) {
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.pacoteTarifasId = pacoteTarifasId;
    }

    public static DateTimeFormatter getOfPattern() {
        return ofPattern;
    }

    public static void setOfPattern(DateTimeFormatter ofPattern) {
        ClienteRequestDTO.ofPattern = ofPattern;
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

    public String getPacoteTarifasId() {
        return pacoteTarifasId;
    }

    public void setPacoteTarifasId(String pacoteTarifasId) {
        this.pacoteTarifasId = pacoteTarifasId;
    }

    public Cliente toCliente() {
        return new Cliente(this.nome, this.cpf, LocalDate.parse(this.dataNascimento, ofPattern));
    }
}