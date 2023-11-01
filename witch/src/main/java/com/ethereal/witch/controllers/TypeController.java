package com.ethereal.witch.controllers;

import com.ethereal.witch.interfaces.ITypeRepository;
import com.ethereal.witch.models.product.Product;
import com.ethereal.witch.models.product_type.TypeProduct;
import com.ethereal.witch.models.product_type.TypeProductRecordDto;
import com.fasterxml.jackson.databind.ObjectReader;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/witch/type/")
public class TypeController {
    @Autowired
    private ITypeRepository iTypeRepository;

    @PostMapping("/product/auth")
    public ResponseEntity create(@RequestBody @Valid TypeProductRecordDto typeDto){
        var type = new TypeProduct();
        BeanUtils.copyProperties(typeDto,type);
        var typeProduct = this.iTypeRepository.findByTypeid(type.getTypeid());
        if (typeProduct != null){
            Map<String,String> msg =  new HashMap<>();
                    msg.put("error","Algo deu errado, verifique seus dados!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg);
        }
        this.iTypeRepository.save(type);
        Map<String,String> msg = new HashMap<>();
                msg.put("msg", type.getTypename() + "adicionado com sucesso!");
        return ResponseEntity.status(HttpStatus.CREATED).body(msg);
    }
    @GetMapping("/product/")
    public ResponseEntity<TypeProduct> findId(@RequestParam("typeId") Long id){
        var type = iTypeRepository.findByTypeid(id);
        return ResponseEntity.status(HttpStatus.OK).body(type);
    }

}
