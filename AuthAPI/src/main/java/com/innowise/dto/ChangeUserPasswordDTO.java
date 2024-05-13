package com.innowise.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import static com.innowise.model.ValidationConstraintsMessages.NOT_BLANK;
import static com.innowise.model.ValidationConstraintsMessages.NOT_IN_RANGE;

@Getter
@Setter
public class ChangeUserPasswordDTO {

    @NotBlank(message = NOT_BLANK)
    private String oldPassword;

    @Size(min = 3, max = 100, message = NOT_IN_RANGE)
    private String newPassword;
}
