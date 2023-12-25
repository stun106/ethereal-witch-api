package com.ethereal.witch.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserPassword {
    private String currentPassword;
    @NotBlank
    @Pattern(regexp = "^(?=.*[@#$%])(?=.*[A-Z])(?=.*[0-9])[A-Za-z0-9@#$%]{8,}$", message = "Your password must have 8 digits, a special character, an uppercase letter and a number to be valid.")
    @Size(min = 8, max = 8)
    private String newPassword;
    @NotBlank
    @Pattern(regexp = "^(?=.*[@#$%])(?=.*[A-Z])(?=.*[0-9])[A-Za-z0-9@#$%]{8,}$", message = "Your password must have 8 digits, a special character, an uppercase letter and a number to be valid.")
    @Size(min = 8, max = 8)
    private String confirmPassword;
}
