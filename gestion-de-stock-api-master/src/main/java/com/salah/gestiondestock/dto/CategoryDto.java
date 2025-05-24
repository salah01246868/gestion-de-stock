package com.salah.gestiondestock.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.salah.gestiondestock.model.Category;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {

  private Integer id;

  private String code;

  private String designation;

  private Integer idEntreprise;

  @JsonIgnore
  private List<ArticleDto> articles;

  public static CategoryDto fromEntity(Category category) {
    if (category == null) {
      return null;
    }

    CategoryDto.CategoryDtoBuilder builder = CategoryDto.builder();
    if (category.getId() != null) {
      builder.id(category.getId());
    }
    if (category.getCode() != null) {
      builder.code(category.getCode());
    }
    if (category.getDesignation() != null) {
      builder.designation(category.getDesignation());
    }
    if (category.getIdEntreprise() != null) {
      builder.idEntreprise(category.getIdEntreprise());
    }

    return builder.build();
  }

  public static Category toEntity(CategoryDto categoryDto) {
    if (categoryDto == null) {
      return null;
    }

    Category category = new Category();
    if (categoryDto.getId() != null) {
      category.setId(categoryDto.getId());
    }
    if (categoryDto.getCode() != null) {
      category.setCode(categoryDto.getCode());
    }
    if (categoryDto.getDesignation() != null) {
      category.setDesignation(categoryDto.getDesignation());
    }
    if (categoryDto.getIdEntreprise() != null) {
      category.setIdEntreprise(categoryDto.getIdEntreprise());
    }

    return category;
  }
}
