package br.com.service;

import br.com.builder.PacoteTarifasBuilder;
import br.com.builder.PacoteTarifasRequestDTOBuilder;
import br.com.dto.PacoteTarifasRequestDTO;
import br.com.exceptions.PacoteTarifasException;
import br.com.exceptions.PacoteTarifasRemoveException;
import br.com.models.Cliente;
import br.com.models.PacoteTarifas;
import br.com.repository.PacoteTarifasRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class PacoteTarifasServiceTest {

    private PacoteTarifasService pacoteTarifasService;

    @Mock
    private PacoteTarifasRepository pacoteTarifasRepository;

    private PacoteTarifas pacoteTarifas;

    private PacoteTarifasRequestDTO pacoteTarifasRequestDTO;

    @BeforeEach
    public void beforeEach() {
        MockitoAnnotations.openMocks(this);

        this.pacoteTarifasService = new PacoteTarifasService(pacoteTarifasRepository);
        this.pacoteTarifasRequestDTO = new PacoteTarifasRequestDTO("PacoteTarifas Mockado I", "11.0", "150.0");
        this.pacoteTarifas = getPacoteTarifas();

        Mockito.when(pacoteTarifasRepository.save(Mockito.any(PacoteTarifas.class)))
                .thenReturn(pacoteTarifas);
    }

    @Test
    public void retornariaPacoteTarifasCriado() {
        PacoteTarifas resultadoCriado = pacoteTarifas;

        Mockito.when(pacoteTarifasRepository.save(Mockito.any(PacoteTarifas.class)))
                .thenReturn(pacoteTarifas);

        Optional<PacoteTarifas> resultado = pacoteTarifasService.cadastrar(pacoteTarifasRequestDTO);

        Assertions.assertEquals(resultadoCriado.getNome(), resultado.get().getNome());
        Assertions.assertEquals(resultadoCriado.getValorMinimo(), resultado.get().getValorMinimo());
        Assertions.assertEquals(resultadoCriado.getValorMaximo(), resultado.get().getValorMaximo());
    }

    @Test
    public void retornariaClientePeloId() {
        Mockito.when(pacoteTarifasRepository.save(Mockito.any(PacoteTarifas.class)))
                .thenReturn(pacoteTarifas);

        Optional<PacoteTarifas> resultadoCadastro = pacoteTarifasService.cadastrar(pacoteTarifasRequestDTO);

        Mockito.when(pacoteTarifasRepository.findById(resultadoCadastro.get().getId()))
                .thenReturn(Optional.of(this.pacoteTarifas));

        Optional<PacoteTarifas> resultado = pacoteTarifasService.encontrar(resultadoCadastro.get().getId());

        Assertions.assertEquals(resultadoCadastro.get().getId(), resultado.get().getId());
    }

    @Test
    public void retornariaErroAoPassarClienteComIdInexistente() {
        Long pacoteTarifasId = Long.parseLong("12");

        Mockito.when(pacoteTarifasRepository.findById(Mockito.any(Long.class)))
                .thenReturn(Optional.empty());

        PacoteTarifasException exception = Assertions.assertThrows(
                PacoteTarifasException.class,
                () -> pacoteTarifasService.encontrar(pacoteTarifasId)
        );
        Assertions.assertEquals("Nao foi possivel encontrar o pacote de tarifas com id 12", exception.getMessage());
    }

    @Test
    public void retornariaClienteAtualizadoAoTentarAtualizarUmClienteExistente() {
        Mockito.when(pacoteTarifasRepository.save(Mockito.any(PacoteTarifas.class)))
                .thenReturn(pacoteTarifas);

        Optional<PacoteTarifas> resultadoCadastro = pacoteTarifasService.cadastrar(pacoteTarifasRequestDTO);
        Long pacoteTarifasId = resultadoCadastro.get().getId();

        PacoteTarifasRequestDTO pacoteTarifasRequest = getPacoteTarifasRequest();

        Mockito.when(pacoteTarifasRepository.findById(Mockito.any(Long.class)))
                .thenReturn(Optional.of(this.pacoteTarifas));

        PacoteTarifas pacoteTarifasRequestConverted = pacoteTarifasRequest.toPacoteTarifas();

        Mockito.when(pacoteTarifasRepository.save(Mockito.any(PacoteTarifas.class)))
                .thenReturn(pacoteTarifasRequestConverted);

        Optional<PacoteTarifas> resultado = pacoteTarifasService.atualizar(pacoteTarifasId,pacoteTarifasRequest);

        Assertions.assertEquals(pacoteTarifasRequestConverted.getNome(), resultado.get().getNome());
        Assertions.assertEquals(pacoteTarifasRequestConverted.getValorMinimo(), resultado.get().getValorMinimo());
        Assertions.assertEquals(pacoteTarifasRequestConverted.getValorMaximo(), resultado.get().getValorMaximo());
    }

    @Test
    public void retornariaErroAoAtualizarUmClienteInexistente() {
        PacoteTarifasRequestDTO pacoteTarifasRequest = getPacoteTarifasRequest();
        Long pacoteTarifasId = Long.parseLong("12");

        Mockito.when(pacoteTarifasRepository.findById(Mockito.any(Long.class)))
                .thenReturn(Optional.empty());

        PacoteTarifasException exception = Assertions.assertThrows(
                PacoteTarifasException.class,
                () -> pacoteTarifasService.atualizar(pacoteTarifasId, pacoteTarifasRequest)
        );
        Assertions.assertEquals("Pacote de Tarifas para atualizar nao encontrado", exception.getMessage());
        Mockito.verify(pacoteTarifasRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    public void retornariaErroAoRemoverClienteInexistente() {
        Long pacoteTarifasId = Long.parseLong("12");

        Mockito.when(pacoteTarifasRepository.findById(Mockito.any(Long.class)))
                .thenReturn(Optional.empty());

        PacoteTarifasRemoveException exception = Assertions.assertThrows(
                PacoteTarifasRemoveException.class,
                () -> pacoteTarifasService.remover(pacoteTarifasId)
        );

        Assertions.assertEquals("Nao foi possivel encontrar o pacote de tarifas com id 12", exception.getMessage());
        Mockito.verify(pacoteTarifasRepository, Mockito.never()).deleteById(Mockito.any());
    }

    @Test
    public void retornariaErroAoRemoverPacoteDeTarifasQuePossuemClientesCadastrados() {
        PacoteTarifas pacoteTarifasComCliente = getPacoteTarifasComCliente();

        Mockito.when(pacoteTarifasRepository.save(Mockito.any(PacoteTarifas.class)))
                .thenReturn(pacoteTarifasComCliente);

        Optional<PacoteTarifas> resultadoCadastro = pacoteTarifasService.cadastrar(pacoteTarifasRequestDTO);

        Long pacoteTarifasId = resultadoCadastro.get().getId();

        Mockito.when(pacoteTarifasRepository.findById(Mockito.any(Long.class)))
                .thenReturn(Optional.of(pacoteTarifasComCliente));

        PacoteTarifasRemoveException exception = Assertions.assertThrows(
                PacoteTarifasRemoveException.class,
                () -> pacoteTarifasService.remover(pacoteTarifasId)
        );

        Assertions.assertEquals("Nao foi possivel remover porque clientes possuem esse pacote de tarifas", exception.getMessage());
        Mockito.verify(pacoteTarifasRepository, Mockito.never()).deleteById(Mockito.any());
    }

    private static PacoteTarifasRequestDTO getPacoteTarifasRequest() {
        return new PacoteTarifasRequestDTOBuilder()
                .comNome("PacoteTarifas Mockado Atualizado")
                .comValorMinimo("11.0")
                .comValorMaximo("150.0")
                .criar();
    }

    private PacoteTarifas getPacoteTarifas() {
        return new PacoteTarifasBuilder()
                .comId(Long.parseLong("1"))
                .comNome("PacoteTarifas Mockado I")
                .comValorMinimo(new BigDecimal("11.0"))
                .comValorMaximo(new BigDecimal("150.0"))
                .criar();
    }

    private PacoteTarifas getPacoteTarifasComCliente() {
        return new PacoteTarifasBuilder()
                .comId(Long.parseLong("1"))
                .comNome("PacoteTarifas Mockado I")
                .comValorMinimo(new BigDecimal("11.0"))
                .comValorMaximo(new BigDecimal("150.0"))
                .comClientes(getListaClientes())
                .criar();
    }

    private List<Cliente> getListaClientes() {
        return List.of(new Cliente("Sheldon", "988.277.633-76", LocalDate.now()));

    }

}