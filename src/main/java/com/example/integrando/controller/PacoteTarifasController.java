package com.example.integrando.controller;

import com.example.integrando.models.PacoteTarifas;
import com.example.integrando.service.PacoteTarifasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pacotetarifas")
public class PacoteTarifasController {

    @Autowired
    private PacoteTarifasService pacoteTarifasService;


    @GetMapping
    public List<PacoteTarifas> listarTodos() {
        return pacoteTarifasService.listar();
    }

}
