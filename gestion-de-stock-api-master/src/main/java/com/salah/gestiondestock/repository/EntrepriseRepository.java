package com.salah.gestiondestock.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salah.gestiondestock.model.Entreprise;

public interface EntrepriseRepository extends JpaRepository<Entreprise, Integer> {

}
