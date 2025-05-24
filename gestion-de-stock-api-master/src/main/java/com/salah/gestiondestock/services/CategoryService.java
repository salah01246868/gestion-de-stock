package com.salah.gestiondestock.services;

import java.util.List;

import com.salah.gestiondestock.dto.CategoryDto;

public interface CategoryService {

  CategoryDto save(CategoryDto dto);

  CategoryDto findById(Integer id);

  CategoryDto findByCode(String code);

  List<CategoryDto> findAll();

  void delete(Integer id);

}
