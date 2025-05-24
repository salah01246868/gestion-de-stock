package com.salah.gestiondestock.services;

import java.util.List;

import com.salah.gestiondestock.dto.ChangerMotDePasseUtilisateurDto;
import com.salah.gestiondestock.dto.UtilisateurDto;

public interface UtilisateurService {

  UtilisateurDto save(UtilisateurDto dto);

  UtilisateurDto findById(Integer id);

  List<UtilisateurDto> findAll();

  void delete(Integer id);

  UtilisateurDto findByEmail(String email);

  UtilisateurDto changerMotDePasse(ChangerMotDePasseUtilisateurDto dto);


}
