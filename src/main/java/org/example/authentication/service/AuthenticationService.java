package org.example.authentication.service;

import lombok.RequiredArgsConstructor;
import org.example.authentication.util.JwtService;
import org.example.domain.dto.OwnerLoginRequestDto;
import org.example.domain.dto.OwnerLoginResponseDto;
import org.example.domain.entity.Owner;
import org.example.exception.OwnerInvalidCredentialsException;
import org.example.repository.OwnerRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private static final String INVALID_CREDENTIALS_MESSAGE = "Invalid login credentials";
    private final JwtService jwtService;
    private final OwnerRepository ownerRepository;
    private final PasswordEncoder passwordEncoder;

    public OwnerLoginResponseDto login(OwnerLoginRequestDto ownerLoginRequestDto) {
        Owner owner = ownerRepository.findByEmail(ownerLoginRequestDto.getEmail())
                .orElseThrow(() -> new OwnerInvalidCredentialsException(INVALID_CREDENTIALS_MESSAGE));

        boolean passwordsMatch = passwordEncoder.matches(ownerLoginRequestDto.getPassword(), owner.getPassword());
        if (!passwordsMatch) {
            throw new OwnerInvalidCredentialsException(INVALID_CREDENTIALS_MESSAGE);
        }

        String token = jwtService.generateToken(ownerLoginRequestDto.getEmail());
        return new OwnerLoginResponseDto(token);
    }
}
