package com.example.integrando.service;

import com.example.integrando.dto.ClienteRequestDTO;
import com.example.integrando.exception.ClienteException;
import com.example.integrando.exception.CpfValidationException;
import com.example.integrando.exception.PacoteTarifasException;
import com.example.integrando.models.Cliente;
import com.example.integrando.models.PacoteTarifas;
import com.example.integrando.repository.ClienteRepository;
import com.example.integrando.repository.PacoteTarifasRepository;
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

    @Autowired
    private PacoteTarifasRepository pacoteTarifasRepository;

    public List<Cliente> listar(String nome, Long id, String cpf) throws ClienteException {
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

        if (todosClientes.isEmpty()) {
            throw new ClienteException("Nao foi possivel encontrar clientes com o filtro desejado");
        }

        return todosClientes;
    }

    public Optional<Cliente> cadastrar(ClienteRequestDTO cliente) {
        Cliente clienteParaSalvar = cliente.toCliente();

        if (clienteRepository.findByCpf(clienteParaSalvar.getCpf()).isEmpty()) {
            PacoteTarifas pacoteTarifas = pegarPacoteTarifas(cliente.getPacoteTarifasId());

            clienteParaSalvar.setPacoteTarifas(pacoteTarifas);

            return this.salvar(clienteParaSalvar);
        } else {
            throw new CpfValidationException("CPF já cadastrado");
        }
    }

    public Optional<Cliente> encontrar(Long id) {
        return clienteRepository.findById(id);
    }

    public Optional<Cliente> atualizar(Long id, ClienteRequestDTO clienteRequest) throws CpfValidationException, ClienteException {
        Optional<Cliente> clienteParaAtualizar = clienteRepository.findById(id);

        if (clienteParaAtualizar.isPresent()) {
            Cliente clienteAntigo = clienteParaAtualizar.get();
            Cliente clienteAtualizado = clienteRequest.toCliente();

            if (clienteAtualizado.getCpf().equals(clienteAntigo.getCpf())){
                clienteAtualizado.setId(clienteAntigo.getId());
                clienteAtualizado.setPacoteTarifas(pegarPacoteTarifas(clienteRequest.getPacoteTarifasId()));

                return this.salvar(clienteAtualizado);
            } else {
                throw new CpfValidationException("CPF nao pode ser diferente da previamente cadastrada");
            }
        } else {
            throw new ClienteException("Cliente para atualizar nao encontrado!");
        }
    }

    public void remover(Long id) throws ClienteException {
        if (clienteRepository.findById(id).isPresent()) {
            clienteRepository.deleteById(id);
        } else {
            throw new ClienteException("Nao foi possivel encontrar cliente com id " + id);
        }
    }

    private Optional<Cliente> salvar(Cliente cliente) {
        return Optional.of(clienteRepository.save(cliente));
    }

    private PacoteTarifas pegarPacoteTarifas(String pacoteTarifaId) {
        Optional<PacoteTarifas> pacoteTarifas = pacoteTarifasRepository.findById(Long.parseLong(pacoteTarifaId));

        if (pacoteTarifas.isPresent()) {
            return pacoteTarifas.get();
        } else {
            throw new PacoteTarifasException("Nao foi possivel encontrar pacote de tarifas com id " + pacoteTarifaId);
        }
    }

}
