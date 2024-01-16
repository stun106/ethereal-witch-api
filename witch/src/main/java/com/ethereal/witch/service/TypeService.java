package com.ethereal.witch.service;

import com.ethereal.witch.models.product_type.TypeProduct;
import com.ethereal.witch.models.user.AccessUser;
import com.ethereal.witch.repository.ITypeRepository;
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
public class TypeService {
    private final ITypeRepository iTypeRepository;
    private final IUserRepository iUserRepository;

    private AccessUser getUserRole(HttpServletRequest request){
        return iUserRepository.findByid((Long) request.getAttribute("idUser")).getAccess();
    }
    @Transactional
    public TypeProduct saveType(TypeProduct typeProduct, HttpServletRequest request){
        TypeProduct type = iTypeRepository.findByTypename(typeProduct.getTypename());
        if (type != null){
            throw new UniqueViolationExeception(String.format("Type {%s} allready exists", typeProduct.getTypename()));
        } else if (getUserRole(request) != AccessUser.ADMIN) {
            throw new UnnauthorizedException("Requires authorization! Please contact the developer." +
                    " Email: antoniojr.strong@gmail.com");
        }else {
            return iTypeRepository.save(typeProduct);
        }
    }
    @Transactional(readOnly = true)
    public List<TypeProduct> findAllTypes(){
        List<TypeProduct> types = iTypeRepository.findAll();
        if (types.isEmpty()){
            throw new EntityNotfoundException("Types not found.");
        }else{
            return types;
        }
    }
    @Transactional(readOnly = true)
    public TypeProduct findTypeId(Long id){
        return iTypeRepository.findById(id)
                .orElseThrow(() -> new EntityNotfoundException(String.format("Type id {%s} not found", id)));
    }
    @Transactional
    public TypeProduct updateType(Long id, String newType, HttpServletRequest request){
        if (getUserRole(request) != AccessUser.ADMIN){
            throw new UnnauthorizedException("Requires authorization! Please contact the developer." +
                    " Email: antoniojr.strong@gmail.com");
        }
        TypeProduct type = findTypeId(id);
        List<TypeProduct> types = findAllTypes();
        List<String> names = types.stream().map(TypeProduct::getTypename).toList();
        names.forEach(typeNames -> {
            if (typeNames.equals(newType)) {
                throw new UniqueViolationExeception(String.format("Type {%s} allready exists", newType));
            }
        });
        type.setTypename(newType);
        return iTypeRepository.save(type);
    }
    @Transactional
    public void destroyType(Long id,HttpServletRequest request){
        if (getUserRole(request) != AccessUser.ADMIN){
            throw new UnnauthorizedException("Requires authorization! Please contact the developer." +
                    " Email: antoniojr.strong@gmail.com");
        }
        iTypeRepository.delete(findTypeId(id));
    }
}
