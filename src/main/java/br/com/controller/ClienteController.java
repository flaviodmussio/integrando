package br.com.controller;


import br.com.dto.ClienteDTO;
import br.com.dto.ClienteListDTO;
import br.com.dto.ClienteRequestDTO;
import br.com.dto.ClienteResponseDTO;
import br.com.models.Cliente;
import br.com.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;

	@GetMapping
	public List<ClienteListDTO> listarTodos(@RequestParam(required = false) String nome, @RequestParam(required = false) Long id, @RequestParam(required = false) String cpf) {
		List<Cliente> clientes = clienteService.listar(nome, id, cpf);

		return clientes.stream()
				.map(ClienteListDTO::new)
				.collect(Collectors.toList());
	}

	@PostMapping
	@Transactional
	public ResponseEntity<ClienteResponseDTO> cadastrar(@RequestBody @Valid ClienteRequestDTO clientRequest, UriComponentsBuilder uriComponentsBuilder) {
		Optional<Cliente> cliente = clienteService.cadastrar(clientRequest);

		return cliente
				.map(clienteSalvo -> {
					URI uri = uriComponentsBuilder.path("/clientes/{id}").buildAndExpand(clienteSalvo.getId()).toUri();

					return ResponseEntity.created(uri).body(new ClienteResponseDTO("cadastrado", new ClienteDTO(clienteSalvo)));
				})
				.orElseGet(() -> ResponseEntity.badRequest().build());
	}

	@GetMapping("/{id}")
	public ResponseEntity<ClienteDTO> detalhar(@PathVariable Long id) {
		return clienteService.encontrar(id)
				.map(clienteEncontrado -> ResponseEntity.ok().body(new ClienteDTO(clienteEncontrado)))
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PutMapping("/{id}")
	public ResponseEntity<ClienteResponseDTO> atualizar(@PathVariable Long id, @RequestBody @Valid ClienteRequestDTO clienteRequest) {
		Optional<Cliente> cliente =clienteService.atualizar(id, clienteRequest);

		return cliente
				.map(clienteAtualizado -> ResponseEntity.ok().body(new ClienteResponseDTO("atualizado", new ClienteDTO(clienteAtualizado))))
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<Object> remover(@PathVariable Long id) {

		clienteService.remover(id);

		return ResponseEntity.noContent().build();
	}

}