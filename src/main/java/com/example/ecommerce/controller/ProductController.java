package com.example.ecommerce.controller;

import com.example.ecommerce.common.ApiResponse;
import com.example.ecommerce.dto.ProductDto;
import com.example.ecommerce.model.Category;
import com.example.ecommerce.repository.CategoryRepository;
import com.example.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    ProductService productService;
    @Autowired
    CategoryRepository categoryRepo;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> createProduct(@RequestBody ProductDto product){
        Optional<Category> optionalCategory = categoryRepo.findById(product.getCategoryId());
        if(!optionalCategory.isPresent()){
            return new ResponseEntity<>(new ApiResponse(false,"Product category does not exists"),HttpStatus.NOT_FOUND);
        }
        productService.save(product,optionalCategory.get ());
        return new ResponseEntity<>(new ApiResponse(true,"New product succesfully created."), HttpStatus.CREATED);
    }
    @GetMapping("/")
    public ResponseEntity<List<ProductDto>> getProducts(){
        List<ProductDto> products = productService.getAllProducts();
        return new ResponseEntity<>(products,HttpStatus.OK);
    }
    @PostMapping("/update/{productId}")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable("productId") Long productId,@RequestBody ProductDto productDto)
    throws  Exception {
        //find category
        Optional<Category> optionalCategory = categoryRepo.findById(productDto.getCategoryId());

        if(!optionalCategory.isPresent()){
            return new ResponseEntity<>(new ApiResponse(false," category not found"),HttpStatus.BAD_REQUEST);
        }
        productService.updateProduct(productDto,productId);
        return new ResponseEntity<>(new ApiResponse(true,"updated product succesfully"),HttpStatus.OK);
    }
}
