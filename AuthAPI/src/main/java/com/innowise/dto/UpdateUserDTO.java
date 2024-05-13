package com.innowise.dto;

import com.innowise.model.Role;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static com.innowise.model.ValidationConstraintsMessages.NOT_IN_RANGE;
import static com.innowise.model.ValidationConstraintsMessages.NOT_NULL;

@Getter
@Setter
public class UpdateUserDTO {

    @Size(min = 3, max = 30, message = NOT_IN_RANGE)
    private String username;

    @NotNull(message = NOT_NULL)
    @Enumerated(EnumType.STRING)
    private Role role;
}
