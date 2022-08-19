package com.example.integrando.service;

import com.example.integrando.dto.PacoteTarifasRequestDTO;
import com.example.integrando.models.PacoteTarifas;
import com.example.integrando.repository.ClienteRepository;
import com.example.integrando.repository.PacoteTarifasRepository;
import com.example.integrando.util.builder.PacoteTarifasBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

public class PacoteTarifasServiceTest {

    private PacoteTarifasService pacoteTarifasService;

    @Mock
    private PacoteTarifasRepository pacoteTarifasRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @BeforeEach
    public void beforeEach() {
        MockitoAnnotations.openMocks(this);

        this.pacoteTarifasService = new PacoteTarifasService(pacoteTarifasRepository, clienteRepository);
    }

    @Test
    public void deveriaRetornarPacoteTarifasCriado() {
        PacoteTarifasRequestDTO pacoteTarifasRequestDTO = new PacoteTarifasRequestDTO("PacoteTarifas Mockado I", "11.0", "150.0");
        PacoteTarifas pacoteTarifas = new PacoteTarifasBuilder()
                .comNome("PacoteTarifas Mockado I")
                .comValorMinimo(new BigDecimal("11.0"))
                .comValorMinimo(new BigDecimal("150.0"))
                .criar();

        Mockito.when(pacoteTarifasRepository.save(Mockito.any(PacoteTarifas.class)))
                .thenReturn(pacoteTarifas);

        Optional<PacoteTarifas> resultadoCriado = Optional.of(pacoteTarifas);
        Optional<PacoteTarifas> resultado = pacoteTarifasService.cadastrar(pacoteTarifasRequestDTO);

        Assertions.assertEquals(resultadoCriado.get(), resultado.get());

    }


}
