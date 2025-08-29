package uz.tenzorsoft.fetch24.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.tenzorsoft.fetch24.domain.User;
import uz.tenzorsoft.fetch24.dto.request.LoginDto;
import uz.tenzorsoft.fetch24.dto.request.ResetPasswordDto;
import uz.tenzorsoft.fetch24.dto.response.TokenResponseDto;
import uz.tenzorsoft.fetch24.mapper.UserMapper;
import uz.tenzorsoft.fetch24.repository.UserRepository;
import uz.tenzorsoft.fetch24.security.JwtUtil;
import uz.tenzorsoft.fetch24.service.AuthService;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final UserMapper userMapper;

    @Override
    public ResponseEntity<?> login(LoginDto loginDto) {

        User user = userRepository.findByUsername(loginDto.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found "+loginDto.getUsername()));

        if (passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {

            TokenResponseDto  tokenResponseDto = new TokenResponseDto();

            tokenResponseDto.setAccessToken(jwtUtil.generateAccessToken(loginDto.getUsername()));

            tokenResponseDto.setRefreshToken(jwtUtil.generateRefreshToken(loginDto.getUsername()));

            tokenResponseDto.setRole(user.getRole().getRoleName().name());

            return new ResponseEntity<>(tokenResponseDto, HttpStatus.OK);

        }
        else  {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Password is not match");
        }


    }

    @Transactional
    @Override
    public ResponseEntity<?> resetPassword(ResetPasswordDto resetPasswordDto) {

        User user = userRepository.findByUsername(resetPasswordDto.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found " + resetPasswordDto.getUsername()));

        if (passwordEncoder.matches(resetPasswordDto.getOldPassword(), user.getPassword())) {

            if (resetPasswordDto.getNewPassword().length()>=6){

                user.setPassword(passwordEncoder.encode(resetPasswordDto.getNewPassword()));

                User save = userRepository.save(user);

                return ResponseEntity.ok(userMapper.toDto(save));

            }
            else {
                throw new RuntimeException("New password must be at least 6 characters");
            }
        }
        else  {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Password is not match");
        }

    }
}
