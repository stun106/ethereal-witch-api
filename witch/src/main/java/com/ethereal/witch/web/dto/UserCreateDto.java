package com.ethereal.witch.web.dto;

import com.ethereal.witch.models.user.AccessUser;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserCreateDto {
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
    private String username;
    @NotBlank
    @Pattern(regexp = "^(?=.*[@#$%])(?=.*[A-Z])(?=.*[0-9])[A-Za-z0-9@#$%]{8,}$", message = "Your password must have 8 digits, a special character, an uppercase letter and a number to be valid.")
    private String password;
    private AccessUser access = AccessUser.USER;

}
