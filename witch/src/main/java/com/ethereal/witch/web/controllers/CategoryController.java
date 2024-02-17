package com.ethereal.witch.web.controllers;

import com.ethereal.witch.repository.ICategoryRepository;
import com.ethereal.witch.repository.IUserRepository;
import com.ethereal.witch.models.collection.Category;
import com.ethereal.witch.service.CategoryService;
import com.ethereal.witch.web.dto.CategoryCreateDto;
import com.ethereal.witch.web.dto.CategoryResponseDto;
import com.ethereal.witch.web.dto.ProductResponseDto;
import com.ethereal.witch.web.dto.UserResponseDto;
import com.ethereal.witch.web.dto.mapper.CategoryMapper;
import com.ethereal.witch.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
@Tag(name = "Category", description = "Contains all the information related Category to product, register, editing and reading a category.")
@RestController
@RequestMapping("/witch/collection/")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    @Operation(summary = "Creates a new resource.", description = "Resource for created a new category.",
            responses = {@ApiResponse(responseCode = "201", description = "Resourse created sucessfully.",
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = ProductResponseDto.class))),
                    @ApiResponse(responseCode = "400",description = "Password invalid.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "401",description = "Need authentication.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403",description = "Requires authorization.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            })

    @PostMapping("/create/auth")
    public ResponseEntity<Map<String,String>> create(@RequestBody  @Valid CategoryCreateDto categoryDto,
                                                     HttpServletRequest request){
        categoryService.saveCategory(CategoryMapper.toCategory(categoryDto),request);
        Map<String,String> flashMsg = new HashMap<>();
        flashMsg.put("msg", categoryDto.getNomecategory() + " created successfuly!");
        return ResponseEntity.status(HttpStatus.CREATED).body(flashMsg);
    }
    @Operation(summary = "Returns resource by id.", description = "Resource for return category by id.",
            responses = {@ApiResponse(responseCode = "201", description = "Product returns successfully.",
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = ProductResponseDto.class))),
                    @ApiResponse(responseCode = "404",description = "Category not found.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @GetMapping("/single/{id}")
    public ResponseEntity<CategoryResponseDto> findById(@PathVariable("id") Long id){
        Category categoryId = categoryService.categoryForId(id);
        return ResponseEntity.status(HttpStatus.OK).body(CategoryMapper.toDto(categoryId));
    }
    @Operation(summary = "Returns all category.", description = "Resource for returns all category.",
            responses = {@ApiResponse(responseCode = "201", description = "Category returns sucessfully.",
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = UserResponseDto.class))),
                    @ApiResponse(responseCode = "404",description = "Category not found.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            })
    @GetMapping("/all")
    public ResponseEntity<List<CategoryResponseDto>> index() {
        List<Category> allCategory = this.categoryService.findAllCategory();
        return ResponseEntity.status(HttpStatus.OK).body(CategoryMapper.toListCategory(allCategory));
    }
    @Operation(summary = "Changes resource category.", description = "Resource for changer category.",
            responses = {@ApiResponse(responseCode = "201", description = "Resource change successfully.",
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = Void.class))),
                    @ApiResponse(responseCode = "400",description = "Password invalid.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "401",description = "Need authentication.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403",description = "Requires authorization.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404",description = "Resource not found.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PutMapping("/change/{id}/auth")
    public ResponseEntity<Map<String,String>> update(@PathVariable("id") Long id, @RequestBody CategoryCreateDto categoryDto, HttpServletRequest request){
        Category cate = categoryService.categoryForId(id);
        String nomeCateBefore = cate.getNomecategory();
        categoryService.updateCategory(id,categoryDto.getNomecategory(),request);
        Map<String, String> flashmsg = new HashMap<>();
        flashmsg.put("msg", "Category: " +  nomeCateBefore + " updated to  " + cate.getNomecategory() + ".");
        return ResponseEntity.status(HttpStatus.OK).body(flashmsg);
    }
    @Operation(summary = "Delete resource by id.", description = "Resource for delete category.",
            responses = {@ApiResponse(responseCode = "201", description = "Resource deleted successfully.",
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = ProductResponseDto.class))),
                    @ApiResponse(responseCode = "400",description = "Password invalid.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "401",description = "Need authentication.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403",description = "Requires authorization.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404",description = "Resource not found.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @DeleteMapping("/del/{id}/auth")
    public ResponseEntity<Map<String,String>> destroy(@PathVariable("id") Long id, HttpServletRequest request){
        Category category = categoryService.categoryForId(id);
        categoryService.destroyCategory(id,request);
        Map<String, String> flashmsg = new HashMap<>();
        flashmsg.put("msg", "Category " + category.getNomecategory() + " deleted successfully!");
        return ResponseEntity.status(HttpStatus.OK).body(flashmsg);
    }

}
