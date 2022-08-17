package com.example.integrando.controller;

import com.example.integrando.dto.PacoteTarifasDTO;
import com.example.integrando.dto.PacoteTarifasListDTO;
import com.example.integrando.models.PacoteTarifas;
import com.example.integrando.service.PacoteTarifasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
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

}
