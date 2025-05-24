package com.salah.gestiondestock.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.salah.gestiondestock.dto.ArticleDto;
import com.salah.gestiondestock.dto.CategoryDto;
import com.salah.gestiondestock.dto.LigneCommandeClientDto;
import com.salah.gestiondestock.dto.LigneCommandeFournisseurDto;
import com.salah.gestiondestock.dto.LigneVenteDto;
import com.salah.gestiondestock.exception.EntityNotFoundException;
import com.salah.gestiondestock.exception.ErrorCodes;
import com.salah.gestiondestock.exception.InvalidEntityException;
import com.salah.gestiondestock.exception.InvalidOperationException;
import com.salah.gestiondestock.model.LigneCommandeClient;
import com.salah.gestiondestock.model.LigneCommandeFournisseur;
import com.salah.gestiondestock.model.LigneVente;
import com.salah.gestiondestock.repository.ArticleRepository;
import com.salah.gestiondestock.repository.LigneCommandeClientRepository;
import com.salah.gestiondestock.repository.LigneCommandeFournisseurRepository;
import com.salah.gestiondestock.repository.LigneVenteRepository;
import com.salah.gestiondestock.services.ArticleService;
import com.salah.gestiondestock.validator.ArticleValidator;

@Service
@Slf4j
public class ArticleServiceImpl implements ArticleService {

  private ArticleRepository articleRepository;
  private LigneVenteRepository venteRepository;
  private LigneCommandeFournisseurRepository commandeFournisseurRepository;
  private LigneCommandeClientRepository commandeClientRepository;

  @Autowired
  public ArticleServiceImpl(
      ArticleRepository articleRepository,
      LigneVenteRepository venteRepository, LigneCommandeFournisseurRepository commandeFournisseurRepository,
      LigneCommandeClientRepository commandeClientRepository) {
    this.articleRepository = articleRepository;
    this.venteRepository = venteRepository;
    this.commandeFournisseurRepository = commandeFournisseurRepository;
    this.commandeClientRepository = commandeClientRepository;
  }

  @Override
  public ArticleDto save(ArticleDto dto) {
    List<String> errors = ArticleValidator.validate(dto);
    if (!errors.isEmpty()) {
      log.error("Article is not valid {}", dto);
      throw new InvalidEntityException("L'article n'est pas valide", ErrorCodes.ARTICLE_NOT_VALID, errors);
    }

    return ArticleDto.fromEntity(
        articleRepository.save(
            ArticleDto.toEntity(dto)
        )
    );
  }

  @Override
  public ArticleDto findById(Integer id) {
    if (id == null) {
      log.error("Article ID is null");
      return null;
    }

    return articleRepository.findById(id).map(ArticleDto::fromEntity).orElseThrow(() ->
        new EntityNotFoundException(
            "Aucun article avec l'ID = " + id + " n' ete trouve dans la BDD",
            ErrorCodes.ARTICLE_NOT_FOUND)
    );
  }

  @Override
  public ArticleDto findByCodeArticle(String codeArticle) {
    if (!StringUtils.hasLength(codeArticle)) {
      log.error("Article CODE is null");
      return null;
    }

    return articleRepository.findArticleByCodeArticle(codeArticle)
        .map(ArticleDto::fromEntity)
        .orElseThrow(() ->
            new EntityNotFoundException(
                "Aucun article avec le CODE = " + codeArticle + " n' ete trouve dans la BDD",
                ErrorCodes.ARTICLE_NOT_FOUND)
        );
  }

