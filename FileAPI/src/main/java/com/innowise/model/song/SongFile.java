package com.innowise.model.song;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SongFile {

    @Id
    @GeneratedValue(
            strategy= SEQUENCE,
            generator="song_file_seq"
    )
    @SequenceGenerator(
            name="song_file_seq",
            sequenceName="song_file_seq"
    )
    private Long id;
    private String artist;
    private String songName;

    @Enumerated(STRING)
    private StorageType storageType;
    private String storagePath;

    public SongFile(String artist, String songName) {
        this.artist = artist;
        this.songName = songName;
    }
}
