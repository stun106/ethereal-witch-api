package com.ethereal.witch.web.controllers;

import com.ethereal.witch.repository.ITypeRepository;
import com.ethereal.witch.repository.IUserRepository;
import com.ethereal.witch.models.product_type.TypeProduct;
import com.ethereal.witch.models.product_type.TypeProductRecordDto;
import com.ethereal.witch.models.user.AccessUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/witch/type/")
public class TypeController {
    @Autowired
    private ITypeRepository iTypeRepository;
    @Autowired
    private IUserRepository userRepository;

    @PostMapping("/product/auth")
    public ResponseEntity create(@RequestBody @Valid TypeProductRecordDto typeDto, HttpServletRequest request){
        var user = userRepository.findByid((Long) request.getAttribute("idUser"));
        var type = new TypeProduct();
        BeanUtils.copyProperties(typeDto,type);
        var typeProduct = this.iTypeRepository.findByTypeid(type.getTypeid());
        if (user.getAccess() != AccessUser.ADMIN){
            Map<String,String> flashMsg = new HashMap<>();
            flashMsg.put("error","Requer autorização! entre em contato com o desenvolvedor." +
                    " Email: antoniojr.strong@gmail.com");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(flashMsg);
        }
        this.iTypeRepository.save(type);
        Map<String,String> msg = new HashMap<>();
                msg.put("msg", type.getTypename() + " adicionado com sucesso!");
        return ResponseEntity.status(HttpStatus.CREATED).body(msg);
    }
    @GetMapping("/all")
    public ResponseEntity findAll(){
        Optional<List<TypeProduct>> type = Optional.ofNullable(iTypeRepository.findAll());
        if (type.isEmpty()){
            Map<String, String> flashmsg = new HashMap<>();
            flashmsg.put("error","Esse tipo de produto não Existe!");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(flashmsg);
        }
        return ResponseEntity.status(HttpStatus.OK).body(type);
    }
    @GetMapping("/product/")
    public ResponseEntity findId (@RequestParam("typeId") Long id){
        Optional<TypeProduct> type = Optional.ofNullable(iTypeRepository.findByTypeid(id));
        if (type.isEmpty()){
            Map<String, String> flashmsg = new HashMap<>();
            flashmsg.put("error","Esse produto não Existe!");
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(flashmsg);
        }
        return ResponseEntity.status(HttpStatus.OK).body(type);
    }
    @PutMapping("/change/{id}")
    public ResponseEntity update (@PathVariable("id") Long id,
                                  @RequestBody @Valid TypeProductRecordDto typedto){
        var type = new TypeProduct();
        BeanUtils.copyProperties(typedto,type);
        Optional<TypeProduct> typed = Optional.ofNullable(iTypeRepository.findByTypeid(id));
        if (typed.isEmpty()){
            Map<String, String> flashmsg = new HashMap<>();
            flashmsg.put("msg","Tipo de produto não existe.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(flashmsg);
        }
        var typeOld = typed.get().getTypename();
        type.setTypeid(typed.get().getTypeid());
        this.iTypeRepository.save(type);
        Map<String, String> flashmsg = new HashMap<>();
        flashmsg.put("msg", "De " + typeOld + " foi alterado para " + type.getTypename() + ".");
        return ResponseEntity.status(HttpStatus.OK).body(flashmsg);
    }

    @DeleteMapping("/del/{id}")
    public ResponseEntity destroy (@PathVariable("id") Long id){
        Optional<TypeProduct> typeO = Optional.ofNullable(this.iTypeRepository.findByTypeid(id));
        if (typeO.isEmpty()){
            Map<String, String> flashmsg = new HashMap<>();
            flashmsg.put("msg","Tipo de produto não existe.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(flashmsg);
        }
        this.iTypeRepository.delete(typeO.get());
        Map<String, String> flashmsg = new HashMap<>();
        flashmsg.put("msg", typeO.get().getTypename() + " Deletado com sucesso!");
        return ResponseEntity.status(HttpStatus.OK).body(flashmsg);
    }
}
