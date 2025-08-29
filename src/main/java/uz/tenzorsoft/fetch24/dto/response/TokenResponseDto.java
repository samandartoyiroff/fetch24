package uz.tenzorsoft.fetch24.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class TokenResponseDto {

    private String accessToken;
    private String refreshToken;
    private String role;

}
