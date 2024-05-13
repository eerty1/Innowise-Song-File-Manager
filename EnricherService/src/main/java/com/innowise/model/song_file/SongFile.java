package com.innowise.model.song_file;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class SongFile {

    private Long id;
    private String artist;
    private String songName;
    private StorageType storageType;
    private String storagePath;
}
