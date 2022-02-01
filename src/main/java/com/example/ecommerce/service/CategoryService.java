package com.example.ecommerce.service;

import com.example.ecommerce.model.Category;
import com.example.ecommerce.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CategoryService {
    @Autowired
    public CategoryRepository categoryRepo;

    public void createCategory(Category category){
        categoryRepo.save(category);
    }

    public List<Category> listCategory(){

        return categoryRepo.findAll();
    }
    public Category readCategory(String categoryName) {
        return categoryRepo.findByCategoryName(categoryName);
    }
    public boolean findById(int categoryId) {
        return categoryRepo.findById(categoryId).isPresent();
    }

    public void updateCategory(int categoryId, Category category){
        Category category_old = categoryRepo.getById(categoryId);
        if(category_old != null){
            category_old.setCategoryName(category.getCategoryName());
            category_old.setDescription(category.getDescription());
            category_old.setImageUrl(category.getImageUrl());
            category.setProducts(category.getProducts());
            categoryRepo.save(category_old);
        }


    }


}
