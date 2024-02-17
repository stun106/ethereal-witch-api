package com.ethereal.witch.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class
ProductResponseDto {
    String nomeproduct;
    BigDecimal valor;
    String image;
    LocalDate createdat;
}
