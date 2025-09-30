package com.lgellu.JwtSecurity.model.request;

import com.lgellu.JwtSecurity.enums.Role;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    @NotBlank(message = "username is required")
    String username;
    @NotBlank(message = "password is required")
    String password;
    Set<Role> roles;
}
