package com.asciugano.blog.services;

import java.util.List;

import com.asciugano.blog.domain.entities.Category;

public interface CategoryService {
  List<Category> listCategories();

  Category createCategory(Category category);

}
