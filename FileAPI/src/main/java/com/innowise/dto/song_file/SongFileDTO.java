package com.innowise.dto.song_file;

import com.innowise.model.song.StorageType;
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
    private String artist;
    private String songName;
    private StorageType storageType;
    private String storagePath;
}
