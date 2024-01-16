package com.ethereal.witch.web.controllers;

import com.ethereal.witch.models.product_type.TypeProduct;
import com.ethereal.witch.service.TypeService;
import com.ethereal.witch.web.dto.TypeCreateDto;
import com.ethereal.witch.web.dto.TypeResponseDto;
import com.ethereal.witch.web.dto.mapper.TypeMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/witch/type/")
public class TypeController {
    private final TypeService typeService;

    @PostMapping("/product/auth")
    public ResponseEntity create(@RequestBody @Valid TypeCreateDto typeDto, HttpServletRequest request){
        typeService.saveType(TypeMapper.toType(typeDto),request);
        Map<String,String> msg = new HashMap<>();
                msg.put("msg", typeDto.getTypename() + " adicionado com sucesso!");
        return ResponseEntity.status(HttpStatus.CREATED).body(msg);
    }
    @GetMapping("/all")
    public ResponseEntity<List<TypeResponseDto>> findAll(){
        List<TypeProduct> types = typeService.findAllTypes();
        return ResponseEntity.status(HttpStatus.OK).body(TypeMapper.listTypes(types));
    }
    @GetMapping("/product/{id}")
    public ResponseEntity<TypeResponseDto> findId (@PathVariable("id") Long id){
       TypeProduct type = typeService.findTypeId(id);
        return ResponseEntity.status(HttpStatus.OK).body(TypeMapper.toDto(type));
    }
    @PutMapping("/change/{id}/auth")
    public ResponseEntity<Map<String,String>> update (@PathVariable("id") Long id,
                                  @RequestBody @Valid TypeCreateDto typedto, HttpServletRequest request){
        String typeOld = typeService.findTypeId(id).getTypename();
        TypeProduct type = typeService.updateType(id,typedto.getTypename(),request);
        Map<String, String> flashmsg = new HashMap<>();
        flashmsg.put("msg", "De " + typeOld + " foi alterado para " + type.getTypename() + ".");
        return ResponseEntity.status(HttpStatus.OK).body(flashmsg);
    }

    @DeleteMapping("/del/{id}/auth")
    public ResponseEntity<Map<String,String>> destroy (@PathVariable("id") Long id, HttpServletRequest request){
        TypeProduct type = typeService.findTypeId(id);
        typeService.destroyType(id,request);
        Map<String, String> flashmsg = new HashMap<>();
        flashmsg.put("msg", type.getTypename() + " Deletado com sucesso!");
        return ResponseEntity.status(HttpStatus.OK).body(flashmsg);
    }
}
