package org.example.authentication.service;

import org.example.authentication.request.RegisterRequest;
import org.example.domain.entity.Owner;
import org.example.repository.OwnerRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    private final OwnerRepository ownerRepository;
    private final PasswordEncoder passwordEncoder;

    public RegistrationService(OwnerRepository ownerRepository, PasswordEncoder passwordEncoder) {
        this.ownerRepository = ownerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Owner registerOwner(RegisterRequest registerRequest) {
        Owner user = new Owner();
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setName(registerRequest.getName());

        return ownerRepository.save(user);
    }
}
