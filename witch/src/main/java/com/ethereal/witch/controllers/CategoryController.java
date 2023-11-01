package com.ethereal.witch.controllers;

import com.ethereal.witch.interfaces.ICategoryRepository;
import com.ethereal.witch.models.collection.Category;
import com.ethereal.witch.models.collection.CategoryRecordDto;
import com.ethereal.witch.models.product.Product;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Array;
import java.util.*;

@RestController
@RequestMapping("/witch/collection")
public class CategoryController {
    @Autowired
    private ICategoryRepository categoryRepository;
    @PostMapping("/create/auth")
    public ResponseEntity<Map<String,String>> create(@RequestBody @Valid CategoryRecordDto categoryDto){
        var categoryEntity = new Category();
        BeanUtils.copyProperties(categoryDto,categoryEntity);
        var category = this.categoryRepository.findByCategoryid(categoryEntity.getCategoryid());
        if (category != null){
            Map<String,String> flashMsg = new HashMap<>();
            flashMsg.put("error","Algo de errado, Verifique seus dados!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(flashMsg);
        }
        this.categoryRepository.save(categoryEntity);
        Map<String,String> flashMsg = new HashMap<>();
        flashMsg.put("msg", categoryEntity.getNomecategory() + " Adicionado(a) como nova Collections!");
        return ResponseEntity.status(HttpStatus.CREATED).body(flashMsg);
    }

    @GetMapping("/single/")
    public ResponseEntity<Object[]> findById(@RequestParam("categoryId") Long id){
        var categoryId = this.categoryRepository.findByid(id);
        return ResponseEntity.status(HttpStatus.OK).body(categoryId);
    }
    @GetMapping("/all")
    public ResponseEntity<List<Object[]>> index() {
        var allCategory = this.categoryRepository.findAllNameCategory();
        return ResponseEntity.status(HttpStatus.OK).body(allCategory);
    }

    @GetMapping("/")
    public ResponseEntity<List<Object[]>> getCategoryByid(@RequestParam("nomecategory") String nomecategory){
        var categoryByname = this.categoryRepository.findBynamecategoyIlike(nomecategory);
        return ResponseEntity.status(HttpStatus.OK).body(categoryByname);
    }
    @PutMapping("/change/{id}")
    public ResponseEntity update(@PathVariable("id") Long id, @RequestBody Category category){
        Category cate = categoryRepository.findByCategoryid(id);
        String nomeCateBefore = cate.getNomecategory();
        if (cate == null){
            Map<String, String> flashmsg = new HashMap<>();
            flashmsg.put("error", "usuario não encontrado");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(flashmsg);
        }
        category.setCategoryid(cate.getCategoryid());
        this.categoryRepository.save(category);
        Map<String, String> flashmsg = new HashMap<>();
        flashmsg.put("msg", "Categoria: " +  nomeCateBefore + " foi alterada para " + category.getNomecategory() + ".");
        return ResponseEntity.status(HttpStatus.OK).body(flashmsg);
    }
}
