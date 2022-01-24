package com.example.ecommerce.service;

import com.example.ecommerce.dto.ProductDto;
import com.example.ecommerce.exceptions.ProductNotExistException;
import com.example.ecommerce.model.Category;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepo;

    public void save(ProductDto productDto, Category category) {
        Product product = getProductFromDto(productDto, category);
        productRepo.save(product);
    }

    public List<ProductDto> getAllProducts() {
        List<Product> allProducts = productRepo.findAll();
        List<ProductDto> allProductsDto = new ArrayList<>();
        for (Product product :allProducts) {
            allProductsDto.add(getDtoFromProduct(product));
        }
        return allProductsDto;
     }
    public void updateProduct(ProductDto productDto, Long productId, Category category) throws Exception {
        Product product = getProductFromDto(productDto, category);
        product.setId(productId);
        productRepo.save(product);
    }
    public Product findById (Long id) throws ProductNotExistException {
        Optional<Product> optionalProduct = productRepo.findById(id);
        if(optionalProduct.isEmpty()){
            throw new ProductNotExistException("product id is invalid: " + id);
        }
        return optionalProduct.get();
    }

    public static ProductDto getDtoFromProduct(Product product) {
        ProductDto productDto = new ProductDto(product);
        return productDto;
    }

    public static Product getProductFromDto(ProductDto productDto, Category category) {
        Product product = new Product(productDto, category);
        return product;
    }
}
