package com.example.integrando.repository;

import com.example.integrando.models.PacoteTarifas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PacoteTarifasRepository extends JpaRepository<PacoteTarifas, Long> {

}
