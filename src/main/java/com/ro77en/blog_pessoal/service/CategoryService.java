package com.ro77en.blog_pessoal.service;

import com.ro77en.blog_pessoal.dto.CategoryDTO;
import com.ro77en.blog_pessoal.model.Category;
import com.ro77en.blog_pessoal.repository.CategoryRepository;
import jakarta.persistence.EntityExistsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public Category createCategory(CategoryDTO categoryDTO) {
        if (categoryRepository.existsByTitle(categoryDTO.title())) {
            throw new EntityExistsException("Category already exists");
        }

        Category newCategory = new Category();
        newCategory.setTitle(categoryDTO.title());
        return categoryRepository.save(newCategory);
    }

    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }
}
