package com.salah.gestiondestock.services;

import java.math.BigDecimal;
import java.util.List;

import com.salah.gestiondestock.dto.CommandeFournisseurDto;
import com.salah.gestiondestock.dto.LigneCommandeFournisseurDto;
import com.salah.gestiondestock.model.EtatCommande;

public interface CommandeFournisseurService {

  CommandeFournisseurDto save(CommandeFournisseurDto dto);

  CommandeFournisseurDto updateEtatCommande(Integer idCommande, EtatCommande etatCommande);

  CommandeFournisseurDto updateQuantiteCommande(Integer idCommande, Integer idLigneCommande, BigDecimal quantite);

  CommandeFournisseurDto updateFournisseur(Integer idCommande, Integer idFournisseur);

  CommandeFournisseurDto updateArticle(Integer idCommande, Integer idLigneCommande, Integer idArticle);

  // Delete article ==> delete LigneCommandeFournisseur
  CommandeFournisseurDto deleteArticle(Integer idCommande, Integer idLigneCommande);

  CommandeFournisseurDto findById(Integer id);

  CommandeFournisseurDto findByCode(String code);

  List<CommandeFournisseurDto> findAll();

  List<LigneCommandeFournisseurDto> findAllLignesCommandesFournisseurByCommandeFournisseurId(Integer idCommande);

  void delete(Integer id);

}
