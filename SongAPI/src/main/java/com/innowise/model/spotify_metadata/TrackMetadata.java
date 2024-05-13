package com.innowise.model.spotify_metadata;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.innowise.model.SongFileMetadata;
import com.innowise.model.attribute_converter.ExternalUrlsConverter;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;


@Entity
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class TrackMetadata {

    @Id
    private String id;

    @EqualsAndHashCode.Exclude
    @ManyToMany(
            mappedBy = "trackMetadata",
            fetch = FetchType.LAZY,
            cascade =
                    {
                            CascadeType.PERSIST, CascadeType.MERGE
                    })
    private Set<Artist> artists;

    private String name;

    @Getter(onMethod_ = @JsonGetter("durationMs"))
    @Setter(onMethod_ = @JsonSetter("duration_ms"))
    private Long durationMs;

    private Integer popularity;

    @Getter(onMethod_ = @JsonGetter("previewUrl"))
    @Setter(onMethod_ = @JsonSetter("preview_url"))
    private String previewUrl;

    @Convert(converter = ExternalUrlsConverter.class)
    @Getter(onMethod_ = @JsonGetter("externalUrls"))
    @Setter(onMethod_ = @JsonSetter("external_urls"))
    private ExternalUrls externalUrls;

    @OneToOne(
            mappedBy = "trackMetadata",
            cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    @EqualsAndHashCode.Exclude
    private SongFileMetadata songFileMetadata;

    @PreRemove
    private void deleteTrackMetadata() {
        artists.forEach(artist -> artist.getTrackMetadata().remove(this));
    }
}
