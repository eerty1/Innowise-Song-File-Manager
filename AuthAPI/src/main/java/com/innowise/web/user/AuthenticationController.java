package com.innowise.web.user;

import com.innowise.crud_service.UserCrudService;
import com.innowise.dto.UserDTO;
import com.innowise.model.User;
import com.innowise.security.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final LoginService loginService;
    private final UserCrudService userCrudService;

    @PostMapping(path = "/login")
    public String login(@RequestBody User user) {
        return loginService.loginUser(user);
    }

    @PostMapping(path = "/registration")
    public ResponseEntity<String> register(@Valid @RequestBody UserDTO userDTO) {
        return userCrudService.registerNewUser(userDTO);
    }
}
