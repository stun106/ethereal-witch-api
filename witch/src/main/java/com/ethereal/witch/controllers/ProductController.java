package com.ethereal.witch.controllers;

import com.ethereal.witch.interfaces.ICategoryRepository;
import com.ethereal.witch.interfaces.IProductRepository;

import com.ethereal.witch.models.collection.Category;
import com.ethereal.witch.models.product.Product;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/witch/product")
public class ProductController {
    @Autowired
    private IProductRepository productRepository;
    @Autowired
    private ICategoryRepository categoryRepository;
    @PostMapping("/create/auth")
    public ResponseEntity create(@RequestBody Product productEntity) {
        var product = this.productRepository.findByNomeproduct(productEntity.getNomeproduct());
        var category = categoryRepository.findByCategoryid(productEntity.getProductcategory().getCategoryid());
        if (product != null) {
            Map<String, String> flashmsg = new HashMap<>();
            flashmsg.put("error", "Algo de errado, verifique seus dados!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(flashmsg);
        }
        Map<String, String> flashmsg = new HashMap<>();

        productEntity.setProductcategory(category);
        this.productRepository.save(productEntity);
        flashmsg.put("msg", productEntity.getNomeproduct() + " cadastrado com sucesso");
        return ResponseEntity.status(HttpStatus.CREATED).body(flashmsg);
    }
}
