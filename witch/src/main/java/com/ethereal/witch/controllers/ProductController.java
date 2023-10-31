package com.ethereal.witch.controllers;

import com.ethereal.witch.interfaces.ICategoryRepository;
import com.ethereal.witch.interfaces.IProductRepository;

import com.ethereal.witch.interfaces.ITypeRepository;
import com.ethereal.witch.models.collection.Category;
import com.ethereal.witch.models.product.Product;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/witch/product")
public class ProductController {
    @Autowired
    private IProductRepository productRepository;
    @Autowired
    private ICategoryRepository categoryRepository;
    @Autowired
    private ITypeRepository typeRepository;
    @PostMapping("/create/auth")
    public ResponseEntity create(@RequestBody Product productEntity) {
        var product = this.productRepository.findByNomeproduct(productEntity.getNomeproduct());
        var category = categoryRepository.findByCategoryid(productEntity.getProductcategory().getCategoryid());
        var type = typeRepository.findByTypeid(productEntity.getNometype().getTypeid());

        if (product != null) {
            Map<String, String> flashmsg = new HashMap<>();
            flashmsg.put("error", "Algo de errado, verifique seus dados!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(flashmsg);
        }
        Map<String, String> flashmsg = new HashMap<>();

        productEntity.setNometype(type);
        productEntity.setProductcategory(category);
        this.productRepository.save(productEntity);
        flashmsg.put("msg", productEntity.getNomeproduct() + " cadastrado com sucesso");
        return ResponseEntity.status(HttpStatus.CREATED).body(flashmsg);
    }
    @GetMapping("/collection/")
    public ResponseEntity<List<Object[]>> findProductCat(@RequestParam("nome") String ncat){
        var product = this.productRepository.findProductCategory(ncat);
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }

    @PutMapping("/change/{id}/auth")
    public void update(@PathVariable("id") Long id, @RequestBody Product product){
        var produtcId = this.productRepository.findByProductid(id);
        var type = this.typeRepository.findByTypeid(produtcId.getNometype().getTypeid());
        var category = categoryRepository.findByCategoryid(produtcId.getProductcategory().getCategoryid());
        product.setProductid(id);
        product.setNometype(type);
        product.setProductcategory(category);
        this.productRepository.save(product);
    }
}
