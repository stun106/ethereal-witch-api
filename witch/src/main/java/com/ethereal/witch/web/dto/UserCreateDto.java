package com.ethereal.witch.web.dto;
import com.ethereal.witch.models.user.AccessUser;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Validated
public class UserCreateDto {
    @Schema(description = "UserCreateDto", example = "José Antonio Bispo dos Santos Junior", type = "string",pattern = "")
    @NotBlank
    @Pattern(regexp = "^(?:[\\p{Lu}&&[\\p{IsLatin}]])" +
            "(?:(?:')?(?:[\\p{Ll}&&[\\p{IsLatin}]]))" +
            "+(?:\\-(?:[\\p{Lu}&&[\\p{IsLatin}]])" +
            "(?:(?:')?(?:[\\p{Ll}&&[\\p{IsLatin}]]))+)" +
            "*(?: (?:(?:e|y|de(?:(?: la| las| lo| los))" +
            "?|do|dos|da|das|del|van|von|bin|le) )" +
            "?(?:(?:(?:d'|D'|O'|Mc|Mac|al\\-))" +
            "?(?:[\\p{Lu}&&[\\p{IsLatin}]])" +
            "(?:(?:')?" +
            "(?:[\\p{Ll}&&[\\p{IsLatin}]]))" +
            "+|(?:[\\p{Lu}&&[\\p{IsLatin}]])(?:(?:')?" +
            "(?:[\\p{Ll}&&[\\p{IsLatin}]]))+" +
            "(?:\\-(?:[\\p{Lu}&&[\\p{IsLatin}]])" +
            "(?:(?:')?(?:[\\p{Ll}&&[\\p{IsLatin}]]))+)*))+" +
            "(?: (?:Jr\\.|II|III|IV))?$"
            ,message = "Enter your first and last name to validate this field")
    private String name;
    @NotBlank
    @Schema(description = "UserCreateDto", example = "stun106", type = "string",pattern = "")
    private String username;
    @NotBlank
    @Schema(description = "UserCreateDto", example = "@Ethereal123", type = "string",pattern = "")
    @Pattern(regexp = "^(?=.*[@#$%])(?=.*[A-Z])(?=.*[0-9])[A-Za-z0-9@#$%]{8,}$", message = "Your password must have 8 digits, a special character, an uppercase letter and a number to be valid.")
    private String password;
}
