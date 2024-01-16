package com.ethereal.witch.web.dto.mapper;

import com.ethereal.witch.models.collection.Category;
import com.ethereal.witch.web.dto.CategoryCreateDto;
import com.ethereal.witch.web.dto.CategoryResponseDto;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class CategoryMapper {
    public static Category toCategory (CategoryCreateDto categoryCreateDto){
        return new ModelMapper().map(categoryCreateDto,Category.class);
    }
    public static CategoryResponseDto toDto (Category category){
        return new ModelMapper().map(category,CategoryResponseDto.class);
    }
    public static List<CategoryResponseDto> toListCategory (List<Category> categories){
        return categories.stream().map(CategoryMapper::toDto).collect(Collectors.toList());
    }
}
