package com.example.integrando.repository;

import com.example.integrando.models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    List<Cliente> findAllByNome(String nome);

    List<Cliente> findByCpf(String cpf);
}
