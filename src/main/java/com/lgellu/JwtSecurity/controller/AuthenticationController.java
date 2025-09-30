package com.lgellu.JwtSecurity.controller;

import com.lgellu.JwtSecurity.UserRepository;
import com.lgellu.JwtSecurity.enums.Role;
import com.lgellu.JwtSecurity.model.dto.AuthResponse;
import com.lgellu.JwtSecurity.model.entity.User;
import com.lgellu.JwtSecurity.model.request.UserRequest;
import com.lgellu.JwtSecurity.service.jwt.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid UserRequest userRequest) {
        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        Set<Role> roles = userRequest.getRoles() == null || userRequest.getRoles().isEmpty()
                ? Set.of(Role.ROLE_USER)
                : userRequest.getRoles();
        user.setRoles(roles);
        userRepository.save(user);
        return ResponseEntity.ok("User Registered Successfully...");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid UserRequest userRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userRequest.getUsername(),userRequest.getPassword())
        );
        UserDetails userDetails = userDetailsService.loadUserByUsername(userRequest.getUsername());
        String token = jwtService.generateToken(userDetails);

        AuthResponse authResponse = new AuthResponse(userRequest.getUsername(),token,userRequest.getRoles());
        return ResponseEntity.ok(authResponse);
    }
}