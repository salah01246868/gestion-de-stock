package com.salah.gestiondestock.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.salah.gestiondestock.model.Ventes;

public interface VentesRepository extends JpaRepository<Ventes, Integer> {

  Optional<Ventes> findVentesByCode(String code);
}
