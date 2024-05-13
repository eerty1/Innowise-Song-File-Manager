package com.innowise.model.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "user_pool")
public class User {

    @Id
    private String id;
    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
}

