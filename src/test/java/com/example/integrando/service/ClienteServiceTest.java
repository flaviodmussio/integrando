package com.example.integrando.service;

import com.example.integrando.dto.ClienteRequestDTO;
import com.example.integrando.dto.PacoteTarifasRequestDTO;
import com.example.integrando.exception.CpfValidationException;
import com.example.integrando.models.Cliente;
import com.example.integrando.models.PacoteTarifas;
import com.example.integrando.repository.ClienteRepository;
import com.example.integrando.repository.PacoteTarifasRepository;
import com.example.integrando.util.builder.ClienteBuilder;
import com.example.integrando.util.builder.ClienteRequestDTOBuilder;
import com.example.integrando.util.builder.PacoteTarifasBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class ClienteServiceTest {

    private ClienteService clienteService;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private PacoteTarifasRepository pacoteTarifasRepository;

    private ClienteRequestDTO clienteRequest;

    private static final DateTimeFormatter ofPattern = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @BeforeEach
    public void beforeEach() {
        MockitoAnnotations.openMocks(this);

        this.clienteService = new ClienteService(clienteRepository, pacoteTarifasRepository);
        this.clienteRequest = getClienteRequest();
    }

    @Test
    public void deveriaRetornarClienteCadastrado(){
        PacoteTarifas pacoteTarifas = getPacoteTarifas();

        Mockito.when(pacoteTarifasRepository.findById(Mockito.any(Long.class)))
                .thenReturn(Optional.of(pacoteTarifas));

        Cliente clienteCriado = clienteRequest.toCliente();

        Mockito.when(clienteRepository.save(Mockito.any(Cliente.class)))
                .thenReturn(getCliente());

        Optional<Cliente> resultado = clienteService.cadastrar(clienteRequest);

        Assertions.assertEquals(clienteCriado.getNome(), resultado.get().getNome());
        Assertions.assertEquals(clienteCriado.getCpf(), resultado.get().getCpf());
        Assertions.assertEquals(clienteCriado.getDataNascimento(), resultado.get().getDataNascimento());
        Assertions.assertEquals(clienteCriado.getPacoteTarifas(), resultado.get().getPacoteTarifas());
    }

    @Test
    public void deveriaRetornarErroAoTentarCadastrarClienteComMesmoCPF() {
        Mockito.when(clienteRepository.findByCpf(Mockito.any(String.class)))
                .thenReturn(Optional.of(clienteRequest.toCliente()));

        CpfValidationException exception = Assertions.assertThrows(
                CpfValidationException.class,
                () -> clienteService.cadastrar(clienteRequest)
        );
        Assertions.assertEquals("CPF ja cadastrado", exception.getMessage());
        Mockito.verify(clienteRepository, Mockito.never()).save(Mockito.any(Cliente.class));
    }


    private ClienteRequestDTO getClienteRequest() {
        return new ClienteRequestDTOBuilder()
                .comNome("Cliente Mockado")
                .comCpf("123.456.789-10")
                .comDataNascimento("01/01/2001")
                .comPacoteTarifasId("1")
                .criar();
    }

    private Cliente getCliente() {
        return new ClienteBuilder()
                .comNome("Cliente Mockado")
                .comCpf("123.456.789-10")
                .comDataNascimento(LocalDate.parse("01/01/2001", ofPattern))
                .comPacoteTarifas(getPacoteTarifas())
                .criar();
    }

    private PacoteTarifas getPacoteTarifas() {
        return new PacoteTarifasBuilder()
                .comId(Long.parseLong("1"))
                .comNome("Pacote Tarifas Mockado")
                .comValorMinimo(new BigDecimal("1.0"))
                .comValorMaximo(new BigDecimal("100.0"))
                .criar();
    }


}
