package com.salah.gestiondestock.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.salah.gestiondestock.dto.CategoryDto;
import com.salah.gestiondestock.exception.EntityNotFoundException;
import com.salah.gestiondestock.exception.ErrorCodes;
import com.salah.gestiondestock.exception.InvalidEntityException;
import com.salah.gestiondestock.services.CategoryService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceImplTest {

  @Autowired
  private CategoryService service;

  @Test
  public void shouldSaveCategoryWithSuccess() {
    CategoryDto expectedCategory = CategoryDto.builder()
        .code("Cat test")
        .designation("Designation test")
        .idEntreprise(1)
        .build();

    CategoryDto savedCategory = service.save(expectedCategory);

    assertNotNull(savedCategory);
    assertNotNull(savedCategory.getId());
    assertEquals(expectedCategory.getCode(), savedCategory.getCode());
    assertEquals(expectedCategory.getDesignation(), savedCategory.getDesignation());
    assertEquals(expectedCategory.getIdEntreprise(), savedCategory.getIdEntreprise());
  }

  @Test
  public void shouldUpdateCategoryWithSuccess() {
    CategoryDto expectedCategory = CategoryDto.builder()
        .code("Cat test")
        .designation("Designation test")
        .idEntreprise(1)
        .build();

    CategoryDto savedCategory = service.save(expectedCategory);

    CategoryDto categoryToUpdate = savedCategory;
    categoryToUpdate.setCode("Cat update");

    savedCategory = service.save(categoryToUpdate);

    assertNotNull(categoryToUpdate);
    assertNotNull(categoryToUpdate.getId());
    assertEquals(categoryToUpdate.getCode(), savedCategory.getCode());
    assertEquals(categoryToUpdate.getDesignation(), savedCategory.getDesignation());
    assertEquals(categoryToUpdate.getIdEntreprise(), savedCategory.getIdEntreprise());
  }

  @Test
  public void shouldThrowInvalidEntityException() {
    CategoryDto expectedCategory = CategoryDto.builder().build();

    InvalidEntityException expectedException = assertThrows(InvalidEntityException.class, () -> service.save(expectedCategory));

    assertEquals(ErrorCodes.CATEGORY_NOT_VALID, expectedException.getErrorCode());
    assertEquals(1, expectedException.getErrors().size());
    assertEquals("Veuillez renseigner le code de la categorie", expectedException.getErrors().get(0));
  }

  @Test
  public void shouldThrowEntityNotFoundException() {
    EntityNotFoundException expectedException = assertThrows(EntityNotFoundException.class, () -> service.findById(0));

    assertEquals(ErrorCodes.CATEGORY_NOT_FOUND, expectedException.getErrorCode());
    assertEquals("Aucune category avec l'ID = 0 n' ete trouve dans la BDD", expectedException.getMessage());
  }

  @Test(expected = EntityNotFoundException.class)
  public void shouldThrowEntityNotFoundException2() {
    service.findById(0);
  }

}
