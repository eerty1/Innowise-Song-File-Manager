package com.innowise.web.internal;

import com.innowise.crud_service.CrudService;
import com.innowise.dto.user.UserDTO;
import com.innowise.model.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final CrudService<User, UserDTO, String> userCrudService;

    @PostMapping
    public void saveUser(@RequestBody UserDTO userDTO) {
        userCrudService.addEntity(userDTO);
    }

    @PatchMapping(path = "/{id}")
    public void updateUser(@PathVariable String id, @RequestBody UserDTO userDTO) {
        userCrudService.updateEntity(id, userDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable String id) {
        userCrudService.deleteEntity(id);
    }
}
