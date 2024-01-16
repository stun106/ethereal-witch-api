package com.ethereal.witch.web.dto.mapper;

import com.ethereal.witch.models.product_type.TypeProduct;
import com.ethereal.witch.web.dto.TypeCreateDto;
import com.ethereal.witch.web.dto.TypeResponseDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.bytebuddy.matcher.StringMatcher;

import java.util.List;
import java.util.stream.Collectors;

public class TypeMapper {
    public static TypeProduct toType(TypeCreateDto typeCreateDto){
        return new ModelMapper().map(typeCreateDto,TypeProduct.class);
    }
    public static TypeResponseDto toDto(TypeProduct typeProduct){
        return new ModelMapper().map(typeProduct,TypeResponseDto.class);
    }
    public static List<TypeResponseDto> listTypes(List<TypeProduct> typeProducts){
        return typeProducts.stream().map(TypeMapper::toDto).collect(Collectors.toList());
    }
}
