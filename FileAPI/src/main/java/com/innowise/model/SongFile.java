package com.innowise.model;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class SongFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String artist;

    @NonNull
    private String songName;

    @NonNull
    @Enumerated(EnumType.STRING)
    private StorageType storageType;

    @NonNull
    private String storagePath;
}
