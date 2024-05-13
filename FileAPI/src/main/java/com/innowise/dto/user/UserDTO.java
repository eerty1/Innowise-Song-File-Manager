package com.innowise.dto.user;

import com.innowise.model.user.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO {
    private String id;
    private String username;
    private String password;
    private Role role;
}
