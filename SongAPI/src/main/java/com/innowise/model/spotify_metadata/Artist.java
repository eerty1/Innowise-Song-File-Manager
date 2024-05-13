package com.innowise.model.spotify_metadata;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.innowise.model.attribute_converter.ExternalUrlsConverter;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NamedEntityGraph(
        name = "graph.artistsTracks",
        attributeNodes = @NamedAttributeNode("trackMetadata")
)
public class Artist {

    @Id
    @ToString.Include
    @EqualsAndHashCode.Include
    private String id;

    @Convert(converter = ExternalUrlsConverter.class)
    @Getter(onMethod_ = @JsonGetter("externalUrls"))
    @Setter(onMethod_ = @JsonSetter("external_urls"))
    private ExternalUrls externalUrls;

    private String name;
    @EqualsAndHashCode.Exclude
    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = { CascadeType.PERSIST }
    )
    @JoinTable(
            name = "track_metadata_artists",
            joinColumns = @JoinColumn(name = "artist_id"),
            inverseJoinColumns = @JoinColumn(name = "track_metadata_id")
    )
    private Set<TrackMetadata> trackMetadata = new HashSet<>();

    public void addTrackMetadata(TrackMetadata trackMetadata) {
        this.trackMetadata.add(trackMetadata);
    }

    @PreRemove
    private void deleteArtist() {
        trackMetadata.forEach(trackMetadata -> trackMetadata.getArtists().remove(this));
    }
}
