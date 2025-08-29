package uz.tenzorsoft.fetch24.service;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import uz.tenzorsoft.fetch24.dto.request.LoginDto;
import uz.tenzorsoft.fetch24.dto.request.ResetPasswordDto;

public interface AuthService {
    ResponseEntity<?> login(@Valid LoginDto loginDto);


    ResponseEntity<?> resetPassword(@Valid ResetPasswordDto loginDto);
}
