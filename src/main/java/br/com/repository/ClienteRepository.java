package br.com.repository;

import br.com.models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    List<Cliente> findAllByNome(String nome);

    List<Cliente> findAllByCpf(String cpf);

    Optional<Cliente> findByCpf(String cpf);

}