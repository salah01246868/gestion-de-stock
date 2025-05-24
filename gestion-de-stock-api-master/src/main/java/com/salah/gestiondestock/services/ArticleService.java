package com.salah.gestiondestock.services;

import java.util.List;

import com.salah.gestiondestock.dto.ArticleDto;
import com.salah.gestiondestock.dto.LigneCommandeClientDto;
import com.salah.gestiondestock.dto.LigneCommandeFournisseurDto;
import com.salah.gestiondestock.dto.LigneVenteDto;

public interface ArticleService {

  ArticleDto save(ArticleDto dto);

  ArticleDto findById(Integer id);

  ArticleDto findByCodeArticle(String codeArticle);

  List<ArticleDto> findAll();

  List<LigneVenteDto> findHistoriqueVentes(Integer idArticle);

  List<LigneCommandeClientDto> findHistoriaueCommandeClient(Integer idArticle);

  List<LigneCommandeFournisseurDto> findHistoriqueCommandeFournisseur(Integer idArticle);

  List<ArticleDto> findAllArticleByIdCategory(Integer idCategory);

  void delete(Integer id);

}
