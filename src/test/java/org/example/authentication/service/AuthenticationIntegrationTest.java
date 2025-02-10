package org.example.authentication.service;

import org.example.authentication.util.JwtService;
import org.example.domain.dto.OwnerLoginRequestDto;
import org.example.domain.dto.OwnerLoginResponseDto;
import org.example.domain.entity.Owner;
import org.example.exception.OwnerInvalidCredentialsException;
import org.example.repository.OwnerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthenticationIntegrationTest {

    private JwtService jwtService;
    private OwnerRepository ownerRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationService authenticationService;

    @BeforeEach
    public void setUp() {
        // Create mocks for the dependencies
        jwtService = mock(JwtService.class);
        ownerRepository = mock(OwnerRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        // Instantiate the AuthenticationService with mocked dependencies
        authenticationService = new AuthenticationService(jwtService, ownerRepository, passwordEncoder);
    }

    /**
     * MethodName: login_WithValidCredentials_ReturnsNonEmptyToken
     *
     * Input:
     *   - Registered owner's email: "owner@example.com"
     *   - Provided raw password: "password123"
     *   - Stored (encoded) password: "encodedPassword"
     *   - Expected token: "mockToken"
     *
     * Expected Output:
     *   - A LoginResponse containing a non-null, non-empty token that equals "mockToken".
     */
    @Test
    public void login_WithValidCredentials_ReturnsNonEmptyToken() {
        // Arrange
        String email = "owner@example.com";
        String rawPassword = "password123";
        String encodedPassword = "encodedPassword";
        String expectedToken = "mockToken";

        // Create an Owner instance as would be retrieved from the repository
        Owner owner = new Owner();
        owner.setEmail(email);
        owner.setPassword(encodedPassword);

        // Prepare the login request DTO
        OwnerLoginRequestDto loginRequest = new OwnerLoginRequestDto();
        loginRequest.setEmail(email);
        loginRequest.setPassword(rawPassword);

        // Stub the repository, password encoder, and JWT service behavior
        when(ownerRepository.findByEmail(email)).thenReturn(Optional.of(owner));
        when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(true);
        when(jwtService.generateToken(email)).thenReturn(expectedToken);

        // Act
        OwnerLoginResponseDto response = authenticationService.login(loginRequest);

        // Assert
        assertNotNull(response, "The response should not be null.");
        assertNotNull(response.getJwtToken(), "The token should not be null.");
        assertFalse(response.getJwtToken().isEmpty(), "The token should not be empty.");
        assertEquals(expectedToken, response.getJwtToken(), "The token should match the expected value.");
    }

    /**
     * MethodName: login_WithInvalidPassword_ThrowsOwnerInvalidCredentialsException
     *
     * Input:
     *   - Registered owner's email: "owner@example.com"
     *   - Provided raw password: "wrongPassword"
     *   - Stored (encoded) password: "encodedPassword"
     *
     * Expected Output:
     *   - An OwnerInvalidCredentialsException is thrown because the passwords do not match.
     */
    @Test
    public void login_WithInvalidPassword_ThrowsOwnerInvalidCredentialsException() {
        // Arrange
        String email = "owner@example.com";
        String rawPassword = "wrongPassword";
        String encodedPassword = "encodedPassword";

        Owner owner = new Owner();
        owner.setEmail(email);
        owner.setPassword(encodedPassword);

        OwnerLoginRequestDto loginRequest = new OwnerLoginRequestDto();
        loginRequest.setEmail(email);
        loginRequest.setPassword(rawPassword);

        when(ownerRepository.findByEmail(email)).thenReturn(Optional.of(owner));
        // Simulate password mismatch
        when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(false);

        // Act & Assert: Expect an exception due to invalid password
        assertThrows(OwnerInvalidCredentialsException.class, () -> {
            authenticationService.login(loginRequest);
        });
    }

    /**
     * MethodName: login_WithNonExistentEmail_ThrowsOwnerInvalidCredentialsException
     *
     * Input:
     *   - Non-existent email: "nonexistent@example.com"
     *   - Provided raw password: "anyPassword"
     *
     * Expected Output:
     *   - An OwnerInvalidCredentialsException is thrown because no owner exists with the provided email.
     */
    @Test
    public void login_WithNonExistentEmail_ThrowsOwnerInvalidCredentialsException() {
        // Arrange
        String email = "nonexistent@example.com";
        String rawPassword = "anyPassword";

        OwnerLoginRequestDto loginRequest = new OwnerLoginRequestDto();
        loginRequest.setEmail(email);
        loginRequest.setPassword(rawPassword);

        // Simulate no owner found for the provided email
        when(ownerRepository.findByEmail(email)).thenReturn(Optional.empty());

        // Act & Assert: Expect an exception due to a non-existent email
        assertThrows(OwnerInvalidCredentialsException.class, () -> {
            authenticationService.login(loginRequest);
        });
    }
}