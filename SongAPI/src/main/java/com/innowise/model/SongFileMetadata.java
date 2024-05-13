package com.innowise.model;

import com.innowise.model.spotify_metadata.TrackMetadata;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class SongFileMetadata {

    @Id
    private String id;
    private String artist;
    private String songName;

    private Long contentSize;

    private String contentType;

    private Date lastModified;

    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "track_metadata_id")
    @EqualsAndHashCode.Exclude
    private TrackMetadata trackMetadata;
}