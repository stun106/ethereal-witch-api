package com.ethereal.witch.service;

import com.ethereal.witch.models.collection.Category;
import com.ethereal.witch.models.product.Product;
import com.ethereal.witch.models.product_type.TypeProduct;
import com.ethereal.witch.repository.ICategoryRepository;
import com.ethereal.witch.repository.IProductRepository;
import com.ethereal.witch.repository.ITypeRepository;
import com.ethereal.witch.service.exception.EntityNotfoundException;
import com.ethereal.witch.service.exception.UniqueViolationExeception;
import com.ethereal.witch.web.dto.ProductCreateDto;
import com.ethereal.witch.web.dto.ProductResponseDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductService {
    private final IProductRepository iProductRepository;
    private final ICategoryRepository iCategoryRepository;
    private final ITypeRepository iTypeRepository;
    public Product saveProduct (Product product){
        Product newProduct = iProductRepository.findByNomeproduct(product.getNomeproduct());
        if (newProduct != null){
            throw new UniqueViolationExeception(String.format("Product {%s} Already created.",product.getNomeproduct()));
        }else{
            return iProductRepository.save(product);
        }
    }
    public Product findById(Long id){
        return iProductRepository.findByProductid(id).orElseThrow(()->
                new EntityNotfoundException(String.format("Product with id: {%s} not exists.",id)));
    }
    public List<Object[]> findAllForType(String type){
        List<Object[]> findProduct = iProductRepository.findProductByType(type);
        if (findProduct.isEmpty()){
            throw new EntityNotfoundException("There are no products registered for this type of products.");
        }else{
            return iProductRepository.findProductByType(type);
        }
    }

    public List<Object[]> findAllForCategory(String category){
        List<Object[]> findProduct = iProductRepository.findProductCategory(category);
        if (findProduct.isEmpty()){
            throw new EntityNotfoundException("There are no products registered for this category of products");
        }else{
            return iProductRepository.findProductCategory(category);
        }
    }
    public Product updateProduct(Long id, String nProduct, BigDecimal value, String image){
        Product produtcId = findById(id);
        var type = iTypeRepository.findByTypeid(produtcId.getNometype().getTypeid());
        var category = iCategoryRepository.findByCategoryid(produtcId.getProductcategory().getCategoryid());
        produtcId.setNomeproduct(nProduct);
        produtcId.setValor(value);
        produtcId.setImage(image);
        produtcId.setNometype(type);
        produtcId.setProductcategory(category);
        iProductRepository.save(produtcId);
        return produtcId;
    }

    public void destroyProduct(Long id){
       iProductRepository.delete(iProductRepository.findByProductid(id).orElseThrow(() ->
               new EntityNotfoundException(String.format("this product id: {%s} not exists",id))));
    }
}
