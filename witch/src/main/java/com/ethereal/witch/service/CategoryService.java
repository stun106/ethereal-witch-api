package com.ethereal.witch.service;

import com.ethereal.witch.models.collection.Category;
import com.ethereal.witch.models.user.AccessUser;
import com.ethereal.witch.repository.ICategoryRepository;
import com.ethereal.witch.repository.IUserRepository;
import com.ethereal.witch.service.exception.EntityNotfoundException;
import com.ethereal.witch.service.exception.UniqueViolationExeception;
import com.ethereal.witch.service.exception.UnnauthorizedException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final ICategoryRepository iCategoryRepository;
    private final IUserRepository iUserRepository;
    private AccessUser getUserRole(HttpServletRequest request){
        return iUserRepository.findByid((Long) request.getAttribute("idUser")).getAccess();
    }
    @Transactional
    public Category saveCategory (Category category, HttpServletRequest request){
        Category newCategory = iCategoryRepository.findByNomecategory(category.getNomecategory());
        if (newCategory != null){
            throw new UniqueViolationExeception(String.format("Category {%s} allready exists",category.getNomecategory()));
        }else if (getUserRole(request) != AccessUser.ADMIN){
            throw new UnnauthorizedException("Requires authorization! Please contact the developer." +
                    " Email: antoniojr.strong@gmail.com");
        }else{
            return iCategoryRepository.save(category);
        }
    }
    @Transactional(readOnly = true)
    public Category categoryForId (Long id) {
        return iCategoryRepository.findByCategoryid(id).orElseThrow(() ->
                new EntityNotfoundException(String.format("Category id {%s} not found", id)));
    }
    @Transactional(readOnly = true)
    public List<Category> findAllCategory(){
        return iCategoryRepository.findAll();
    }
    @Transactional
    public Category updateCategory (Long id, String nCategory, HttpServletRequest request){
        if (getUserRole(request) != AccessUser.ADMIN){
            throw new UnnauthorizedException("Requires authorization! Please contact the developer." +
                    " Email: antoniojr.strong@gmail.com");
        }
        Category category = categoryForId(id);
        List<String> names = findAllCategory()
                .stream().map(Category::getNomecategory).toList();
        names.forEach(categoryNames -> {
            if (categoryNames.equals(nCategory)){
                throw new UniqueViolationExeception(String.format("Category {%s} allready exists",nCategory));
            }
        });
        category.setNomecategory(nCategory);
        return iCategoryRepository.save(category);
    }
    @Transactional
    public void destroyCategory (Long id, HttpServletRequest request){
        if (getUserRole(request) != AccessUser.ADMIN) {
            throw new UnnauthorizedException("Requires authorization! Please contact the developer." +
                    " Email: antoniojr.strong@gmail.com");
        }
        iCategoryRepository.delete(categoryForId(id));
    }
}