  @Override
  @Transactional(readOnly = true)
  public List<ArticleDto> findAll() {
    log.info("Beginning findAll() method for articles");
    List<ArticleDto> result = new java.util.ArrayList<>();
    
    try {
      List<com.salah.gestiondestock.model.Article> articles = articleRepository.findAll();
      log.info("Found {} articles in the database", articles.size());
      
      for (com.salah.gestiondestock.model.Article article : articles) {
        try {
          if (article == null) {
            log.warn("Skipping null article in the result set");
            continue;
          }
          
          log.debug("Processing article with ID: {}", article.getId());
          ArticleDto dto = new ArticleDto();
          
          if (article.getId() != null) {
            dto.setId(article.getId());
          }
          if (article.getCodeArticle() != null) {
            dto.setCodeArticle(article.getCodeArticle());
          }
          if (article.getDesignation() != null) {
            dto.setDesignation(article.getDesignation());
          }
          if (article.getPhoto() != null) {
            dto.setPhoto(article.getPhoto());
          }
          if (article.getPrixUnitaireHt() != null) {
            dto.setPrixUnitaireHt(article.getPrixUnitaireHt());
          }
          if (article.getPrixUnitaireTtc() != null) {
            dto.setPrixUnitaireTtc(article.getPrixUnitaireTtc());
          }
          if (article.getTauxTva() != null) {
            dto.setTauxTva(article.getTauxTva());
          }
          if (article.getIdEntreprise() != null) {
            dto.setIdEntreprise(article.getIdEntreprise());
          }
          
          // Handle category safely with additional null checks
          try {
            if (article.getCategory() != null) {
              CategoryDto categoryDto = new CategoryDto();
              
              if (article.getCategory().getId() != null) {
                categoryDto.setId(article.getCategory().getId());
              }
              if (article.getCategory().getCode() != null) {
                categoryDto.setCode(article.getCategory().getCode());
              }
              if (article.getCategory().getDesignation() != null) {
                categoryDto.setDesignation(article.getCategory().getDesignation());
              }
              
              dto.setCategory(categoryDto);
            }
          } catch (Exception e) {
            log.warn("Error processing category for article ID {}: {}", article.getId(), e.getMessage());
            // Continue without setting the category
          }
          
          result.add(dto);
          log.debug("Successfully added article ID {} to result list", article.getId());
          
        } catch (Exception e) {
          log.error("Error processing individual article: " + e.getMessage(), e);
          // Continue processing other articles
        }
      }
      
      log.info("Successfully processed {} articles out of {} found in the database", result.size(), articles.size());
      return result;
      
    } catch (Exception e) {
      log.error("Error while fetching all articles: " + e.getMessage(), e);
      return result; // Return whatever we've managed to collect so far
    }
  }

  @Override
  public List<LigneVenteDto> findHistoriqueVentes(Integer idArticle) {
    return venteRepository.findAllByArticleId(idArticle).stream()
        .map(LigneVenteDto::fromEntity)
        .collect(Collectors.toList());
  }

  @Override
  public List<LigneCommandeClientDto> findHistoriaueCommandeClient(Integer idArticle) {
    return commandeClientRepository.findAllByArticleId(idArticle).stream()
        .map(LigneCommandeClientDto::fromEntity)
        .collect(Collectors.toList());
  }

  @Override
  public List<LigneCommandeFournisseurDto> findHistoriqueCommandeFournisseur(Integer idArticle) {
    return commandeFournisseurRepository.findAllByArticleId(idArticle).stream()
        .map(LigneCommandeFournisseurDto::fromEntity)
        .collect(Collectors.toList());
  }

  @Override
  public List<ArticleDto> findAllArticleByIdCategory(Integer idCategory) {
    return articleRepository.findAllByCategoryId(idCategory).stream()
        .map(ArticleDto::fromEntity)
        .collect(Collectors.toList());
  }

  @Override
  public void delete(Integer id) {
    if (id == null) {
      log.error("Article ID is null");
      return;
    }
    List<LigneCommandeClient> ligneCommandeClients = commandeClientRepository.findAllByArticleId(id);
    if (!ligneCommandeClients.isEmpty()) {
      throw new InvalidOperationException("Impossible de supprimer un article deja utilise dans des commandes client", ErrorCodes.ARTICLE_ALREADY_IN_USE);
    }
    List<LigneCommandeFournisseur> ligneCommandeFournisseurs = commandeFournisseurRepository.findAllByArticleId(id);
    if (!ligneCommandeFournisseurs.isEmpty()) {
      throw new InvalidOperationException("Impossible de supprimer un article deja utilise dans des commandes fournisseur",
          ErrorCodes.ARTICLE_ALREADY_IN_USE);
    }
    List<LigneVente> ligneVentes = venteRepository.findAllByArticleId(id);
    if (!ligneVentes.isEmpty()) {
      throw new InvalidOperationException("Impossible de supprimer un article deja utilise dans des ventes",
          ErrorCodes.ARTICLE_ALREADY_IN_USE);
    }
    articleRepository.deleteById(id);
  }
}
