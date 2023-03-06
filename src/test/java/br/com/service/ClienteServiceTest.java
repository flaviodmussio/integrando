package br.com.service;


import br.com.builder.ClienteBuilder;
import br.com.builder.ClienteRequestDTOBuilder;
import br.com.builder.PacoteTarifasBuilder;
import br.com.dto.ClienteRequestDTO;
import br.com.exceptions.ClienteException;
import br.com.exceptions.CpfValidationException;
import br.com.exceptions.PacoteTarifasException;
import br.com.models.Cliente;
import br.com.models.PacoteTarifas;
import br.com.repository.ClienteRepository;
import br.com.repository.PacoteTarifasRepository;
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

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    public void retornariaClienteCadastrado(){
        Mockito.when(pacoteTarifasRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(pacoteTarifas));

        Cliente clienteCriado = clienteRequest.toCliente();
        clienteCriado.setPacoteTarifas(pacoteTarifas);

        Mockito.when(clienteRepository.save(Mockito.any(Cliente.class)))
                .thenReturn(getCliente());

        Optional<Cliente> resultado = clienteService.cadastrar(clienteRequest);

        assertEquals(clienteCriado.getNome(), resultado.get().getNome());
        assertEquals(clienteCriado.getCpf(), resultado.get().getCpf());
        assertEquals(clienteCriado.getDataNascimento(), resultado.get().getDataNascimento());
        assertEquals(clienteCriado.getPacoteTarifas(), resultado.get().getPacoteTarifas());
    }

    @Test
    public void retornariaErroAoCadastrarClienteComMesmoCPF() {
        Mockito.when(clienteRepository.findByCpf(Mockito.anyString()))
                .thenReturn(Optional.of(clienteRequest.toCliente()));

        CpfValidationException exception = Assertions.assertThrows(
                CpfValidationException.class,
                () -> clienteService.cadastrar(clienteRequest)
        );
        assertEquals("CPF já cadastrado", exception.getMessage());
        Mockito.verify(clienteRepository, Mockito.never()).save(Mockito.any(Cliente.class));
    }

    @Test
    public void retornariaClienteAtualizadoAoAtualizarClienteExistente() {
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

        assertEquals(clienteCriado.getNome(), resultado.get().getNome());
        assertEquals(clienteCriado.getCpf(), resultado.get().getCpf());
        assertEquals(clienteCriado.getDataNascimento(), resultado.get().getDataNascimento());
        assertEquals(clienteCriado.getPacoteTarifas(), resultado.get().getPacoteTarifas());
    }



    @Test
    public void retornariaErroAoTentarAtualizarClienteInexistente() {
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
        assertEquals("Cliente para atualizar nao encontrado!", exception.getMessage());
        Mockito.verify(clienteRepository, Mockito.never()).save(Mockito.any(Cliente.class));
    }

    @Test
    public void retornariaErroAoTentarMudarCPFdeClienteCadastrado() {
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
        assertEquals("CPF nao pode ser diferente da previamente cadastrada", exception.getMessage());
        Mockito.verify(clienteRepository, Mockito.never()).save(Mockito.any(Cliente.class));
    }

    @Test
    public void retornariaErroAoTentarAtualizarPacoteTarifasClientePorPacoteTarifasInexistente() {
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
        assertEquals("Nao foi possivel encontrar pacote de tarifas com id 1", exception.getMessage());
        Mockito.verify(clienteRepository, Mockito.never()).save(Mockito.any(Cliente.class));
    }

    @Test
    public void retornariaErroAoTentarDeletarClienteInexistente() {
        Mockito.when(clienteRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.empty());

        Long clienteId = Long.parseLong("12");

        ClienteException exception = Assertions.assertThrows(
                ClienteException.class,
                () -> clienteService.remover(clienteId)
        );

        assertEquals("Nao foi possivel encontrar cliente com id " + clienteId, exception.getMessage());
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