package com.innowise.model.spotify_metadata;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.innowise.model.attribute_converter.ExternalUrlsConverter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class SpotifyMetadata {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("spotify_metadata_id")
    private Long id;

    @JsonProperty("artists")
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "spotify_metadata_id", nullable = false)
    private List<Artist> artists = new ArrayList<>();

    @JsonProperty("id")
    private String trackId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("duration_ms")
    private int durationMs;

    @JsonProperty("popularity")
    private int popularity;

    @JsonProperty("preview_url")
    private String previewUrl;

    @Convert(converter = ExternalUrlsConverter.class)
    @JsonProperty("external_urls")
    private ExternalUrls externalUrls;
}
