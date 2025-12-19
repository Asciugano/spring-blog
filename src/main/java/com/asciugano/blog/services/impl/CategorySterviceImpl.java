package com.asciugano.blog.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.asciugano.blog.domain.entities.Category;
import com.asciugano.blog.repository.CategoryRepository;
import com.asciugano.blog.services.CategoryService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategorySterviceImpl implements CategoryService {

  private final CategoryRepository categoryRepository;

  @Override
  public List<Category> listCategories() {
    return categoryRepository.findAllWithPostCount();
  }

  @Override
  @Transactional
  public Category createCategory(Category category) {
    if (categoryRepository.existsByNameIgnoreCase(category.getName())) {
      throw new IllegalArgumentException("Category already exists with name: " + category.getName());
    }

    return categoryRepository.save(category);
  }

  @Override
  public void deleteCategory(UUID id) {
    Optional<Category> category = categoryRepository.findById(id);
    if (category.isPresent()) {
      if (!category.get().getPosts().isEmpty()) {
        throw new IllegalStateException("Category had posts associated with it");
      }

      categoryRepository.deleteById(id);
    }
  }

}
