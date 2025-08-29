package uz.tenzorsoft.fetch24.mapper;

import org.springframework.stereotype.Component;
import uz.tenzorsoft.fetch24.domain.User;
import uz.tenzorsoft.fetch24.dto.response.UserResponseDto;

@Component
public class UserMapper {

    public UserResponseDto toDto(User user) {

        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(user.getId());
        userResponseDto.setUsername(user.getUsername());
        userResponseDto.setEmail(user.getEmail());
        userResponseDto.setFullName(user.getFullName());
        userResponseDto.setRoleName(user.getRole().getRoleName());
        return userResponseDto;

    }

}
