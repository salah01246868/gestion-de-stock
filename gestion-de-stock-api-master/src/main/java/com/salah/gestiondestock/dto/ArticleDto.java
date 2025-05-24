package com.salah.gestiondestock.dto;

import java.math.BigDecimal;

import com.salah.gestiondestock.model.Article;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDto {

  private Integer id;

  private String codeArticle;

  private String designation;

  private BigDecimal prixUnitaireHt;

  private BigDecimal tauxTva;

  private BigDecimal prixUnitaireTtc;

  private String photo;

  private CategoryDto category;

  private Integer idEntreprise;

  public static ArticleDto fromEntity(Article article) {
    if (article == null) {
      return null;
    }
    ArticleDto.ArticleDtoBuilder builder = ArticleDto.builder();
    
    if (article.getId() != null) {
      builder.id(article.getId());
    }
    if (article.getCodeArticle() != null) {
      builder.codeArticle(article.getCodeArticle());
    }
    if (article.getDesignation() != null) {
      builder.designation(article.getDesignation());
    }
    if (article.getPhoto() != null) {
      builder.photo(article.getPhoto());
    }
    if (article.getPrixUnitaireHt() != null) {
      builder.prixUnitaireHt(article.getPrixUnitaireHt());
    }
    if (article.getPrixUnitaireTtc() != null) {
      builder.prixUnitaireTtc(article.getPrixUnitaireTtc());
    }
    if (article.getTauxTva() != null) {
      builder.tauxTva(article.getTauxTva());
    }
    if (article.getIdEntreprise() != null) {
      builder.idEntreprise(article.getIdEntreprise());
    }
        
    try {
      if (article.getCategory() != null) {
        builder.category(CategoryDto.fromEntity(article.getCategory()));
      }
    } catch (Exception e) {
      // If there's an issue with the category, log it but continue without the category
      System.err.println("Error processing category for article " + article.getId() + ": " + e.getMessage());
    }
    
    return builder.build();
  }

  public static Article toEntity(ArticleDto articleDto) {
    if (articleDto == null) {
      return null;
    }
    Article article = new Article();
    
    if (articleDto.getId() != null) {
      article.setId(articleDto.getId());
    }
    if (articleDto.getCodeArticle() != null) {
      article.setCodeArticle(articleDto.getCodeArticle());
    }
    if (articleDto.getDesignation() != null) {
      article.setDesignation(articleDto.getDesignation());
    }
    if (articleDto.getPhoto() != null) {
      article.setPhoto(articleDto.getPhoto());
    }
    if (articleDto.getPrixUnitaireHt() != null) {
      article.setPrixUnitaireHt(articleDto.getPrixUnitaireHt());
    }
    if (articleDto.getPrixUnitaireTtc() != null) {
      article.setPrixUnitaireTtc(articleDto.getPrixUnitaireTtc());
    }
    if (articleDto.getTauxTva() != null) {
      article.setTauxTva(articleDto.getTauxTva());
    }
    if (articleDto.getIdEntreprise() != null) {
      article.setIdEntreprise(articleDto.getIdEntreprise());
    }
    
    try {
      if (articleDto.getCategory() != null) {
        article.setCategory(CategoryDto.toEntity(articleDto.getCategory()));
      }
    } catch (Exception e) {
      // If there's an issue with the category, log it but continue without the category
      System.err.println("Error processing category for article: " + e.getMessage());
    }
    
    return article;
  }

}
