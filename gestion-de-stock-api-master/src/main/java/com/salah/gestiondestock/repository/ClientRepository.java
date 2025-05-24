package com.salah.gestiondestock.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salah.gestiondestock.model.Client;

public interface ClientRepository extends JpaRepository<Client, Integer> {

}
