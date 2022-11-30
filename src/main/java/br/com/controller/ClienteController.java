package br.com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.models.Cliente;
import br.com.service.ClienteService;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

	
	@Autowired
	private ClienteService clienteService;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente Salvar(Cliente cliente) {
		return clienteService.salvar(cliente);
	}
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<Cliente> listaCliente(){
		return clienteService.listaCliente();
	}
	
//    !!!!!!!!!!!!!!!!!!pesquisar para arrumar!!!!!!!!!!!!!!!!	
	
	
//	@GetMapping("/{id}")
//	public Cliente buscarClientePorId() {
//		return clienteService.buscarPorId(id)
//				.orElseThrow(() -> new responseStatusExpection(HttpStatus.NOT_FOUND,"Cliente nao encontrado."));
//	}
//	
	
	
	
	
}
