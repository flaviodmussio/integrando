package com.example.integrando.controller;

import com.example.integrando.dto.PacoteTarifasDTO;
import com.example.integrando.dto.PacoteTarifasListDTO;
import com.example.integrando.dto.PacoteTarifasRequestDTO;
import com.example.integrando.dto.PacoteTarifasResponseDTO;
import com.example.integrando.exception.PacoteTarifasRemoveException;
import com.example.integrando.models.PacoteTarifas;
import com.example.integrando.service.PacoteTarifasService;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pacotetarifas")
public class PacoteTarifasController {

    @Autowired
    private PacoteTarifasService pacoteTarifasService;


    @GetMapping
    public List<PacoteTarifasListDTO> listarTodos(Long id, Long clienteId, String clienteNome) {
        List<PacoteTarifas> pacotesTarifas = pacoteTarifasService.listar(id, clienteId, clienteNome);

        return pacotesTarifas.stream()
                .map(PacoteTarifasListDTO::new)
                .collect(Collectors.toList());
    }

    @PostMapping
    @Transactional
    public ResponseEntity<PacoteTarifasResponseDTO> cadastrar(@RequestBody @Valid PacoteTarifasRequestDTO pacoteTarifasRequest, UriComponentsBuilder uriComponentsBuilder) {
        Optional<PacoteTarifas> pacoteTarifas = pacoteTarifasService.cadastrar(pacoteTarifasRequest);

        return pacoteTarifas
                .map(pacoteTarifa -> {
                    URI uri = uriComponentsBuilder.path("/pacoteTarifas/{id}").buildAndExpand(pacoteTarifa.getId()).toUri();

                    return ResponseEntity.created(uri).body(new PacoteTarifasResponseDTO("cadastrado", new PacoteTarifasDTO(pacoteTarifa)));
                })
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Object> remover(@PathVariable Long id) {
        pacoteTarifasService.remover(id);

        return ResponseEntity.noContent().build();
    }

}