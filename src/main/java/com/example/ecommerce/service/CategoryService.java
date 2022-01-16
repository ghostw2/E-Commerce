package com.example.ecommerce.service;

import com.example.ecommerce.model.Category;
import com.example.ecommerce.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    public CategoryRepository categoryRepo;
    public void createCategory(Category category){
        categoryRepo.save(category);
    }
    public List<Category> listCategory(){
       return categoryRepo.findAll();
    }
    public void updateCategory(int categoryId, Category category){
        Category category_old = categoryRepo.getById(categoryId);
        if(category_old != null){
            category_old.setCategoryName(category.getCategoryName());
            category_old.setDescription(category.getDescription());
            category_old.setImageUrl(category.getImageUrl());
            categoryRepo.save(category_old);
        }


    }

    public boolean findById(int categoryId) {
        return categoryRepo.findById(categoryId).isPresent();
    }
}
