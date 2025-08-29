package uz.tenzorsoft.fetch24.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

}
