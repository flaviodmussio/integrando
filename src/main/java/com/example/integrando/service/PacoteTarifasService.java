package com.example.integrando.service;

import com.example.integrando.models.PacoteTarifas;
import com.example.integrando.repository.PacoteTarifasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PacoteTarifasService {

    @Autowired
    private PacoteTarifasRepository pacoteTarifasRepository;


    public List<PacoteTarifas> listar() {
       return pacoteTarifasRepository.findAll();
    }
}
