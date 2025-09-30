package com.lgellu.JwtSecurity.model.dto;

import com.lgellu.JwtSecurity.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class AuthResponse {
    String username;
    String accessToken;
    Set<Role> roles;
}
