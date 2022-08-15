package com.example.integrando.controller;

import com.example.integrando.dto.ClienteRequestDTO;
import com.example.integrando.dto.ClienteResponseDTO;
import com.example.integrando.models.Cliente;
import com.example.integrando.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public List<Cliente> listarTodos() {
        return clienteService.listar();
    }

    @PostMapping
    @Transactional
    public ResponseEntity<ClienteResponseDTO> cadastrar(@RequestBody @Valid ClienteRequestDTO clientRequest, UriComponentsBuilder uriComponentsBuilder) {
        Cliente cliente = clienteService.salvar(clientRequest);

        URI uri = uriComponentsBuilder.path("/cliente/{id}").buildAndExpand(cliente.getId()).toUri();

        return ResponseEntity.created(uri).body(new ClienteResponseDTO(cliente));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> detalhar(@PathVariable Long id) {
        Optional<Cliente> cliente = clienteService.encontrarUm(id);

        return cliente
                .map(clienteEncontrado -> ResponseEntity.ok().body(new ClienteResponseDTO(clienteEncontrado)))
                .orElseGet(() -> ResponseEntity.notFound().build());

    }


}

