package com.example.expensetracker.service;

import com.example.expensetracker.dao.CategoryRepository;
import com.example.expensetracker.dto.Category;
import com.example.expensetracker.dto.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Long createCategory(Category category) {
        category.setBalance(category.getInitialAmount());
        Category newCategory = categoryRepository.save(category);
        return newCategory.getCategoryId();
    }

    public List<Category> getAllCategories() {
        return (List<Category>) categoryRepository.findAll();
    }
}
