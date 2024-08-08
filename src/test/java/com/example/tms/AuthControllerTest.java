package com.example.tms;

import com.example.tms.controller.AuthController;
import com.example.tms.model.ERole;
import com.example.tms.model.Role;
import com.example.tms.payload.request.LoginRequest;
import com.example.tms.payload.request.SignupRequest;
import com.example.tms.payload.response.JwtResponse;
import com.example.tms.payload.response.MessageResponse;
import com.example.tms.repository.RoleRepository;
import com.example.tms.repository.UserRepository;
import com.example.tms.security.jwt.JwtUtils;
import com.example.tms.security.services.UserDetailsImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder encoder;

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private Authentication authentication;

    @Mock
    private UserDetailsImpl userDetails;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void authenticateUser_Success() {

        LoginRequest loginRequest = new LoginRequest("user", "password");
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(jwtUtils.generateJwtToken(any(Authentication.class))).thenReturn("jwtToken");
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getId()).thenReturn(1L);
        when(userDetails.getUsername()).thenReturn("user");
        when(userDetails.getEmail()).thenReturn("user@example.com");

        ResponseEntity<?> response = authController.authenticateUser(loginRequest);

        JwtResponse jwtResponse = (JwtResponse) response.getBody();
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1L, jwtResponse.getId());
        assertEquals("user", jwtResponse.getUsername());
        assertEquals("user@example.com", jwtResponse.getEmail());
    }

    @Test
    void registerUser_Success() {

        SignupRequest signUpRequest = new SignupRequest();
        signUpRequest.setUsername("user");
        signUpRequest.setEmail("user@example.com");
        signUpRequest.setPassword("password");
        signUpRequest.setRole(Set.of("user"));

        when(userRepository.existsByUsername(signUpRequest.getUsername())).thenReturn(false);
        when(userRepository.existsByEmail(signUpRequest.getEmail())).thenReturn(false);
        when(encoder.encode(signUpRequest.getPassword())).thenReturn("encodedPassword");
        when(roleRepository.findByName(ERole.ROLE_USER)).thenReturn(Optional.of(new Role(ERole.ROLE_USER)));

        ResponseEntity<?> response = authController.registerUser(signUpRequest);

        MessageResponse messageResponse = (MessageResponse) response.getBody();
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("User registered successfully!", messageResponse.getMessage());
    }

    @Test
    void registerUser_UsernameTaken() {

        SignupRequest signUpRequest = new SignupRequest();
        signUpRequest.setUsername("user");
        signUpRequest.setEmail("user@example.com");

        when(userRepository.existsByUsername(signUpRequest.getUsername())).thenReturn(true);

        ResponseEntity<?> response = authController.registerUser(signUpRequest);

        MessageResponse messageResponse = (MessageResponse) response.getBody();
        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Error: Username is already taken!", messageResponse.getMessage());
    }

    @Test
    void registerUser_EmailTaken() {

        SignupRequest signUpRequest = new SignupRequest();
        signUpRequest.setUsername("user");
        signUpRequest.setEmail("user@example.com");

        when(userRepository.existsByUsername(signUpRequest.getUsername())).thenReturn(false);
        when(userRepository.existsByEmail(signUpRequest.getEmail())).thenReturn(true);

        ResponseEntity<?> response = authController.registerUser(signUpRequest);

        MessageResponse messageResponse = (MessageResponse) response.getBody();
        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Error: Email is already in use!", messageResponse.getMessage());
    }
}
