package com.ethereal.witch.service;

import com.ethereal.witch.models.collection.Category;
import com.ethereal.witch.models.product.Product;
import com.ethereal.witch.models.product_type.TypeProduct;
import com.ethereal.witch.models.user.AccessUser;
import com.ethereal.witch.repository.IProductRepository;
import com.ethereal.witch.repository.ITypeRepository;
import com.ethereal.witch.repository.IUserRepository;
import com.ethereal.witch.service.exception.EntityNotfoundException;
import com.ethereal.witch.service.exception.UniqueViolationExeception;
import com.ethereal.witch.service.exception.UnnauthorizedException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final IProductRepository iProductRepository;
    private final CategoryService categoryService;
    private final ITypeRepository iTypeRepository;
    private final IUserRepository iUserRepository;

    private AccessUser getUserRole(HttpServletRequest request){
        return iUserRepository.findByid((Long) request.getAttribute("idUser")).getAccess();
    }
    @Transactional
    public Product saveProduct (Product product,HttpServletRequest request){
        Product newProduct = iProductRepository.findByNomeproduct(product.getNomeproduct());
        if (getUserRole(request) != AccessUser.ADMIN) {
            throw new UnnauthorizedException("Requires authorization! Please contact the developer." +
                    " Email: antoniojr.strong@gmail.com");
        }else if(newProduct != null){
            throw new UniqueViolationExeception(String.format("Product {%s} Already created.",product.getNomeproduct()));
        }else{
        return iProductRepository.save(product);
        }
    }
    @Transactional(readOnly = true)
    public Product findById(Long id){
        return iProductRepository.findByProductid(id).orElseThrow(()->
                new EntityNotfoundException(String.format("Product with id: {%s} not exists.",id)));
    }
    @Transactional(readOnly = true)
    public List<Object[]> findAllForType(String type){
        List<Object[]> findProduct = iProductRepository.findProductByType(type);
        if (findProduct.isEmpty()){
            throw new EntityNotfoundException("There are no products registered for this type of products.");
        }else{
            return iProductRepository.findProductByType(type);
        }
    }
    @Transactional(readOnly = true)
    public List<Object[]> findAllForCategory(String category){
        List<Object[]> findProduct = iProductRepository.findProductCategory(category);
        if (findProduct.isEmpty()){
            throw new EntityNotfoundException("There are no products registered for this category of products");
        }else{
            return iProductRepository.findProductCategory(category);
        }
    }
    @Transactional
    public Product updateProduct(Long id, String nProduct, BigDecimal value, String image,HttpServletRequest request){
        if(getUserRole(request) != AccessUser.ADMIN){
            throw new UnnauthorizedException("Requires authorization! Please contact the developer." +
                    " Email: antoniojr.strong@gmail.com");
        }
        Product produtcId = findById(id);
        TypeProduct type = iTypeRepository.findByTypeid(produtcId.getNometype().getTypeid());
        Category category = categoryService.categoryForId(produtcId.getProductcategory().getCategoryid());
        produtcId.setNomeproduct(nProduct);
        produtcId.setValor(value);
        produtcId.setImage(image);
        produtcId.setNometype(type);
        produtcId.setProductcategory(category);
        iProductRepository.save(produtcId);
        return produtcId;
    }
    @Transactional
    public void destroyProduct(Long id, HttpServletRequest request){
       if (getUserRole(request) != AccessUser.ADMIN){
           throw new UnnauthorizedException("Requires authorization! Please contact the developer." +
                   " Email: antoniojr.strong@gmail.com");
       }
       iProductRepository.delete(iProductRepository.findByProductid(id).orElseThrow(() ->
               new EntityNotfoundException(String.format("this product id: {%s} not exists",id))));
    }
}
