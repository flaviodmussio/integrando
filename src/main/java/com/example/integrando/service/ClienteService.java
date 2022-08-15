package com.example.integrando.service;

import com.example.integrando.dto.ClienteRequestDTO;
import com.example.integrando.models.Cliente;
import com.example.integrando.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> listar() {
        return clienteRepository.findAll();
    }

    public Cliente salvar(ClienteRequestDTO cliente) {
        return clienteRepository.save(cliente.toCliente());
    }

    public Optional<Cliente> encontrarUm(Long id) {
        return clienteRepository.findById(id);
    }

}
