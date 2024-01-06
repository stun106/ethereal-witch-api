package com.ethereal.witch.web.dto;

import com.ethereal.witch.models.collection.Category;
import com.ethereal.witch.models.product_type.TypeProduct;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductCreateDto {
        @NotBlank
        String nomeproduct;
        @NotNull
        BigDecimal valor;
        @NotNull
        String image;
        TypeProduct nometype;
        Category productcategory;
}
