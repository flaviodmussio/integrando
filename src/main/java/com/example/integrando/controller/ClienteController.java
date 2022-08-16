package com.example.integrando.controller;

import com.example.integrando.dto.ClienteRequestDTO;
import com.example.integrando.dto.ClienteResponseDTO;
import com.example.integrando.dto.ResponseDTO;
import com.example.integrando.models.Cliente;
import com.example.integrando.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
    public List<Cliente> listarTodos(String nome, Long id, String cpf) {
        return clienteService.listar(nome, id, cpf);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<ResponseDTO<ClienteResponseDTO>> cadastrar(@RequestBody @Valid ClienteRequestDTO clientRequest, UriComponentsBuilder uriComponentsBuilder) {
        Optional<Cliente> cliente = clienteService.salvar(clientRequest);

        return cliente
                .map(clienteSalvo -> {
                    URI uri = uriComponentsBuilder.path("/cliente/{id}").buildAndExpand(clienteSalvo.getId()).toUri();

                    return ResponseEntity.created(uri).body(new ResponseDTO<ClienteResponseDTO>("cadastrado", new ClienteResponseDTO(clienteSalvo)));
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> detalhar(@PathVariable Long id) {
        Optional<Cliente> cliente = clienteService.encontrarUm(id);

        return cliente
                .map(clienteEncontrado -> ResponseEntity.ok().body(new ClienteResponseDTO(clienteEncontrado)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity remover(@PathVariable Long id) {

        clienteService.removerUm(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}