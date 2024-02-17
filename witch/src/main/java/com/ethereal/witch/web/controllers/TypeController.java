package com.ethereal.witch.web.controllers;

import com.ethereal.witch.models.product_type.TypeProduct;
import com.ethereal.witch.service.TypeService;
import com.ethereal.witch.web.dto.ProductResponseDto;
import com.ethereal.witch.web.dto.TypeCreateDto;
import com.ethereal.witch.web.dto.TypeResponseDto;
import com.ethereal.witch.web.dto.mapper.TypeMapper;
import com.ethereal.witch.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Type", description = "Contains all the information related to type products, register, editing and reading a type.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/witch/type/")
public class TypeController {
    private final TypeService typeService;
    @Operation(summary = "Creates a new resource.", description = "Resource for created a new type.",
            responses = {@ApiResponse(responseCode = "201", description = "Resourse created sucessfully.",
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = ProductResponseDto.class))),
                    @ApiResponse(responseCode = "401",description = "Need authentication.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403",description = "Requires authorization.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "400",description = "Password invalid.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PostMapping("/product/auth")
    public ResponseEntity create(@RequestBody @Valid TypeCreateDto typeDto, HttpServletRequest request){
        typeService.saveType(TypeMapper.toType(typeDto),request);
        Map<String,String> msg = new HashMap<>();
                msg.put("msg", typeDto.getTypename() + " adicionado com sucesso!");
        return ResponseEntity.status(HttpStatus.CREATED).body(msg);
    }
    @Operation(summary = "Returns resource by id.", description = "Resource for return all types.",
            responses = {@ApiResponse(responseCode = "201", description = "Type returns successfully.",
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = ProductResponseDto.class))),
                    @ApiResponse(responseCode = "404",description = "Type not found.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @GetMapping("/all")
    public ResponseEntity<List<TypeResponseDto>> findAll(){
        List<TypeProduct> types = typeService.findAllTypes();
        return ResponseEntity.status(HttpStatus.OK).body(TypeMapper.listTypes(types));
    }
    @Operation(summary = "Returns resource by id.", description = "Resource for return type by id.",
            responses = {@ApiResponse(responseCode = "201", description = "Type returns successfully.",
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = ProductResponseDto.class))),
                    @ApiResponse(responseCode = "404",description = "Type not found.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @GetMapping("/product/{id}")
    public ResponseEntity<TypeResponseDto> findId (@PathVariable("id") Long id){
       TypeProduct type = typeService.findTypeId(id);
        return ResponseEntity.status(HttpStatus.OK).body(TypeMapper.toDto(type));
    }
    @Operation(summary = "Changes resource type.", description = "Resource for changer type.",
            responses = {@ApiResponse(responseCode = "201", description = "Resource change successfully.",
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = Void.class))),
                    @ApiResponse(responseCode = "404",description = "Resource not found.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "401",description = "Need authentication.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403",description = "Requires authorization.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "400",description = "Password invalid.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PutMapping("/change/{id}/auth")
    public ResponseEntity<Map<String,String>> update (@PathVariable("id") Long id,
                                  @RequestBody @Valid TypeCreateDto typedto, HttpServletRequest request){
        String typeOld = typeService.findTypeId(id).getTypename();
        TypeProduct type = typeService.updateType(id,typedto.getTypename(),request);
        Map<String, String> flashmsg = new HashMap<>();
        flashmsg.put("msg", "De " + typeOld + " foi alterado para " + type.getTypename() + ".");
        return ResponseEntity.status(HttpStatus.OK).body(flashmsg);
    }
    @Operation(summary = "Delete resource by id.", description = "Resource for delete type.",
            responses = {@ApiResponse(responseCode = "201", description = "Resourse deleted sucessfully.",
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = ProductResponseDto.class))),
                    @ApiResponse(responseCode = "400",description = "Password invalid.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "401",description = "Need authentication.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403",description = "Requires authorization.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404",description = "Resources not found.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @DeleteMapping("/del/{id}/auth")
    public ResponseEntity<Map<String,String>> destroy (@PathVariable("id") Long id, HttpServletRequest request){
        TypeProduct type = typeService.findTypeId(id);
        typeService.destroyType(id,request);
        Map<String, String> flashmsg = new HashMap<>();
        flashmsg.put("msg", type.getTypename() + " Deletado com sucesso!");
        return ResponseEntity.status(HttpStatus.OK).body(flashmsg);
    }
}
