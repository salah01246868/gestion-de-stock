package com.salah.gestiondestock.services;

import java.math.BigDecimal;
import java.util.List;

import com.salah.gestiondestock.dto.CommandeClientDto;
import com.salah.gestiondestock.dto.LigneCommandeClientDto;
import com.salah.gestiondestock.model.EtatCommande;

public interface CommandeClientService {

  CommandeClientDto save(CommandeClientDto dto);

  CommandeClientDto updateEtatCommande(Integer idCommande, EtatCommande etatCommande);

  CommandeClientDto updateQuantiteCommande(Integer idCommande, Integer idLigneCommande, BigDecimal quantite);

  CommandeClientDto updateClient(Integer idCommande, Integer idClient);

  CommandeClientDto updateArticle(Integer idCommande, Integer idLigneCommande, Integer newIdArticle);

  // Delete article ==> delete LigneCommandeClient
  CommandeClientDto deleteArticle(Integer idCommande, Integer idLigneCommande);

  CommandeClientDto findById(Integer id);

  CommandeClientDto findByCode(String code);

  List<CommandeClientDto> findAll();

  List<LigneCommandeClientDto> findAllLignesCommandesClientByCommandeClientId(Integer idCommande);

  void delete(Integer id);

}
