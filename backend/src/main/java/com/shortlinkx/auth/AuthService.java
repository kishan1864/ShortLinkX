package com.shortlinkx.auth;

import com.shortlinkx.auth.dto.AuthResponse;
import com.shortlinkx.auth.dto.LoginRequest;
import com.shortlinkx.auth.dto.RegisterRequest;

import com.shortlinkx.exception.UserAlreadyExistsException;
import com.shortlinkx.security.JwtService;

import com.shortlinkx.user.User;
import com.shortlinkx.user.UserRepository;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JwtService jwtService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public void register(RegisterRequest request) {

        String email = request.getEmail()
                .trim()
                .toLowerCase();

        if (userRepository.existsByEmail(email)) {
            throw new UserAlreadyExistsException(
                    "User already exists with email: " + email
            );
        }

        User user = new User();

        user.setName(request.getName().trim());
        user.setEmail(email);

        user.setPassword(
                passwordEncoder.encode(
                        request.getPassword()
                )
        );

        user.setRole("USER");

        userRepository.save(user);
    }

    public AuthResponse login(LoginRequest request) {

        String email = request.getEmail()
                .trim()
                .toLowerCase();

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                email,
                                request.getPassword()
                        )
                );

        org.springframework.security.core.userdetails.User
                principal =
                (org.springframework.security.core.userdetails.User)
                        authentication.getPrincipal();

        String role = principal.getAuthorities()
                .stream()
                .findFirst()
                .map(authority ->
                        authority.getAuthority()
                                .replace("ROLE_", "")
                )
                .orElse("USER");

        String token = jwtService.generateToken(
                email,
                role
        );

        return new AuthResponse(
                token,
                "Bearer",
                jwtService.getExpiration()
        );
    }
}