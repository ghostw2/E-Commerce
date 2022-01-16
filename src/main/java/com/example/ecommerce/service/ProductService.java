package com.example.ecommerce.service;

import com.example.ecommerce.dto.ProductDto;
import com.example.ecommerce.exceptions.ProductNotExistsException;
import com.example.ecommerce.model.Category;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepo;

    public void save(ProductDto productDto, Category category) {
        Product product = new Product();
        product.setCategory(category);
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setName(productDto.getName());
        product.setImageUrl(productDto.getImageUrl());
        productRepo.save(product);
    }
    public ProductDto getProductDto(Product product){
        ProductDto productDto = new ProductDto();
        productDto.setCategoryId(product.getCategory().getId());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setName(product.getName());
        productDto.setImageUrl(product.getImageUrl());

        productDto.setId(product.getId());
        return productDto;
    }

    public List<ProductDto> getAllProducts() {
        List<Product> allProducts = productRepo.findAll();
        List<ProductDto> allProductsDto = new ArrayList<>();
        for (Product product :allProducts) {
            allProductsDto.add(getProductDto(product));
        }
        return allProductsDto;
     }
    public void updateProduct(ProductDto productDto, Long productId) throws Exception {
        Optional<Product> optionalProduct = productRepo.findById(productId);
        // throw an exception if product does not exists
        if (!optionalProduct.isPresent()) {
            throw new Exception("product not present");
        }
        Product product = optionalProduct.get();
        product.setDescription(productDto.getDescription());
        product.setImageUrl(productDto.getImageUrl());
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());

        productRepo.save(product);
    }
    public Product findById (Long id) throws ProductNotExistsException{
        Optional<Product> optionalProduct = productRepo.findById(id);
        if(optionalProduct.isEmpty()){
            throw new ProductNotExistsException("product id is invalid: " + id);
        }
        return optionalProduct.get();
    }
}
