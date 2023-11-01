package com.ethereal.witch.models.product_type;

import jakarta.validation.constraints.NotBlank;

public record TypeProductRecordDto(@NotBlank String typename) {
}
