package br.com.builder;

import br.com.dto.ClienteRequestDTO;

public class ClienteRequestDTOBuilder {

    private String nome;

    private String cpf;

    private String dataNascimento;

    private String pacoteTarifasId;

    public ClienteRequestDTOBuilder comNome(String nome) {
        this.nome = nome;

        return this;
    }

    public ClienteRequestDTOBuilder comCpf(String cpf) {
        this.cpf = cpf;

        return this;
    }

    public ClienteRequestDTOBuilder comDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;

        return this;
    }

    public ClienteRequestDTOBuilder comPacoteTarifasId(String pacoteTarifasId) {
        this.pacoteTarifasId = pacoteTarifasId;

        return this;
    }

    public ClienteRequestDTO criar() {
        return new ClienteRequestDTO(nome, cpf, dataNascimento, pacoteTarifasId);
    }

}