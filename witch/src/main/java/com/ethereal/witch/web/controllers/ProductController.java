package com.ethereal.witch.web.controllers;

import com.ethereal.witch.models.collection.Category;
import com.ethereal.witch.models.product_type.TypeProduct;

import com.ethereal.witch.repository.ITypeRepository;
import com.ethereal.witch.models.product.Product;

import com.ethereal.witch.service.CategoryService;
import com.ethereal.witch.service.ProductService;
import com.ethereal.witch.service.TypeService;
import com.ethereal.witch.web.dto.ProductCreateDto;
import com.ethereal.witch.web.dto.ProductResponseDto;
import com.ethereal.witch.web.dto.mapper.ProductMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/witch/product")
@AllArgsConstructor
public class ProductController {
    private final CategoryService categoryService;
    private final TypeService typeService;
    private final ProductService productService;

    @PostMapping("/create/auth")
    public ResponseEntity<ProductResponseDto> create(@Valid @RequestBody ProductCreateDto productDto, HttpServletRequest request) {
        Category productCategory = categoryService.categoryForId(productDto.getProductcategory().getCategoryid());
        TypeProduct typeProduct = typeService.findTypeId(productDto.getNometype().getTypeid());
        productDto.setNometype(typeProduct);
        productDto.setProductcategory(productCategory);
        Product product = productService.saveProduct(ProductMapper.toProduct(productDto),request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ProductMapper.toProductDto(product));
    }

    @GetMapping("/single/{id}")
    public ResponseEntity<ProductResponseDto> findId(@PathVariable("id") Long id) {
       Product product = productService.findById(id);
       return ResponseEntity.status(HttpStatus.OK).body(ProductMapper.toProductDto(product));
    }

    @GetMapping("/type/{nome}")
    public ResponseEntity<List<Object[]>> findProductByType(@PathVariable("nome") String nome){
       List<Object[]> allProduct = productService.findAllForType(nome);
        return ResponseEntity.status(HttpStatus.OK).body(allProduct);
    }
    @GetMapping("/collection/{nome}")
    public ResponseEntity<List<Object[]>> findProductCat(@PathVariable("nome") String ncat){
        List<Object[]> product = productService.findAllForCategory(ncat) ;
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }

    @PutMapping("/change/{id}/auth")
    public ResponseEntity<Map<String,String>> update(@PathVariable("id") Long id, @Valid @RequestBody ProductCreateDto productDto, HttpServletRequest request){
        productService.updateProduct(id,productDto.getNomeproduct(),productDto.getValor(),productDto.getImage(),request);
        Map<String, String> flashMsg = new HashMap<>();
        flashMsg.put("msg",String.format("product changed succefully for id {%d}.",id));
        return ResponseEntity.status(HttpStatus.OK).body(flashMsg);
    }

    @DeleteMapping("/del/{id}/auth")
    public ResponseEntity<Map<String,String>> destroy(@PathVariable("id") Long id, HttpServletRequest request){
        productService.destroyProduct(id,request);
        Map<String, String> flashmsg = new HashMap<>();
        flashmsg.put("msg",String.format("Product deleted succefully for id {%d}",id));
        return ResponseEntity.status(HttpStatus.OK).body(flashmsg);
    }
}
