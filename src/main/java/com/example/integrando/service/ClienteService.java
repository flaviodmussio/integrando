package com.example.integrando.service;

import com.example.integrando.dto.ClienteRequestDTO;
import com.example.integrando.exception.CpfValidationException;
import com.example.integrando.models.Cliente;
import com.example.integrando.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> listar(String nome, Long id, String cpf) {
        List<Cliente> todosClientes = null;

        if (nome != null) {
            todosClientes = clienteRepository.findAllByNome(nome);
        } else if (id != null) {
            todosClientes = clienteRepository.findAllById(Collections.singleton(id));
        } else if (cpf != null) {
            todosClientes = clienteRepository.findAllByCpf(cpf);
        } else {
            todosClientes = clienteRepository.findAll();
        }

        return todosClientes;
    }

    public Optional<Cliente> cadastrar(ClienteRequestDTO cliente) {
        Cliente clienteParaSalvar = cliente.toCliente();

        if (clienteRepository.findByCpf(clienteParaSalvar.getCpf()).isEmpty()) {
            return this.salvar(clienteParaSalvar);
        } else {
            throw new CpfValidationException("CPF já cadastrado");
        }
    }

    public Optional<Cliente> encontrar(Long id) {
        return clienteRepository.findById(id);
    }

    public Optional<Cliente> atualizar(Long id, ClienteRequestDTO clienteRequest) {
        Optional<Cliente> resultado = Optional.empty();
        Optional<Cliente> clienteParaAtualizar = clienteRepository.findById(id);

        if (clienteParaAtualizar.isPresent()) {
            Cliente clienteAntigo = clienteParaAtualizar.get();
            Cliente clienteAtualizado = clienteRequest.toCliente();

            if (clienteAtualizado.getCpf().equals(clienteAntigo.getCpf())){
                clienteAtualizado.setId(clienteAntigo.getId());

                resultado = this.salvar(clienteAtualizado);
            } else {
                throw new CpfValidationException("CPF nao pode ser diferente da previamente cadastrada");
            }
        }

        return resultado;
    }

    public void removerUm(Long id) {
        clienteRepository.deleteById(id);
    }

    private Optional<Cliente> salvar(Cliente cliente) {
        return Optional.of(clienteRepository.save(cliente));
    }

}
