package com.innowise.model.song_file;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class SongFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String artist;
    private String songName;

    @Enumerated(EnumType.STRING)
    private StorageType storageType;

    private String storagePath;
}
