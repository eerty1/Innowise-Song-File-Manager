package com.innowise.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.innowise.model.song_file.StorageType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SongFileDTO {
    private Long id;
    @JsonProperty("artist")
    private String artist;

    @JsonProperty("song_name")
    private String songName;

    @JsonProperty("storage_type")
    private StorageType storageType;

    @JsonProperty("storage_path")
    private String storagePath;
}
