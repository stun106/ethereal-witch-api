package com.ethereal.witch.web.dto.mapper;

import com.ethereal.witch.models.product.Product;
import com.ethereal.witch.web.dto.ProductCreateDto;
import com.ethereal.witch.web.dto.ProductResponseDto;
import org.modelmapper.ModelMapper;

public class ProductMapper {
    public static Product toProduct(ProductCreateDto productCreateDto){
        return new ModelMapper().map(productCreateDto, Product.class);
    }
    public static ProductResponseDto toProductDto(Product product){
        return new ModelMapper().map(product, ProductResponseDto.class);
    }
}
