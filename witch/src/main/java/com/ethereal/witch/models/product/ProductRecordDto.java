package com.ethereal.witch.models.product;

import com.ethereal.witch.models.collection.Category;
import com.ethereal.witch.models.collection.CategoryRecordDto;
import com.ethereal.witch.models.product_type.TypeProduct;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductRecordDto(
        @NotBlank String nomeproduct,
        @NotNull BigDecimal valor,
        @NotNull String image,
        TypeProduct nometype,
        Category productcategory
) {
}
