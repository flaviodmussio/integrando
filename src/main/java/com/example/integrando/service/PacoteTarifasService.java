package com.example.integrando.service;

import com.example.integrando.dto.PacoteTarifasRequestDTO;
import com.example.integrando.exception.PacoteTarifasException;
import com.example.integrando.exception.PacoteTarifasRemoveException;
import com.example.integrando.models.Cliente;
import com.example.integrando.models.PacoteTarifas;
import com.example.integrando.repository.ClienteRepository;
import com.example.integrando.repository.PacoteTarifasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class PacoteTarifasService {

    @Autowired
    private PacoteTarifasRepository pacoteTarifasRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    public List<PacoteTarifas> listar(Long id, Long clienteId, String clienteNome) {
        List<PacoteTarifas> pacoteTarifas = null;

        if (id != null) {
            pacoteTarifas = pacoteTarifasRepository.findAllById(Collections.singleton(id));
        } else if (clienteId != null) {
            pacoteTarifas = pacoteTarifasRepository.findAllByClientesId(clienteId);
        } else if (clienteNome != null) {
            pacoteTarifas = pacoteTarifasRepository.findAllByClientesNome(clienteNome);
        } else {
            pacoteTarifas = pacoteTarifasRepository.findAll();
        }

        return pacoteTarifas;
    }

    public Optional<PacoteTarifas> cadastrar(PacoteTarifasRequestDTO pacoteTarifasRequest) {
        PacoteTarifas pacoteTarifas = pacoteTarifasRequest.toPacoteTarifas();

        return this.salvar(pacoteTarifas);
    }

    public Optional<PacoteTarifas> atualizar(Long id, PacoteTarifasRequestDTO pacoteTarifasRequest) throws PacoteTarifasException {
        Optional<PacoteTarifas> pacoteTarifasParaAtualizar = pacoteTarifasRepository.findById(id);

        if (pacoteTarifasParaAtualizar.isPresent()) {
            PacoteTarifas pacoteTarifasAntigo = pacoteTarifasParaAtualizar.get();
            PacoteTarifas pacoteTarifasNovo = pacoteTarifasRequest.toPacoteTarifas();

            pacoteTarifasNovo.setId(pacoteTarifasAntigo.getId());
            pacoteTarifasNovo.setClientes(pacoteTarifasAntigo.getClientes());

            return this.salvar(pacoteTarifasNovo);
        } else {
            throw new PacoteTarifasException("Pacote de Tarifas para atualizar nao encontrado");
        }

    }

    public void remover(Long id) throws PacoteTarifasRemoveException {
        if (pacoteTarifasRepository.findById(id).isPresent()) {
            if(pacoteTarifasRepository.findById(id).get().getClientes().isEmpty()) {
                pacoteTarifasRepository.deleteById(id);
            } else {
                throw new PacoteTarifasRemoveException("Nao foi possivel remover porque clientes possuem esse pacote de tarifas");
            }
        } else {
            throw new PacoteTarifasRemoveException("Nao foi possivel encontrar o pacote de tarifas com " + id);
        }
    }

    private Optional<PacoteTarifas> salvar(PacoteTarifas pacoteTarifas) {
        return Optional.of(pacoteTarifasRepository.save(pacoteTarifas));
    }

    private Cliente pegarCliente(Long id) {
        return clienteRepository.findById(id).get();
    }
}
