package com.example.integrando.service;

import com.example.integrando.dto.ClienteRequestDTO;
import com.example.integrando.exception.ClienteException;
import com.example.integrando.exception.CpfValidationException;
import com.example.integrando.exception.PacoteTarifasException;
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

    private PacoteTarifas pacoteTarifas;

    private static final DateTimeFormatter ofPattern = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @BeforeEach
    public void beforeEach() {
        MockitoAnnotations.openMocks(this);

        this.clienteService = new ClienteService(clienteRepository, pacoteTarifasRepository);
        this.clienteRequest = getClienteRequest("Cliente Mockado");
        this.pacoteTarifas = getPacoteTarifas();

    }

    @Test
    public void deveriaRetornarClienteCadastrado(){
        Mockito.when(pacoteTarifasRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(pacoteTarifas));

        Cliente clienteCriado = clienteRequest.toCliente();
        clienteCriado.setPacoteTarifas(pacoteTarifas);

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
        Mockito.when(clienteRepository.findByCpf(Mockito.anyString()))
                .thenReturn(Optional.of(clienteRequest.toCliente()));

        CpfValidationException exception = Assertions.assertThrows(
                CpfValidationException.class,
                () -> clienteService.cadastrar(clienteRequest)
        );
        Assertions.assertEquals("CPF ja cadastrado", exception.getMessage());
        Mockito.verify(clienteRepository, Mockito.never()).save(Mockito.any(Cliente.class));
    }

    @Test
    public void deveriaRetornarClienteAtualizadoAoTentarAtualizarClienteExistente() {
        Mockito.when(clienteRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(getCliente()));

        Mockito.when(pacoteTarifasRepository.findById(Mockito.any(Long.class)))
                .thenReturn(Optional.of(pacoteTarifas));

        Long clienteId = Long.parseLong("1");
        ClienteRequestDTO clienteAtualizacaoRequest = getClienteAtualizacaoRequest();
        Cliente clienteCriado = clienteAtualizacaoRequest.toCliente();
        clienteCriado.setPacoteTarifas(pacoteTarifas);

        Mockito.when(clienteRepository.save(Mockito.any(Cliente.class)))
                .thenReturn(getClienteComId());

        Optional<Cliente> resultado = clienteService.atualizar(clienteId, clienteAtualizacaoRequest);

        Assertions.assertEquals(clienteCriado.getNome(), resultado.get().getNome());
        Assertions.assertEquals(clienteCriado.getCpf(), resultado.get().getCpf());
        Assertions.assertEquals(clienteCriado.getDataNascimento(), resultado.get().getDataNascimento());
        Assertions.assertEquals(clienteCriado.getPacoteTarifas(), resultado.get().getPacoteTarifas());
    }



    @Test
    public void deveriaRetornarErroAoTentarAtualizarClienteInexistente() {
        Mockito.when(clienteRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(getCliente()));

        Long clienteId = Long.parseLong("12");
        ClienteRequestDTO clienteRequest = getClienteAtualizacaoRequest();

        Mockito.when(clienteRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.empty());

        ClienteException exception = Assertions.assertThrows(
                ClienteException.class,
                () -> clienteService.atualizar(clienteId, clienteRequest)
        );
        Assertions.assertEquals("Cliente para atualizar nao encontrado!", exception.getMessage());
        Mockito.verify(clienteRepository, Mockito.never()).save(Mockito.any(Cliente.class));
    }

    @Test
    public void deveriaRetornarErroAoTentarMudarCPFdeClienteCadastrado() {
        Mockito.when(clienteRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(getCliente()));

        Long clienteId = Long.parseLong("1");
        ClienteRequestDTO clienteAtualizacaoRequest = new ClienteRequestDTOBuilder()
                .comNome("Cliente Mockado")
                .comCpf("109.876.543-21")
                .comDataNascimento("01/01/2001")
                .comPacoteTarifasId("1")
                .criar();

        CpfValidationException exception = Assertions.assertThrows(
                CpfValidationException.class,
                () -> clienteService.atualizar(clienteId, clienteAtualizacaoRequest)
        );
        Assertions.assertEquals("CPF nao pode ser diferente da previamente cadastrada", exception.getMessage());
        Mockito.verify(clienteRepository, Mockito.never()).save(Mockito.any(Cliente.class));
    }

    @Test
    public void deveriaRetornarErroAoTentarAtualizarPacoteTarifasClientePorPacoteTarifasInexistente() {
        Mockito.when(clienteRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(getCliente()));

        Mockito.when(pacoteTarifasRepository.findById(Mockito.any(Long.class)))
                .thenReturn(Optional.empty());

        Long clienteId = Long.parseLong("1");
        ClienteRequestDTO clienteAtualizacaoRequest = getClienteAtualizacaoRequest();

        PacoteTarifasException exception = Assertions.assertThrows(
                PacoteTarifasException.class,
                () -> clienteService.atualizar(clienteId, clienteAtualizacaoRequest)
        );
        Assertions.assertEquals("Nao foi possivel encontrar pacote de tarifas com id 1", exception.getMessage());
        Mockito.verify(clienteRepository, Mockito.never()).save(Mockito.any(Cliente.class));
    }

    @Test
    public void deveriaRetornarErroAoTentarDeletarClienteInexistente() {
        Mockito.when(clienteRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.empty());

        Long clienteId = Long.parseLong("12");

        ClienteException exception = Assertions.assertThrows(
                ClienteException.class,
                () -> clienteService.remover(clienteId)
        );

        Assertions.assertEquals("Nao foi possivel encontrar cliente com id " + clienteId, exception.getMessage());
        Mockito.verify(clienteRepository, Mockito.never()).deleteById(Mockito.anyLong());
    }


    private ClienteRequestDTO getClienteAtualizacaoRequest() {
        return getClienteRequest("Cliente Mockado Atualizado");
    }

     private static ClienteRequestDTO getClienteRequest(String clienteNome) {
        return new ClienteRequestDTOBuilder()
                .comNome(clienteNome)
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
                .comPacoteTarifas(pacoteTarifas)
                .criar();
    }

    private Cliente getClienteComId() {
        return new ClienteBuilder()
                .comId(Long.parseLong("1"))
                .comNome("Cliente Mockado Atualizado")
                .comCpf("123.456.789-10")
                .comDataNascimento(LocalDate.parse("01/01/2001", ofPattern))
                .comPacoteTarifas(pacoteTarifas)
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
