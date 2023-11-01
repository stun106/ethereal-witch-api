package com.ethereal.witch.models.collection;

import jakarta.validation.constraints.NotBlank;

public record CategoryRecordDto(@NotBlank String nomecategory) {
}
