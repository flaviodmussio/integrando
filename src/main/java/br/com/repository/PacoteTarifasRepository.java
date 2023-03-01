package br.com.repository;

import br.com.models.PacoteTarifas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PacoteTarifasRepository extends JpaRepository<PacoteTarifas, Long> {

    List<PacoteTarifas> findAllByClientesId(Long id);

    List<PacoteTarifas> findAllByClientesNome(String nome);
}