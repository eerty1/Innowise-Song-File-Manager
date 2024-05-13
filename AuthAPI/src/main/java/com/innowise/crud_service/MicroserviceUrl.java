package com.innowise.crud_service;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MicroserviceUrl {
    FILE_API("http://FileAPI/api/user/"),
    SONG_API("http://SongAPI/api/user/");
    private final String url;
}
