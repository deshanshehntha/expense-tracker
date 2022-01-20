package com.example.expensetracker.dao;

import com.example.expensetracker.dto.Category;
import com.example.expensetracker.dto.User;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {

    Category findCategoryByCategoryName(String categoryName);
    Category findCategoryByCategoryId(Long categoryId);
}
