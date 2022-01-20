package com.example.expensetracker.controller;

import com.example.expensetracker.dto.Category;
import com.example.expensetracker.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/category")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class CategoryController {

    private final CategoryService categoryService;
    private static final double DOUBLE_ZERO = 0.0d;


    @PostMapping()
    private Long createCategory(@RequestBody Category category) {

        if(category.getBalance() == null) {
            category.setBalance(DOUBLE_ZERO);
        }

        return categoryService.createCategory(category);
    }

    @GetMapping()
    private List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }
}
