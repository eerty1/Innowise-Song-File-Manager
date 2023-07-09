package com.innowise.model.song_file;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class SongFile implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("artist")
    private String artist;

    @JsonProperty("song_name")
    private String songName;

    @JsonProperty("storage_type")
    @Enumerated(EnumType.STRING)
    private StorageType storageType;

    @JsonProperty("storage_path")
    private String storagePath;
}

