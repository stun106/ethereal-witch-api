package com.ethereal.witch.web.controllers;

import com.ethereal.witch.repository.ICategoryRepository;
import com.ethereal.witch.repository.IUserRepository;
import com.ethereal.witch.models.collection.Category;
import com.ethereal.witch.service.CategoryService;
import com.ethereal.witch.web.dto.CategoryCreateDto;
import com.ethereal.witch.web.dto.CategoryResponseDto;
import com.ethereal.witch.web.dto.mapper.CategoryMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/witch/collection/")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("/create/auth")
    public ResponseEntity<Map<String,String>> create(@RequestBody  @Valid CategoryCreateDto categoryDto,
                                                     HttpServletRequest request){
        categoryService.saveCategory(CategoryMapper.toCategory(categoryDto),request);
        Map<String,String> flashMsg = new HashMap<>();
        flashMsg.put("msg", categoryDto.getNomecategory() + " created successfuly!");
        return ResponseEntity.status(HttpStatus.CREATED).body(flashMsg);
    }

    @GetMapping("/single/{id}")
    public ResponseEntity<CategoryResponseDto> findById(@PathVariable("id") Long id){
        Category categoryId = categoryService.categoryForId(id);
        return ResponseEntity.status(HttpStatus.OK).body(CategoryMapper.toDto(categoryId));
    }
    @GetMapping("/all")
    public ResponseEntity<List<CategoryResponseDto>> index() {
        List<Category> allCategory = this.categoryService.findAllCategory();
        return ResponseEntity.status(HttpStatus.OK).body(CategoryMapper.toListCategory(allCategory));
    }

    @PutMapping("/change/{id}/auth")
    public ResponseEntity<Map<String,String>> update(@PathVariable("id") Long id, @RequestBody CategoryCreateDto categoryDto, HttpServletRequest request){
        Category cate = categoryService.categoryForId(id);
        String nomeCateBefore = cate.getNomecategory();
        categoryService.updateCategory(id,categoryDto.getNomecategory(),request);
        Map<String, String> flashmsg = new HashMap<>();
        flashmsg.put("msg", "Category: " +  nomeCateBefore + " updated to  " + cate.getNomecategory() + ".");
        return ResponseEntity.status(HttpStatus.OK).body(flashmsg);
    }

    @DeleteMapping("/del/{id}/auth")
    public ResponseEntity<Map<String,String>> destroy(@PathVariable("id") Long id, HttpServletRequest request){
        Category category = categoryService.categoryForId(id);
        categoryService.destroyCategory(id,request);
        Map<String, String> flashmsg = new HashMap<>();
        flashmsg.put("msg", "Category " + category.getNomecategory() + " deleted succesfuly!");
        return ResponseEntity.status(HttpStatus.OK).body(flashmsg);
    }

}
