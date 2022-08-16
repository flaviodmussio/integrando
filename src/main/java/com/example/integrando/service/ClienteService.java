package com.example.integrando.service;

import com.example.integrando.dto.ClienteRequestDTO;
import com.example.integrando.exception.CpfValidationException;
import com.example.integrando.models.Cliente;
import com.example.integrando.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
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

    public Optional<Cliente> salvar(ClienteRequestDTO cliente) {
        Optional<Cliente> resultado = Optional.empty();
        Cliente clienteParaSalvar = cliente.toCliente();

        if (clienteRepository.findByCpf(clienteParaSalvar.getCpf()).isEmpty()) {
            resultado = Optional.of(clienteParaSalvar);

            clienteRepository.save(clienteParaSalvar);
        } else {
            throw new CpfValidationException("CPF já cadastrado");
        }

        return resultado;
    }

    public Optional<Cliente> encontrarUm(Long id) {
        return clienteRepository.findById(id);
    }

    public void removerUm(Long id) {
        clienteRepository.deleteById(id);
    }

}
