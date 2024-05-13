package com.innowise.web.user;

import com.innowise.crud_service.UserCrudService;
import com.innowise.dto.ChangeUserPasswordDTO;
import com.innowise.dto.CheckUserOutDTO;
import com.innowise.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserCrudService userCrudService;

    @GetMapping(path = "/{id}")
    public UserDTO getUser(@PathVariable UUID id) {
        return userCrudService.getUser(id);
    }

    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userCrudService.getAllUsers();
    }

    @PostMapping
    public ResponseEntity<UserDTO> checkUserOut(@RequestBody CheckUserOutDTO checkUserOutDTO) {
        return userCrudService.checkUserOut(checkUserOutDTO);
    }

    @PostMapping(path = "/password/{id}")
    public ResponseEntity<String> changeUserPassword(@PathVariable UUID id, @Valid @RequestBody ChangeUserPasswordDTO changeUserPasswordDTO) {
        return userCrudService.changeUserPassword(id, changeUserPasswordDTO);
    }
}
