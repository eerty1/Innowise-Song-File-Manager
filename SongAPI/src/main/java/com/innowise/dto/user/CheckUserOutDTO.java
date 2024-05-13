package com.innowise.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckUserOutDTO {
    private final String microserviceName = "SongAPI";
    private String id;

    public CheckUserOutDTO(String id) {
        this.id = id;
    }
}
