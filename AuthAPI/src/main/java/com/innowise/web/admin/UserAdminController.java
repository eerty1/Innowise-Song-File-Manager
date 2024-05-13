package com.innowise.web.admin;

import com.innowise.crud_service.UserCrudService;
import com.innowise.dto.UpdateUserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/admin/user")
@RequiredArgsConstructor
public class UserAdminController {

    private final UserCrudService userCrudService;

    @PatchMapping(path = "/{id}")
    public void updateUser(@PathVariable UUID id, @Valid @RequestBody UpdateUserDTO updateUserDTO) {
        userCrudService.updateUser(id, updateUserDTO);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteUser(@PathVariable UUID id) {
        userCrudService.deleteUser(id);
    }
}
