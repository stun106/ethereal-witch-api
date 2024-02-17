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
import com.ethereal.witch.web.dto.UserResponseDto;
import com.ethereal.witch.web.dto.mapper.ProductMapper;
import com.ethereal.witch.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Product", description = "Contains all the information related to Product, register, editing and reading a product.")
@RestController
@RequestMapping("/witch/product")
@AllArgsConstructor
public class ProductController {
    private final CategoryService categoryService;
    private final TypeService typeService;
    private final ProductService productService;
    @Operation(summary = "Creates a new resource.", description = "Resource for created a new product.",
            responses = {@ApiResponse(responseCode = "201", description = "Resourse created sucessfully.",
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = ProductResponseDto.class))),
                    @ApiResponse(responseCode = "401",description = "Need authentication.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403",description = "Requires authorization.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "400",description = "Password invalid.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PostMapping("/create/auth")
    public ResponseEntity<ProductResponseDto> create(@Valid @RequestBody ProductCreateDto productDto, HttpServletRequest request) {
        Category productCategory = categoryService.categoryForId(productDto.getProductcategory().getCategoryid());
        TypeProduct typeProduct = typeService.findTypeId(productDto.getNometype().getTypeid());
        productDto.setNometype(typeProduct);
        productDto.setProductcategory(productCategory);
        Product product = productService.saveProduct(ProductMapper.toProduct(productDto),request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ProductMapper.toProductDto(product));
    }
    @Operation(summary = "Returns resource by id.", description = "Resource for return product by id.",
            responses = {@ApiResponse(responseCode = "201", description = "Product returns successfully.",
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = ProductResponseDto.class))),
                    @ApiResponse(responseCode = "404",description = "Product not found.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })

    @GetMapping("/single/{id}")
    public ResponseEntity<ProductResponseDto> findId(@PathVariable("id") Long id) {
       Product product = productService.findById(id);
       return ResponseEntity.status(HttpStatus.OK).body(ProductMapper.toProductDto(product));
    }
    @Operation(summary = "Return recourse by type.", description = "Resource for return Product by type.",
            responses = {@ApiResponse(responseCode = "201", description = "Product returns successfuly.",
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = ProductResponseDto.class))),
                    @ApiResponse(responseCode = "404",description = "Product in type not exists.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            })

    @GetMapping("/type/{nome}")
    public ResponseEntity<List<Object[]>> findProductByType(@PathVariable("nome") String nome){
       List<Object[]> allProduct = productService.findAllForType(nome);
        return ResponseEntity.status(HttpStatus.OK).body(allProduct);
    }
    @Operation(summary = "Return recourse by category.", description = "Resource for return Product by category.",
            responses = {@ApiResponse(responseCode = "201", description = "Product returns successfuly.",
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = ProductResponseDto.class))),
                    @ApiResponse(responseCode = "404",description = "Product in category not exists.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            })
    @GetMapping("/collection/{nome}")
    public ResponseEntity<List<Object[]>> findProductCat(@PathVariable("nome") String ncat){
        List<Object[]> product = productService.findAllForCategory(ncat) ;
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }
    @Operation(summary = "Changes resource product.", description = "Resource for changer Product.",
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
    public ResponseEntity<Map<String,String>> update(@PathVariable("id") Long id, @Valid @RequestBody ProductCreateDto productDto, HttpServletRequest request){
        productService.updateProduct(id,productDto.getNomeproduct(),productDto.getValor(),productDto.getImage(),request);
        Map<String, String> flashMsg = new HashMap<>();
        flashMsg.put("msg",String.format("product changed succefully for id {%d}.",id));
        return ResponseEntity.status(HttpStatus.OK).body(flashMsg);
    }
    @Operation(summary = "Delete resource by id.", description = "Resource for delete product.",
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
    public ResponseEntity<Map<String,String>> destroy(@PathVariable("id") Long id, HttpServletRequest request){
        productService.destroyProduct(id,request);
        Map<String, String> flashmsg = new HashMap<>();
        flashmsg.put("msg",String.format("Product deleted succefully for id {%d}",id));
        return ResponseEntity.status(HttpStatus.OK).body(flashmsg);
    }
}
