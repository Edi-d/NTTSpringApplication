package org.example.controller;

import org.example.authentication.service.RegistrationService;
import org.example.authentication.request.LoginRequest;
import org.example.authentication.request.RegisterRequest;
import org.example.authentication.response.LoginResponse;
import org.example.authentication.response.RegisterResponse;
import org.example.authentication.util.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RegistrationService registrationService;

    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService, RegistrationService registrationService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.registrationService = registrationService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        String token = jwtService.generateToken(loginRequest.getEmail());
        return ResponseEntity.ok(new LoginResponse(token));
    }

//    @PostMapping("/login")
//    public ResponseEntity<Void> login(@RequestBody UserLoginRequestDto loginRequest, HttpServletResponse response) {
//        UserLoginResponseDto loginResponse = authenticationService.login(loginRequest);
//
//        Cookie cookie = new Cookie(AUTH_HEADER_NAME, loginResponse.getJwtToken());
//        cookie.setMaxAge(LOGIN_EXPIRATION_TIME);
//        cookie.setHttpOnly(true);
//        cookie.setSecure(true); // Ensure secure cookie if using HTTPS
//        response.addCookie(cookie);
//
//        return ResponseEntity.ok().build();
//    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        try {
            registrationService.registerOwner(registerRequest);
            return ResponseEntity.ok(new RegisterResponse("User registered successfully!"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new RegisterResponse("Registration failed: " + e.getMessage()));
        }
    }
}
