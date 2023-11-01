package com.ethereal.witch.models.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserRecordDto(@NotNull String name, @NotBlank String username, @NotBlank String password, @NotNull AccessUser access) {
}
