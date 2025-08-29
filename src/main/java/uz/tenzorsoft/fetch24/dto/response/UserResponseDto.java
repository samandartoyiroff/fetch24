package uz.tenzorsoft.fetch24.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import uz.tenzorsoft.fetch24.model.RoleName;

@Setter
@Getter
@ToString
public class UserResponseDto {

    private Long id;

    private String fullName;

    private String email;

    private String username;

    private RoleName roleName;

}
