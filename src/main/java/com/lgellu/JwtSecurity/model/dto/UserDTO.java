package com.lgellu.JwtSecurity.model.dto;

import com.lgellu.JwtSecurity.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    String username;
    Set<Role> roles;
}
